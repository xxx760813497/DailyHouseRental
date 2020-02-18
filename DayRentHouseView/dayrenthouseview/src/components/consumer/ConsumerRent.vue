<template>
  <div style="padding-top:5%">
    <div  id="selectHouseDiv">
      <van-row type="flex" justify="center">
        <span>欢迎使用共享日租房系统</span>
      </van-row>
      <van-row type="flex" justify="center">
        <span>
          点击
          <span style="color:#FFC125">搜索</span>，选择你心仪的房屋吧
        </span>
      </van-row>
      <div class="searchDiv" style="margin-top:5%">
        <!-- <van-row type="flex" justify="center" style="padding-top:3%">
          <van-col span="18" style="border-bottom: 1px solid #ffb90f;">
            <van-field value="泉州" label="城市" left-icon="bar-chart-o" />
          </van-col>
        </van-row> -->
        <van-row type="flex" justify="center">
          <van-col span="20" style="border-bottom: 1px solid #ffb90f;">
            <van-field v-model="selectForm.date" placeholder="点击选择入住时间" label="入住时间" left-icon="notes-o" @click="dateSecltPopShow=true" />
          </van-col>
        </van-row>

        <van-popup v-model="dateSecltPopShow" round>
          <inlineCalendar  mode="during" :minDate="new Date()" @change="dateOnChange" style="width:300px" />
          <van-row  type="flex" justify="center">
            <van-button type="default" @click="dateSecltPopShow=false">确认</van-button>
          </van-row>
        </van-popup>

        <van-row type="flex" justify="center">
          <van-col span="20">
            <van-field
              v-model="selectForm.location"
              placeholder="点击选择住房地点"
              label="地点"
              left-icon="location-o"
              @focus="LocationViewShow=true"
            />
          </van-col>
        </van-row>

        <van-row type="flex" justify="center" style="padding-bottom:5%">
          <van-col span="10" offset="3">
            <van-button round type="warning" @click="clickSelectHouse">搜索房源</van-button>
          </van-col>
        </van-row>
      </div>

      <van-dialog
        v-model="LocationViewShow"
        title="地址选择"
        show-cancel-button
        @confirm="getHousePoint"
        @cancel="MapClickCancel"
      >
        <van-row>
          <van-field
            id="suggestId"
            v-model="selectForm.location"
            label="地址："
            placeholder="住房地点（右侧可定位当前位置）"
            right-icon="location-o"
            @click="MapSugges"
            @click-right-icon="myLocation"
            required
          />
        </van-row>
        <div
          id="searchResultPanel"
          style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;position:static"
        ></div>
        <van-row type="flex" justify="center">
          <van-col span="20">
            <baidu-map @ready="MapReady" class="map" :center="center" :zoom="zoom"></baidu-map>
          </van-col>
        </van-row>
      </van-dialog>

      <van-row style="margin-top:5%">
        <van-col offset="1">
          <span>*您当前位置附近的房屋</span>
          <baidu-map @ready="getNearHouse"></baidu-map>
        </van-col>
      </van-row>

      <van-row v-show="loadingShow" type="flex" justify="center" style="margin-top:10%">
        <van-loading vertical type="spinner" color="#1989fa" size="30">正在查找附近的房屋</van-loading>
      </van-row>

      <div v-for="(house,index) in nearHousesList" :key="index" style="margin-top:5%">
        <van-row style="background-color:#F4F4F4;margin-top:2%" @click="getHouseDetail(house.id)">
          <van-col span="8">
            <van-image fit="fill" width="120" height="140" :src="getImg(house.houseTitleImg)" />
          </van-col>
          <van-col span="15" offset="1" style="font-size:14px">
            <span>房名：{{house.name}}</span>
            <br />
            <span>地址：{{house.location}}</span>
            <br />
            <span>价格：￥{{house.price}}</span>
            <br />
            <span>本房订单成交量：{{house.orderQuantity}}</span>
            <br />
            <span>评分：{{house.grade}}</span>
            <br />
            <span>距离当前位置：{{house.distance/1000}}公里</span>
          </van-col>
        </van-row>
      </div>
    </div>

   
  </div>
</template>

