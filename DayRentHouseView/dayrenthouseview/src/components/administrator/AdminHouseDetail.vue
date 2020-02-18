<template>
  <div>
    <van-row>
      <van-nav-bar :title="houseDitail.name" left-text="返回" left-arrow @click-left="clickBack" />
    </van-row>

    <van-row>
      <van-swipe :autoplay="3000000" indicator-color="white">
        <van-swipe-item v-for="(imgPath,index) in houseDitail.houseImgs" :key="index">
          <van-image width="100%" height="300px" :src="getImg(imgPath)" />
        </van-swipe-item>
      </van-swipe>
    </van-row>

    <van-row>
      <van-col offset="1">
        <span>{{houseDitail.name}}</span>
      </van-col>
    </van-row>

    <van-row>
      <van-col offset="1">
        <span>￥{{houseDitail.price}}</span>
      </van-col>
    </van-row>
    <van-divider />
    <van-row>
      <van-col offset="1">
        <span>屋主：小明</span>
      </van-col>
    </van-row>
    <van-divider />
    <van-row>
      <van-col offset="1">
        <span>房屋描述</span>
        <br />
        <span>{{houseDitail.describe}}</span>
      </van-col>
    </van-row>
    <van-divider />

    <van-row>
      <van-col offset="1">房屋设备</van-col>
    </van-row>

    <van-row style="margin-top:3%">
      <van-grid :gutter="0.5" :column-num="5">
        <van-grid-item :class="{iconActive:equipments.noreshui}" icon="wap-home-o" text="热水" />
        <van-grid-item :class="{iconActive:equipments.noyuye}" icon="cluster-o" text="浴液" />
        <van-grid-item :class="{iconActive:equipments.noyaju}" icon="brush-o" text="牙具" />
        <van-grid-item :class="{iconActive:equipments.nomaojin}" icon="label-o" text="毛巾" />
        <van-grid-item :class="{iconActive:equipments.noyugang}" icon="idcard" text="浴缸" />
        <van-grid-item :class="{iconActive:equipments.nozhijin}" icon="paid" text="纸巾" />
        <van-grid-item :class="{iconActive:equipments.nokongtiao}" icon="bulb-o" text="空调" />
        <van-grid-item :class="{iconActive:equipments.nonuanqi}" icon="fire-o" text="暖气" />
        <van-grid-item :class="{iconActive:equipments.nodianshi}" icon="tv-o" text="电视" />
        <van-grid-item :class="{iconActive:equipments.nowifi}" icon="bar-chart-o" text="Wifi" />
        <van-grid-item :class="{iconActive:equipments.nobingxiang}" icon="hotel-o" text="冰箱" />
        <van-grid-item :class="{iconActive:equipments.noxiyiji}" icon="send-gift-o" text="洗衣机" />
        <van-grid-item :class="{iconActive:equipments.notingchechang}" icon="circle" text="停车场" />
      </van-grid>
    </van-row>
    <van-divider />

    <van-row>
      <van-col offset="1">
        <span>地理位置</span>
        <br />
        <br />
        <span>{{houseDitail.location}}</span>
      </van-col>
    </van-row>

    <van-row style="margin-top:3%">
      <baidu-map class="map" :center="center" @ready="mapReady" :zoom="zoom">
        <!-- <bm-marker :position="{lng:center.lng,lat:center.lat}" :dragging="false" animation="BMAP_ANIMATION_BOUNCE">
          <bm-label
            :content="houseDitail.name"
            :labelStyle="{color: 'red', fontSize : '12px'}"
            :offset="{width:-10 , height: 30}"
          />
        </bm-marker>-->
      </baidu-map>
    </van-row>
    <van-divider />

    <van-row v-if="buttonShow" type="flex" justify="center">
      <van-col>
        <van-button type="primary" @click="clickAllow">申请通过</van-button>
      </van-col>
      <van-col offset="1">
        <van-button type="warning" @click="clickCancel">撤回申请</van-button>
      </van-col>
    </van-row>

    <van-row v-if="resultTextShow" type="flex" justify="center">
      <span>审核完成，结果为:{{houseDitail.houseState}}</span>
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      houseId: -1,
      center: {
        lng: 0,
        lat: 0
      },
      zoom: 15,
      buttonShow: true,
      resultTextShow: false,
      houseDitail: {
        id: "",
        userId: "",
        name: "你好",
        address: "",
        location: "",
        describe: "",
        equipments: "",
        houseTitleImg: "",
        houseImgs: "",
        grade: "",
        price: -1,
        beginDate: "",
        endDate: "",
        allDate: [],
        DaysNumber: 0,
        expense: -1,
        houseState: ""
        // rentedDate:[]
      },
      equipments: {
        noreshui: true,
        noyuye: true,
        noyaju: true,
        nomaojin: true,
        noyugang: true,
        nozhijin: true,
        nokongtiao: true,
        nonuanqi: true,
        nodianshi: true,
        nowifi: true,
        nobingxiang: true,
        noxiyiji: true,
        notingchechang: true
      }
    }
  },

  created() {
    this.getHouseDitail()
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
    clickBack() {
      this.$router.push("/adminHome")
    },

    getHouseDitail() {
      this.houseId = this.$route.query.id
      this.$axios.get("/all/house/" + this.houseId).then(response => {
        let data = response.data

        if (data != null) {
          this.houseDitail.id = data.id
          this.houseDitail.userId = data.userId
          this.houseDitail.name = data.name

          //地图定位
          this.houseDitail.address = data.address

          this.houseDitail.location = data.location
          this.houseDitail.describe = data.describe

          //房屋设备的处理
          this.houseDitail.equipments = data.equipments
          this.getHouseEquipment(this.houseDitail.equipments)

          //房屋的图片路径处理，将字符串转换为数组
          this.houseDitail.houseTitleImg = data.houseTitleImg
          this.houseDitail.houseImgs =
            this.houseDitail.houseTitleImg + "," + data.houseImgs
          this.houseDitail.houseImgs = this.transforImgs(
            this.houseDitail.houseImgs
          )
          this.houseDitail.grade = data.grade
          this.houseDitail.price = data.price

          this.houseDitail.houseState = data.houseState
          

          this.buttonShow =(this.houseDitail.houseState == "审核中")
          this.resultTextShow =!this.buttonShow
        } else {
          alert("系统异常，请重试")
        }
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
    getHouseEquipment(data) {
      let equipments = data.split(",")
      for (let equipment in equipments) {
        switch (equipments[equipment]) {
          case "reshui":
            this.equipments.noreshui = false
            break
          case "yuye":
            this.equipments.noyuye = false
            break
          case "yaju":
            this.equipments.noyaju = false
            break
          case "maojin":
            this.equipments.nomaojin = false
            break
          case "yugang":
            this.equipments.noyugang = false
            break
          case "zhijin":
            this.equipments.nozhijin = false
            break
          case "kongtiao":
            this.equipments.nokongtiao = false
            break
          case "nuanqi":
            this.equipments.nonuanqi = false
            break
          case "dianshi":
            this.equipments.nodianshi = false
            break
          case "wifi":
            this.equipments.nowifi = false
            break
          case "bingxiang":
            this.equipments.nobingxiang = false
            break
          case "xiyiji":
            this.equipments.noxiyiji = false
            break
          case "tingchechang":
            this.equipments.notingchechang = false
            break
          default:
            break
        }
      }
    },
    mapReady() {
      let addresss = this.houseDitail.address.split(",")
      this.center.lng = addresss[0]
      this.center.lat = addresss[1]
      this.zoom = 17
    },
    clickAllow() {
      this.$dialog
        .confirm({
          title: "审核通过",
          message: "确认房屋信息真实，租客将会检索到该房屋的信息"
        })
        .then(() => {
          this.$axios
            .put("/admin/reviewHouses", {
              id: this.houseDitail.id,
              houseState: "审核通过"
            })
            .then(response => {
              let data = response.data
              if (data.msg == "noLogin") {
                this.$router.push("/login")
              }
              if (data == "ok") {
                alert("房屋审核通过")
                
                this.getHouseDitail()

              } else {
                alert("系统异常，请重试")
              }
            })
        }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
    clickCancel() {
      this.$dialog
        .confirm({
          title: "审核不通过",
          message: "确认房屋信息不真实或者不合理，本次房屋发布请求撤回"
        })
        .then(() => {
          this.$axios
            .put("/admin/reviewHouses", {
              id: this.houseDitail.id,
              houseState: "审核未通过"
            })
            .then(response => {
              let data = response.data
              if (data.msg == "noLogin") {
                this.$router.push("/login")
              }
              if (data == "ok") {
                alert("操作成功")
                this.getHouseDitail()
              } else {
                alert("系统异常，请重试")
                
              }
              this.$router.push("/adminHome")
            })
        })
    }
  }
}
</script>

<style scoped>
.iconActive {
  opacity: 0.4;
}
.reserveBtn {
  height: 50px;
  background-color: #ffdab9;
}
</style>
