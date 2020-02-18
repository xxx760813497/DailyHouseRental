<template>
  <div style="background-color:#F5F5F5">
    <van-nav-bar title="评价房屋" left-text="返回" @click-left="clickBack" left-arrow />
    <div class="top">
      <van-row>
        <van-col offset="1" span="7">
          <van-image width="80" height="80" :src="orderImg" />
        </van-col>


        <van-col style="margin-top:2%" offset="1" span="5">
          <span>房屋名:</span>
        </van-col>
        <van-col style="margin-top:2%" span="9">{{orderInfo.houseName}}</van-col>

        <van-col style="margin-top:5%" offset="1" span="5">
          <span>订单金额:</span>
        </van-col>
        <van-col style="margin-top:5%" span="9">￥{{orderInfo.orderExpense}}</van-col>
      </van-row>
    </div>
    <div class="middle">
      <van-row style="margin-top:5%">
        <van-col offset="2" style="font-size:20px">房屋内部坏境：</van-col>
        <van-col>
          <van-rate size="25" gutter="8" v-model="commentInfo.interiorScore" />
        </van-col>
      </van-row>
      <van-row style="margin-top:2%">
        <van-col offset="2" style="font-size:20px">屋主服务评价：</van-col>
        <van-col>
          <van-rate size="25" gutter="8" v-model="commentInfo.masterScore" />
        </van-col>
      </van-row>
      <van-row style="margin-top:2%">
        <van-col offset="2" style="font-size:20px">房屋周围坏境：</van-col>
        <van-col>
          <van-rate size="25" gutter="8" v-model="commentInfo.aroundScore" />
        </van-col>
      </van-row>
    </div>

    <div style="margin-top:2%">
      <van-row>
        <van-col offset="2">
          <span style="font-size:20px">您的评价：</span>
        </van-col>
      </van-row>
      <van-row style="margin-top:2%">
        <van-col offset="1" span="22"></van-col>
      </van-row>
      <van-field
        v-model="commentInfo.content"
        rows="5"
        autosize
        type="textarea"
        maxlength="200"
        placeholder="说一说这次住房的体验吧~"
        show-word-limit
      />
      <van-row style="margin-top:2%">
        <van-col offset="1"></van-col>
      </van-row>
    </div>

    <van-uploader
      v-model="files"
      :max-count="6"
      style="padding-top:2%;padding-left:2%"
      preview-size="80"
      :before-read="beforeRead"
      :after-read="commentaryImgsUp"
      @delete="commentaryImgsDelete"
    />

    <div style="margin-top:1%">
      <van-row>
        <van-col offset="1" span="22">
          <van-button type="primary" size="large" @click="clcikComment">发布评价</van-button>
        </van-col>
      </van-row>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      orderImg:require("@/assets/timg.jpg"),
      orderInfo: {
        orderId: -1,
        houseId: -1,
        houseTitleImg: "",
        houseName: "",
        orderExpense: -1
      },
      commentInfo: {
        houseId:-1,
        orderId:-1,
        interiorScore: 0,
        masterScore: 0,
        aroundScore: 0,
        content: "",
        imgs: []
      },
      files: []
    }
  },
  methods: {
    init() {
      var info = this.$route.params
      this.orderInfo=info
      this.commentInfo.houseId=info.houseId
      this.commentInfo.orderId=info.orderId
    },
    getImg(imgPath) {
      if (imgPath == null) {
        return
      }
      const img = imgPath.substring(9)
      let path = "@/assets/"
      return require("@/assets/" + img)
    },
    clickBack() {
      this.$router.go(-1) //返回上一层
    },
    clcikComment() {
      if (
        this.commentInfo.interiorScore == 0 ||
        this.commentInfo.masterScore == 0 ||
        this.commentInfo.aroundScore == 0
      ) {
        alert("请对房屋进行评分")
        return
      }

      if (
        this.commentInfo.content == null ||
        this.commentInfo.content.length == 0
      ) {
        alert("请填写这次的住房体验")
        return
      }

      this.$axios.post("/consumer/commentary", this.commentInfo).then(response => {
        let data=response.data
        if(data=='success'){
          alert('评价成功')
          this.$router.go(-1)
        }
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })

    },
    beforeRead(file) {
      if (file.type !== "image/jpeg" && file.type !== "image/png") {
        alert("请上传 jpg或者png 格式图片")
        return false
      }
      return true
    },
    commentaryImgsUp(file) {
      let formData = new FormData()
      formData.append("file", file.file)

      this.$axios
        .post("/consumer/commentaryImg", formData, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(response => {
          let data = response.data
          if (data != "error") {
            this.commentInfo.imgs.push(data)
          } else {
            alert("系统异常，请重新上传")
          }
        }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
    commentaryImgsDelete(file, name) {
      var index = name.index
      let url = this.commentInfo.imgs[index]
      let formData = new FormData()
      formData.append("filePath", url)
      this.$axios.post("/consumer/deleteCommentaryImg", formData).then(response => {
        if (response.data == "success") {
          let newImgList = new Array()
          for (let i = 0, j = 0; i < this.commentInfo.imgs.length; i++) {
            if (i != index) {
              newImgList[j] = this.commentInfo.imgs[i]
              j++
            }
          }
          this.commentInfo.imgs = newImgList
        } else {
        }
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
  },
  created() {
    this.init()
  },
}
</script>

<style scoped>
.top {
  margin-top: 3%;
  padding: 2%;
  font-size: 16px;
  border: 2px solid #7cfc00;
  background-color: white;
}
.middle {
  background-color: white;
}
</style>