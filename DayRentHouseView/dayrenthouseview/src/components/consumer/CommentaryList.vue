<template>
  <div>
    <van-nav-bar title="房屋评价" left-text="返回" left-arrow @click-left="clickBack" />

    <div v-for="(commentary,index) in comentaryList" :key="index">
      <van-row style="margin-top:3%">
        <van-col offset="1" span="9">{{commentary.userPhone}}</van-col>
        <van-col offset="1" span="4">
          <span style="font-size:11px">评分：{{commentary.fraction}}</span>
        </van-col>
        <van-col offset="2">
          <span style="font-size:11px">{{commentary.creatTime}}</span>
        </van-col>
      </van-row>

      <van-row>
        <van-col offset="1">
          <span style="font-size:12px">{{commentary.content}}</span>
        </van-col>
      </van-row>

      <van-row>
        <van-col
          style="margin-top:3%"
          offset="1"
          v-for="(img ,index) in commentary.imgs"
          :key="index"
        >
          <van-image width="100" height="100" :src="getImg(img)" />
        </van-col>
      </van-row>

      <van-row style="font-size:13px;margin-top:2%" v-if="commentary.isRead=='yes'">
        <van-col style="background-color:#EEE9E9;padding:1%;" offset="2" span="21">
          <span>屋主回复：{{commentary.reply}}</span>
        </van-col>
      </van-row>

      <van-row v-if="isMaster&&commentary.isRead=='no'" style="margin-top:2%">
        <van-col offset="18">
          <van-button plain type="primary" size="small" @click="clickShowReply(commentary.id)">回复</van-button>
        </van-col>
      </van-row>
      <van-divider />

      <van-popup closeable v-model="replyShow" position="bottom" :style="{ height: '30%' }">
        <van-row style="margin-top:4%">
          <van-field
            v-model="replyInfo.content"
            rows="5"
            label="店家回复："
            type="textarea"
            placeholder="请输入回复内容"
          />
        </van-row>
        <van-row>
          <van-col offset="20">
            <van-button size="small" plain type="primary" @click="clickReply">回复</van-button>
          </van-col>
        </van-row>
      </van-popup>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      houseId: -1,
      comentaryList: [],
      isMaster: false,
      replyShow: false,
      replyInfo: {
        commentaryId: -1,
        content: ""
      }
    }
  },

  methods: {
    getImg(imgPath) {
      if (imgPath == null) {
        return
      }
      const img = imgPath.substring(9)
      let path = "@/assets/"
      return require("@/assets/" + img)
    },
    getCommentary() {
      this.houseId = this.$route.query.houseId
      this.$axios.get("/commentarys/" + this.houseId).then(response => {
        let data = response.data
        for (let i = 0; i < data.length; i++) {
          if (data[i].imgs.indexOf(",") != -1) {
            data[i].imgs = this.transforImgs(data[i].imgs)
          }
        }
        this.comentaryList = data
      })

      this.$axios.get("/getUserState").then(response => {
        let data = response.data
        if (data == "master") {
          this.isMaster = true
        }
      })
    },

    transforImgs(imgs) {
      let houseImgs = imgs.split(",")
      for (let imgPath in houseImgs) {
        houseImgs[imgPath] = houseImgs[imgPath].substr(
          houseImgs[imgPath].indexOf("src") + 3
        )
        houseImgs[imgPath] = houseImgs[imgPath].replace(/\\/g, "/")
        houseImgs[imgPath] = "@" + houseImgs[imgPath]
      }
      return houseImgs
    },
    clickBack() {
      this.$router.go(-1) //返回上一层
    },
    clickShowReply(id) {
      this.replyInfo.content = ""
      this.replyInfo.commentaryId = id
      this.replyShow = true
    },
    clickReply() {
      if (this.replyInfo.commentaryId < 0) {
        alert("系统异常")
        return
      }

      if (this.replyInfo.content == "" || this.replyInfo.content.length == 0) {
        alert("请输入回复内容")
        return
      }

      this.$axios
        .post("/updateCommentaryReply", this.replyInfo)
        .then(response => {
          let data = response.data
          if (data == "success") {
            this.replyShow=false
            alert("回复成功")
            this.getCommentary() 
          } else {
            alert("系统异常")
          }
        })
    }
  },
  created() {
    this.getCommentary()
  }
}
</script>

<style  scoped>
</style>