<template>
  <div>
    <van-row>
      <a href @click.prevent="goBack">
        <span style="color:blue">《返回首页</span>
      </a>
    </van-row>

    <van-row>
      <van-cell-group>
        <van-field v-model="addHouseForm.name" label="房屋名：" placeholder="请输入房屋名" required />

        <div style="font-size:14px;padding-left:1%;padding-top:2%">
          <span style="color:red;">*</span>
          <span>房屋首图（房间的宣传图）：</span>
        </div>
        <van-uploader
          v-model="titleImg"
          :max-count="1"
          style="padding-top:2%;padding-left:2%"
          :before-read="beforeRead"
          :after-read="titleImgUp"
          @delete="titleImgDelete"
        />

        <div style="font-size:14px;padding-left:1%;padding-top:2%">
          <span style="color:red;">*</span>
          <span>房屋内部图片（最多上传6张）：</span>
        </div>
        <van-uploader
          v-model="imgList"
          :max-count="6"
          style="padding-top:2%;padding-left:2%"
          :before-read="beforeRead"
          :after-read="houseImgsUp"
          @delete="houseImgsDelete"
        />

        <van-field
          v-model="addHouseForm.location"
          label="地址："
          placeholder="点击选择房屋地址"
          @focus="LocationViewShow=true"
          required
        />

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
              v-model="addHouseForm.location"
              label="地址："
              placeholder="点击选择房屋地址（推荐使用右侧自动定位功能）"
              right-icon="location-o"
              @click="MapSugges"
              @click-right-icon="MyLocation"
              required
            />
          </van-row>
          <div
            id="searchResultPanel"
            style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;position:static"></div>
          <van-row type="flex" justify="center">
            <van-col span="20">
              <baidu-map id="l-map" class="map" @ready="MapReady" :center="center" :zoom="zoom"></baidu-map>
            </van-col>
          </van-row>
        </van-dialog>

        <van-field
          v-model="addHouseForm.describe"
          label="房屋介绍："
          placeholder="房屋的介绍"
          required
          rows="5"
          type="textarea"
          maxlength="200"
        />
      </van-cell-group>
      <van-divider style=";padding-left:2%" />
    </van-row>
    <div style="padding-left:2%">
      <van-row>
        <span>房屋可提供的设备：</span>
      </van-row>
      <van-row style="padding-top:2%">
        <van-col span="8">
          <span>热水：</span>
          <van-switch size="25" v-model="equipments.reshui" />
        </van-col>

        <van-col span="8">
          <span>浴液：</span>
          <van-switch size="25" v-model="equipments.yuye" />
        </van-col>

        <van-col span="8">
          <span>牙具：</span>
          <van-switch size="25" v-model="equipments.yaju" />
        </van-col>

        <van-col span="8">
          <span>毛巾：</span>
          <van-switch size="25" v-model="equipments.maojin" />
        </van-col>

        <van-col span="8">
          <span>浴缸：</span>
          <van-switch size="25" v-model="equipments.yugang" />
        </van-col>

        <van-col span="8">
          <span>纸巾：</span>
          <van-switch size="25" v-model="equipments.zhijin" />
        </van-col>

        <van-col span="8">
          <span>空调：</span>
          <van-switch size="25" v-model="equipments.kongtiao" />
        </van-col>

        <van-col span="8">
          <span>暖气：</span>
          <van-switch size="25" v-model="equipments.nuanqi" />
        </van-col>

        <van-col span="8">
          <span>电视：</span>
          <van-switch size="25" v-model="equipments.dianshi" />
        </van-col>

        <van-col span="8">
          <span>Wifi：</span>
          <van-switch size="25" v-model="equipments.wifi" />
        </van-col>

        <van-col span="8">
          <span>冰箱：</span>
          <van-switch size="25" v-model="equipments.bingxiang" />
        </van-col>

        <van-col span="8">
          <span>洗衣机：</span>
          <van-switch size="25" v-model="equipments.xiyiji" />
        </van-col>

        <van-col span="8">
          <span>停车场：</span>
          <van-switch size="25" v-model="equipments.tingchechang" />
        </van-col>
      </van-row>
    </div>
    <van-divider style=";padding-left:2%" />
    <van-row>
      <van-field v-model="addHouseForm.price" label="房屋租价：￥" placeholder="请输入租客住一晚所需要的金额" required />
    </van-row>
    <van-row type="flex" justify="center">
      <van-col>
        <van-button round type="info" @click="addHouseFormSubmit">提交</van-button>
      </van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
      titleImg: [],
      imgList: [],
      addHouseForm: {
        name: "",
        location: "",
        address: "",
        describe: "",
        price: "",
        titleImg: "",
        imgList: [],
        equipmentsList: []
      },
      LocationViewShow: false,
      center: {
        lng: 0,
        lat: 0
      },
      zoom: 3
    }
  },
  mounted() {},
  methods: {
    goBack(){
      this.$router.push('/masterHome')
    },
    beforeRead(file) {
      if (file.type !== "image/jpeg"&&file.type !== "image/png") {
        alert("请上传 jpg或者png 格式图片")
        return false
      }
      return true
    },
    titleImgUp(file) {
      let formData = new FormData()
      formData.append("file", file.file)

      this.$axios
        .post("/houseTitleImg", formData, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(response => {
          let data = response.data
          if (data != "error") {
            this.addHouseForm.titleImg = data
          } else {
            alert("系统异常")
          }
        })
    },
    houseImgsUp(file) {
      let formData = new FormData()
      formData.append("file", file.file)

      this.$axios
        .post("/houseImgs", formData, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(response => {
          let data = response.data
          this.addHouseForm.imgList.push(data)
        })
    },
    titleImgDelete() {
      let formData = new FormData()
      formData.append("filePath", this.addHouseForm.titleImg)

      this.$axios.post("/deleteHouseImg", formData).then(response => {
        this.addHouseForm.titleImg = ""
      })
      return false
    },
    houseImgsDelete(file, name) {
      var index = name.index
      let url = this.addHouseForm.imgList[index]
      let formData = new FormData()
      formData.append("filePath", url)
      this.$axios.post("/deleteHouseImg", formData).then(response => {
        if (response.data == "success") {
          let newImgList = new Array()
          for (let i = 0, j = 0; i < this.addHouseForm.imgList.length; i++) {
            if (i != index) {
              newImgList[j] = this.addHouseForm.imgList[i]
              j++
            }
          }
          this.addHouseForm.imgList = newImgList
        } else {
          alert("系统异常")
        }
      })
    },
    MapReady() {
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        if (geolocation.getStatus() == BMAP_STATUS_SUCCESS) {
          this.center.lat = r.point.lat
          this.center.lng = r.point.lng
          this.zoom = 16
        } else {
          alert("自动定位失败，请手动添加")
        }
      })
    },
    MyLocation() {
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        if (geolocation.getStatus() == BMAP_STATUS_SUCCESS) {
          let myGeo = new BMap.Geocoder()
          myGeo.getLocation(r.point, result => {
            if (result) {
              this.addHouseForm.location = result.address
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
        this.addHouseForm.location = myValue
        
        //隐藏ac
        let elm = Array.prototype.slice.call(document.getElementsByClassName('tangram-suggestion-main'));
                elm.forEach(function (v, i) {
                    v.style.zIndex = -1;
                    v.style.visibility = 'hidden';
                });
                
        var myGeo = new BMap.Geocoder()
        myGeo.getPoint(
        this.addHouseForm.location,
        point => {
          if (point) {
            this.center.lat = point.lat
            this.center.lng = point.lng
            this.zoom = 16
          } else {
            this.addHouseForm.location=''
            alert("地址选择错误,请重新选择!")
          }
        },
        this.addHouseForm.location.slice(0, 3)
      )
      })
    },
    MapClickCancel(){
      this.addHouseForm.location=''
    },
    getHousePoint() {
      var myGeo = new BMap.Geocoder('l-map')
      myGeo.getPoint(
        this.addHouseForm.location,
        point => {
          if (point) {
            this.addHouseForm.address = point.lng + "," + point.lat
          } else {
            alert("地址选择错误!")
          }
        },
        this.addHouseForm.location.slice(0, 3)
      )
    },
    addHouseFormSubmit() {
      let equipments = this.equipments
      for (let equipment in equipments) {
        if (
          equipments[equipment] == true &&
          this.addHouseForm.equipmentsList.indexOf(equipment) == -1
        ) {
          this.addHouseForm.equipmentsList.push(equipment)
        }
      }

      for(let item in this.addHouseForm){
        if(this.addHouseForm[item]==""||this.addHouseForm[item]==0){
          alert('房屋资料不全，请认真填写')
          return
        }
      }

      this.$axios.post("/house", this.addHouseForm)
                  .then(response => {
                    let data = response.data
                    if(data.msg=='success'){
                      alert('发布房屋成功，等待管理员审核')
                      this.$router.push('/masterHome')
                    }else{
                      alert('系统异常，发布失败')
                    }
                  })
    }
  }
}
</script>
  
<style >
.map {
  height: 300px;
}
.tangram-suggestion-main {
  z-index: 9999;
}
</style>
