<template>
  <div class="all">
    <van-row type="flex" justify="center" style="padding-top:5%">我的订单</van-row>
    <div v-for="(order,index) in orderList" :key="index">
      <div class="orderItem">
        <van-row @click="clickOrderItem(order.houseId)">
          <van-col offset="1" span="8">
            <van-image fit="fill" height="140" :src="getImg(order.houseTitleImg)" />
          </van-col>
          <div style="font-size:14px;">
            <van-col offset="1" span="14">
              <span>房屋名：{{order.houseName}}</span>
            </van-col>
            <van-col offset="1" span="14">
              <span>入住日期：{{order.boardDate[0]}}</span>
            </van-col>
            <van-col offset="1" span="14">
              <span>入住天数：{{order.boardDate.length}}</span>
            </van-col>
            <van-col offset="1" span="14">
              <span>订单金额：￥{{order.orderPrice}}</span>
            </van-col>
            <van-col offset="1" span="14">
              <span>入住人：{{order.boardUserName}}</span>
            </van-col>
            <van-col offset="1" span="14">
              <span>入住人电话：{{order.boardUserTel}}</span>
            </van-col>
            <van-col offset="1" span="14">
              <span>下单时间：{{order.orderProduceTime}}</span>
            </van-col>
          </div>
        </van-row>
        <van-row>
          <van-steps :active="order.active" :active-color="order.activeColor">
            <van-step>租客下单</van-step>
            <van-step>屋主接单</van-step>
            <van-step>租客入住</van-step>
            <van-step>订单完成</van-step>
          </van-steps>
        </van-row>
        <van-row type="flex" justify="center" style="font-size:14px">
          订单状态：
          <span style="color:#DAA520">{{order.orderTip}}</span>
        </van-row>
      </div>
      <van-row style="padding-top:0.8%">
        <van-col offset="13">
          <van-button
            @click="refuseOrder(order.orderId)"
            v-if="order.orderState==='已下单'"
            plain
            type="danger"
            size="small"
          >拒绝订单</van-button>
        </van-col>
        <van-col offset="1">
          <van-button
            @click="receiveOrder(order.orderId)"
            v-if="order.orderState==='已下单'"
            plain
            type="primary"
            size="small"
          >接收订单</van-button>
        </van-col>
      </van-row>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      orderList: []
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
    getOrders(){
    this.$axios.get("/master/masterOrders").then(response => {
      let data = response.data
      if (data != null) {
        for (let i = 0; i < data.length; i++) {

          data[i].activeColor='#9ACD32'

          let order = data[i]
          if (order.boardDate.indexOf(",") != -1) {
            let datas = order.boardDate.split(",")
            data[i].boardDate = datas
          } else {
            let dates = new Array()
            dates.push(order.boardDate)
            data[i].boardDate = dates
          }

          switch (order.orderState) {
            case "已下单":
              data[i].active = 0
              data[i].orderTip = "等待屋主处理"
              break
            case "已接单":
              data[i].active = 1
              data[i].orderTip = "等待租客入住"
              break
            case "已拒绝":
              data[i].active=1
              data[i].orderTip = "屋主拒绝了本次订单"
              data[i].activeColor='#EE0000'
              break
            case "已入住":
              data[i].active = 2
              data[i].orderTip = "租客住宿中"
              break
            case "已完成":
              data[i].active = 3
              data[i].orderTip = "订单已完成"
              break
            case "已评价":
                data[i].active = 3
                data[i].orderTip = "订单已作出评价"
                break
          }
        }
        this.orderList = data
      } else {
        alert("您当前没有订单，快去挑选自己喜爱的房屋吧！")
      }
    }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
    clickOrderItem(houseId) {
      this.$router.push({ path: "/houseDetail", query: { id: houseId } })
    },
    receiveOrder(orderId) {
      this.$dialog.confirm({
        title: "接收订单",
        message: "在接收订单后，租客将在约定日期到达房屋住宿"
      })
        .then(() => {
          let formData={}
          formData.orderId=orderId
          formData.orderState='已接单'
          this.$axios.post('/master/masterOrder',formData)
                        .then(response=>{
                          if(response.data=='success'){
                            alert('接单成功')
                            this.getOrders()
                          }else{
                            alert('系统异常！')
                          }
                        }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
        })
        .catch(() => {
          // on cancel
        })
    },
    refuseOrder(orderId) {
      this.$dialog.confirm({
        title: "拒绝订单",
        message: "由于某些原因，无法继续本次订单的完成"
      })
        .then(() => {
          let formData={}
          formData.orderId=orderId
          formData.orderState='已拒绝'
          this.$axios.post('/master/masterOrder',formData)
                        .then(response=>{
                          if(response.data=='success'){
                            alert('拒绝成功')
                            this.getOrders()
                          }else{
                            alert('系统异常！')
                          }
                        }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
        })
        .catch(() => {
          // on cancel
        })
    }
  },
  created() {
    this.getOrders()
  }
}
</script>

<style  scoped>
.orderItem {
  margin-top: 5%;
  padding-top: 2%;
  width: 90%;
  margin-left: 5%;
  border: 1px solid #e3e3e3;
  border-radius: 10px/10px;
}
</style>