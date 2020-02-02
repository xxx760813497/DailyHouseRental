<template>
  <div>
    <van-nav-bar
  title="房屋评价"
  left-text="返回"
  left-arrow
  @click-left="clickBack"

/>

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
      <van-divider />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      houseId: -1,
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
  },
  created() {
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
  }
}
</script>

<style  scoped>
</style>