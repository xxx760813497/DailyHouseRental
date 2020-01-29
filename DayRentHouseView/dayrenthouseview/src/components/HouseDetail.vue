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
        <span>屋主电话：{{houseDitail.userPhone}}</span>
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
        <bm-marker :position="center" :dragging="false" animation="BMAP_ANIMATION_BOUNCE">
          <bm-label :content="houseDitail.name" :offset="{width:-15 , height: 30}" />
        </bm-marker>
      </baidu-map>
    </van-row>
    <van-divider />

    <van-row>
      <van-col offset="1">
        <span>可租日期</span>
        <br />
      </van-col>
    </van-row>

    <van-row style="margin-top:5%">
      <inlineCalendar :disabledDate="houseDitail.rentedDate" mode="multiple" @change="dataOnChange"></inlineCalendar>
    </van-row>

    <van-row>
      <van-col offset="1">
        <span style="font-size:10px">
          入住时间为所选日期下午入住，第二天中午两点离开
          <br />（连租多天则为最后一天的第二天中午两点离开）
        </span>
      </van-col>
    </van-row>

    <van-row class="reserveBtn">
      <van-col span="7" style="font-size:13px;margin-top:2px">
        <span>{{dateMsg}}</span>
        <a v-show="dateTextBtnShow" href @click.prevent="dateTextShow=true" style="color:blue">....</a>
      </van-col>

      <van-popup v-model="dateTextShow">
        入住时间为：
        <div v-for="(date,index) in OrderInfo.dates" :key="index">
          <span>{{date}}</span>
          <br />
        </div>
      </van-popup>

      <van-col span="4" offset="1" style="margin-top:3%">
        <span>共{{OrderInfo.daysNumber}}晚</span>
      </van-col>

      <van-col span="2" offset="1" style="margin-top:3%">
        <span>￥{{OrderInfo.expense}}</span>
      </van-col>

      <van-col @click="clickDestine" span="7" offset="2" style="margin-top:3%">
        <span>立即预定 →</span>
      </van-col>
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
      zoom: 14,
      calendar1Show: true,
      houseDitail: {
        id: "",
        userId: "",
        userPhone:"",
        name: "",
        address: "",
        location: "",
        describe: "",
        equipments: "",
        houseTitleImg: "",
        houseImgs: "",
        grade: "",
        price: -1,
        rentedDate: []
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
      },
      dateMsg: "请选择入住日期",
      dateTextBtnShow: false,
      dateTextShow: false,
      OrderInfo: {
        dates: [],
        expense: 0,
        daysNumber: 0
      }
    }
  },

  created() {
    this.getHouseDitail()
  },
  watch: {
    newName(val) {
      let addresss = this.houseDitail.address.split(",")
      this.center.lng = addresss[0]
      this.center.lat = addresss[1]
      this.zoom = 16
    }
  },
  computed: {
    newName() {
      return this.houseDitail.address
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
    getAllDate(begin, end) {
      var arr = []
      var ab = begin.split("-")
      var ae = end.split("-")
      var db = new Date()
      db.setUTCFullYear(ab[0], ab[1] - 1, ab[2])
      var de = new Date()
      de.setUTCFullYear(ae[0], ae[1] - 1, ae[2])
      var unixDb = db.getTime() - 24 * 60 * 60 * 1000
      //最后一天的为离开房屋的时间，计算在日期内
      var unixDe = de.getTime() - 48 * 60 * 60 * 1000
      for (var k = unixDb; k <= unixDe; ) {
        k = k + 24 * 60 * 60 * 1000
        let date = new Date(parseInt(k))
        let dateStr =
          date.getFullYear() +
          "-" +
          (date.getMonth() + 1) +
          "-" +
          date.getDate()
        arr.push(dateStr)
      }
      return arr
    },
    dataOnChange(date) {
      let selectDates = date.map(item => item.format("YYYY-MM-DD"))

      if (selectDates.length > 0) {
        this.OrderInfo.dates = selectDates
        this.dateMsg = "您选择的入住日期有："
        this.dateTextBtnShow = true

        this.OrderInfo.daysNumber = this.OrderInfo.dates.length

        this.OrderInfo.expense =
          this.OrderInfo.daysNumber * this.houseDitail.price
      }
    },
    getHouseDitail() {
      this.houseId = this.$route.query.id
      this.$axios.get("/house/" + this.houseId).then(response => {
        let data = response.data
        if (data != null) {
          this.houseDitail.id = data.id
          this.houseDitail.userId = data.userId
          this.houseDitail.name = data.name
          this.houseDitail.userPhone=data.userPhone
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

          //房屋已经租借日期的处理
          if (data.date != null && data.date.indexOf(",") != -1) {
            this.houseDitail.rentedDate = data.date.split(",")
          } else {
            if (data.date != null) {
              this.houseDitail.rentedDate.push(data.date)
            }
          }

          this.houseDitail.grade = data.grade
          this.houseDitail.price = data.price
          this.houseDitail.rentedDate = data.rentedDate

          this.houseDitail.expense = 0
          this.houseDitail.beginDate = "请选择时间"
        } else {
          alert("系统异常，请重试")
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
      // let addresss = this.houseDitail.address.split(",")
      // this.center.lng = addresss[0]
      // this.center.lat = addresss[1]
      // this.zoom = 18
    },
    clickBack() {
      this.$router.go(-1) //返回上一层
    },
    clickDestine() {
      //判断是否有选择日期
      if (this.OrderInfo.dates.length < 1) {
        alert("请选择要预定的时间")
        return
      }

      //判断日期是否是过时日期
      let currentDate = new Date()
      let year = currentDate.getFullYear()
      let month = currentDate.getMonth() + 1
      let day = currentDate.getDate()
      let dateStr = year + "-" + month + "-" + day
      for (let i = 0; i < this.OrderInfo.dates.length; i++) {
        let selectTime = this.OrderInfo.dates[i].split("-")
        let selectYear = parseInt(selectTime[0])
        let selectMon = parseInt(selectTime[1])
        let selectDay = parseInt(selectTime[2])
        if (
          selectYear >= year &&
          selectMon >= parseInt(month) &&
          selectDay >= parseInt(day)
        ) {
          this.$router.push({
            name: "ConsumerOrder",
            params: {
              houseId: this.houseDitail.id,
              houseTitleImg: this.houseDitail.houseImgs[0],
              housePrice: this.houseDitail.price,
              orderExpense: this.OrderInfo.expense,
              orderDate: this.OrderInfo.dates,
              orderDaysNum: this.OrderInfo.daysNumber
            }
          })
        } else {
          alert("请选择正确时间（今天的日期是：" + dateStr + ")")
          return
        }
      }
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
.RetendPoint {
  width: 6px;
  height: 6px;
  background-color: red;
  border-radius: 50%;
  position: absolute;
  top: 10%;
  right: 14%;
}
.RetendText1 {
  position: absolute;
  top: 0%;
  right: 10%;
  font-size: 5px;
  opacity: 0.5;
}
.RetendText2 {
  position: absolute;
  top: 30%;
  right: 10%;
  font-size: 5px;
  opacity: 0.5;
}
</style>
