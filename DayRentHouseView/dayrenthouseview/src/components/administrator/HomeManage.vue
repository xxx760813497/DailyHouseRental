<template>
  <div style="padding-top:5%">
    <van-row type="flex" justify="center">
      <span>房屋管理</span>
    </van-row>

    <div v-for="(house,index) in housesList" :key="index">
      <van-row style="background-color:#F4F4F4;margin-top:3%">
        <van-col span="8">
          <van-image fit="fill" width="120" height="120" :src="getImg(house.houseTitleImg)" />
        </van-col>
        <van-col span="15" offset="1">
          <span>房名：{{house.name}}</span>
          <br />
          <span>地址：{{house.location}}</span>
          <br />
          <span>价格：{{house.price}}</span>
          <br />
          <span>申请时间：{{house.addDate}}</span>
          <br />
          <span>房屋状态：</span>
          <span style="color:red">{{house.houseState}}</span>
        </van-col>
      </van-row>
      <van-row>
        <van-col style="margin-top:1.5%" span="5" offset="19" >
          <van-button size="small" @click="getHouseDetail(house.id)">审核房屋</van-button>
        </van-col>
      </van-row>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      housesList: [],
      houseDetail: {},
      equipments: {
        reshui: false,
        yuye: false,
        yaju: false,
        maojin: false,
        yugang: false,
        zhijin: false,
        kongtiao: false,
        nuanqi: false,
        dianshi: false,
        wifi: false,
        bingxiang: false,
        xiyiji: false,
        tingchechang: false
      },
      houseUpdateShow: false,
      houseTitleImgUpShow: false,
      houseImgsUpShow: false,
      titleUpdateFile: [],
      titleUpdatePath: "",
      houseImgsUpdateFile: [],
      houseUpdateForm: {
        id:"",
        name: "",
        describe: "",
        price: "",
        // titleImg: "",
        // imgList: [],
        equipmentsList: []
      }
    }
  },

  created() {
    this.getHouseList()
  },

  methods: {
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
    getImg(imgPath) {
      if (imgPath == null) {
        return
      }
      const img = imgPath.substring(9)
      let path = "@/assets/"
      return require("@/assets/" + img)
    },
    getHouseList(){
      this.$axios.get("/reviewHouses").then(response => {
      let data = response.data
      
      this.housesList = data

    }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
    getHouseDetail(houseId){
      this.$router.push({path:'/adminHouseDetail',query:{id:houseId}})
    }
  }
}
</script>

<style scoped>
</style>