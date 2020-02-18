<template>
  <div class="divClass">
    <van-nav-bar title="提交订单" left-text="返回" left-arrow @click-left="goBack" />
    <van-row type="flex" justify="center" style="margin-top:2%">如家小店</van-row>
    <van-row style="padding-top:5%">
      <van-image
        width="100%"
        height="12rem"
        fit="cover"
        :src="getImg(this.houseDetail.houseTitleImg)"
      />
    </van-row>

    <van-row type="flex" justify="center" style="margin-top:3%">
      <span>屋主：小明</span>
    </van-row>

    <van-divider />
    <van-row style="margin-top:3%">
      <van-col offset="2">
        入住信息：
        <span></span>
      </van-col>
    </van-row>

    <van-row>
      <van-col offset="1" span="22" class="orderEdit">
        <van-field required label="入住人" v-model="orderInfo.orderUser" placeholder="填写房屋实际入住人姓名" />
        <van-field required label="手机号" v-model="orderInfo.orderUserTel" placeholder="填写入住人的手机号码" />
        <van-field label="到店时间" readonly value="14：00后办理入住" />
        <van-field label="离店时间" readonly value="第二天中午12：00离开" />
        <van-field readonly value="（连租多天则最后一天的第二天12：00前离店）" />
        <van-field
          label="住房日期"
          :right-icon="datesTxtRightIcon"
          @click-right-icon="AllDateTxtshow=true"
          readonly
          :value="houseDateTxt"
        />
        <van-field label="住房天数" readonly :value="houseDaysNumTxt" />
        <van-field label="住房价格" readonly :value="housePriceTxt" />
      </van-col>
    </van-row>

    <van-popup v-model="AllDateTxtshow">
      入住时间为：
      <div v-for="(date,index) in orderInfo.dates" :key="index">
        <span>{{date}}</span>
        <br />
      </div>
    </van-popup>

    <van-row>
      <van-submit-bar style="position:relative;" @submit="clickSubmit" :price="orderInfo.expense*100" button-text="提交订单" />
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      houseDetail: {
        id: -1,
        houseTitleImg: "",
        housePrice: -1
      },
      houseDateTxt: "",
      datesTxtRightIcon: "",
      houseAllDateTxt: "",
      AllDateTxtshow: false,

      houseDaysNumTxt: "",
      housePriceTxt: "",

      orderInfo: {
        houseId:-1,
        dates: [],
        daysNum:-1,
        expense: -1,
        orderUser: "",
        orderUserTel: ""
      }
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    getImg(imgPath) {
      if (imgPath == null) {
        return
      }
      const img = imgPath.substring(9)
      let path = "@/assets/"
      return require("@/assets/" + img)
    },
    clickSubmit(){
      if(this.orderInfo.orderUser==null||this.orderInfo.orderUser.length==0){
        alert('请补充实际居住人姓名')
        return
      }

      if(this.orderInfo.orderUserTel==null||this.orderInfo.orderUserTel.length==0){
        alert('请补充居住人电话')
        return
      }

      this.orderInfo.houseId=this.houseDetail.id
      this.$axios.post("/consumer/consumerOrder", this.orderInfo)
                  .then(response => {
                    let data=response.data
                    if(data=='ok'){
                      alert('订单发出成功！等待屋主处理')
                      this.$router.push('/consumerHome')
                    }
                  }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    }
  },
  created() {
    let info = this.$route.params
    this.houseDetail.id = info.houseId
    this.houseDetail.houseTitleImg = info.houseTitleImg
    this.houseDetail.price = info.housePrice

    this.orderInfo.dates = info.orderDate
    this.orderInfo.daysNum = info.orderDaysNum
    this.orderInfo.expense = info.orderExpense

    this.houseDaysNumTxt = "共" + this.orderInfo.daysNum + "天"
    this.housePriceTxt = "￥" + this.houseDetail.price + "/天"

    if (this.orderInfo.dates != null) {
      for (let i = 0; i < 2 && i < this.orderInfo.dates.length; i++) {
        this.houseDateTxt += this.orderInfo.dates[i]
        if (i < this.orderInfo.dates.length - 1) {
          this.houseDateTxt += ","
          this.datesTxtRightIcon = ""
        }
      }
      if (this.orderInfo.dates.length > 2) {
        this.houseDateTxt += "..."
        this.datesTxtRightIcon = "more-o"
      }
    }
  }
}
</script>

<style  scoped>
.orderEdit {
  margin-top: 2%;
  background-color: #cccccc;
}
</style>