<script>
export default {
  data() {
    return {
      dateSecltPopShow:false,
      date:"",
      center: {
        lng: 0,
        lat: 0
      },
      zoom: 16,
      selectForm: {
        location: "",
        address: "",
        round: 10000,
        date:"",
        allDates:[]
      },
      LocationViewShow: false,
      loadingShow: true,
      currentForm: {
        address: "",
        currentLocation: "",
        allDates:[],
        round: 10000
      },
      nearHousesList: [],

    }
  },
  methods: {
    dateOnChange(date){
      let selectDates = date.map(item => item.format("YYYY-MM-DD"))
      if(selectDates.length>1){
        this.selectForm.date=selectDates[0].substring(6)+'入住，'+selectDates[1].substring(6)+'离开'
        this.selectForm.allDates=this.getAllDate(selectDates[0],selectDates[1]);
      }
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
    myLocation() {
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        if (geolocation.getStatus() == BMAP_STATUS_SUCCESS) {
          let myGeo = new BMap.Geocoder()
          myGeo.getLocation(r.point, result => {
            if (result) {
              this.selectForm.location = result.address
              this.getHousePoint()
            }
          })
        } else {
          alert("自动定位失败，请手动添加")
        }
      })
    },
    MapSugges() {
      let ac = new BMap.Autocomplete({ input: "suggestId" })
      let myValue
      ac.addEventListener("onconfirm", e => {
        let _value = e.item.value
        myValue =
          _value.province +
          _value.city +
          _value.district +
          _value.street +
          _value.business
        this.selectForm.location = myValue

        //隐藏ac
        let elm = Array.prototype.slice.call(
          document.getElementsByClassName("tangram-suggestion-main")
        )
        elm.forEach(function(v, i) {
          v.style.zIndex = -1
          v.style.visibility = "hidden"
        })

        var myGeo = new BMap.Geocoder()
        myGeo.getPoint(
          this.selectForm.location,
          point => {
            if (point) {
              this.center.lat = point.lat
              this.center.lng = point.lng
              this.selectForm.address = point.lng + "," + point.lat
            } else {
              this.selectForm.location = ""
              this.selectForm.address = ""
              alert("地址选择错误,请重新选择!")
            }
          },
          this.selectForm.location.slice(0, 3)
        )
      })
    },
    MapReady() {
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        if (geolocation.getStatus() == BMAP_STATUS_SUCCESS) {
          this.center.lat = r.point.lat
          this.center.lng = r.point.lng
          this.selectForm.address = r.point.lng + "," + r.point.lat
        } else {
          alert("自动定位失败，请手动添加")
        }
      })
    },
    MapClickCancel() {
      this.selectForm.location = ""
      this.selectForm.address = ""
    },
    getHousePoint() {
      var myGeo = new BMap.Geocoder("l-map")
      myGeo.getPoint(
        this.selectForm.location,
        point => {
          if (point) {
            this.selectForm.address = point.lng + "," + point.lat
          } else {
            alert("地址选择错误!")
          }
        },
        this.selectForm.location.slice(0, 3)
      )
    },
    clickSelectHouse() {
      if (this.selectForm.address.length > 0&&this.selectForm.allDates.length>0) {
        this.$router.push({ path: "/consumerHouseList", 
              query: { address:this.selectForm.address,
                        date:this.selectForm.date,
                        allDates:this.selectForm.allDates } })
      } else {
        alert("请补充住房信息")
      }
    },
    getImg(imgPath) {
      if (imgPath == null) {
        return
      }
      const img = imgPath.substring(9)
      let path = "@/assets/"
      return require("@/assets/" + img)
    },
    getNearHouse() {
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        if (geolocation.getStatus() == BMAP_STATUS_SUCCESS) {
          this.currentForm.address = r.point.lng + "," + r.point.lat
          let currentDate=new Date()
          let year=currentDate.getFullYear
          let mon=currentDate.getMonth+1
          let day=currentDate.getDate
          let date=year+"-"+mon+"-"+day
          this.currentForm.allDates.push(date)
          this.$axios
            .post("/consumer/selectHouseByAddress", this.currentForm)
            .then(response => {
              this.loadingShow = false
              this.nearHousesList = response.data
            })
        } else {
          alert("自动定位失败")
        }
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
    getHouseDetail(houseId) {
      this.$router.push({ path: "/houseDetail", query: { id: houseId } })
    }
  },
  created() {
    
  }
}
</script>

<style scoped>
.searchDiv {
  padding-top:5%;
  width: 90%;
  margin-left: 5%;
  border: 1px solid #ffb90f;
  border-radius: 50px/100px;
}
.timeDivClass{
  padding-top:3%;
  height: 30px;
  border: 2px solid#EEDFCC;
  border-radius: 10px/10px;
}
</style>