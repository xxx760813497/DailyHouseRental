<template>
  <div>
    <van-row type="flex" justify="center" style="padding-top:5%">
      <span>评论管理</span>
    </van-row>

    <div style="margin-top:5%" v-for="(commentary,index) in comentaryList" :key="index">
      <van-row @click="getHouseDetail(commentary.houseId)" style="margin-top:3%">
        <van-col offset="1" span="9">{{commentary.userPhone}}</van-col>
        <van-col offset="1" span="4">
          <span style="font-size:11px">评分：{{commentary.fraction}}</span>
        </van-col>
        <van-col offset="2">
          <span style="font-size:11px">{{commentary.creatTime}}</span>
        </van-col>
      </van-row>

      <van-row @click="getHouseDetail(commentary.houseId)">
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

      <van-row style="font-size:13px;margin-top:2%">
        <van-col style="background-color:#EEE9E9;padding:1%;" offset="2" span="21">
          <span>申诉内容：{{commentary.appeal}}</span>
        </van-col>
      </van-row>

      <van-row style="margin-top:3%">
        <van-col offset="1" style="font-size:14px">
          <span>房屋名：{{commentary.houseName}}</span>
          <br />
          <span>租客入住时间：{{commentary.orderDate}}</span>
          <br />
          <span>订单金额：{{commentary.orderPrice}}</span>
        </van-col>
      </van-row>

      <van-row style="margin-top:3%">
        <van-col offset="13">
          <van-button plain type="warning" size="small" @click="clickUndo(commentary.id)">撤回评论</van-button>
        </van-col>
        <van-col offset="1">
          <van-button plain type="primary" size="small" @click="clickInvalid(commentary.id)">申诉无效</van-button>
        </van-col>
      </van-row>
      <van-divider />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      comentaryList: []
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
      this.$axios.get("/reviewCommentaries").then(response => {
        let data = response.data
        if(data.msg=='noLogin'){
          this.$router.push('/login')
        }
        for (let i = 0; i < data.length; i++) {
          if (data[i].imgs.indexOf(",") != -1) {
            data[i].imgs = this.transforImgs(data[i].imgs)
          }
        }
        this.comentaryList = data
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
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
    getHouseDetail(houseId) {
      this.$router.push({ path: "/houseDetail", query: { id: houseId } })
    },
    clickUndo(commentaryId) {
      this.$dialog
        .alert({
          title: "撤销评论",
          message: "本次操作将这条评论删除，确定要继续吗？"
        })
        .then(() => {
          this.$axios.delete("/reviewCommentary/"+commentaryId).then(response => {
            let data = response.data
            if(data.msg=='noLogin'){
          this.$router.push('/login')
        }
            if(data=='success'){
              alert('操作成功')
            }else{
              alert('系统异常')
            }
            this.getCommentary()
          })
        }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
        .catch(() => {
          // on cancel
        })
    },
    clickInvalid(commentaryId){
      this.$dialog
        .alert({
          title: "申诉无效",
          message: "本次操作将撤回本次申诉，评论会继续存在，确定要继续吗？"
        })
        .then(() => {
          let commentaryInfo={}
          commentaryInfo.id=commentaryId
          commentaryInfo.status="申诉无效"
          this.$axios.post("/reviewCommentary",commentaryInfo).then(response => {
            let data = response.data
            if(data=='success'){
              alert('操作成功')
            }else{
              alert('系统异常')
            }
            this.getCommentary()
          })
        }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
        .catch(() => {
          // on cancel
        })
    },
  },
  created() {
    this.getCommentary()
  }
}
</script>

<style  scoped>
</style>