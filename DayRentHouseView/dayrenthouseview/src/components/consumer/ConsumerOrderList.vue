<template>
  <div class="all">
    <van-row type="flex" justify="center" style="margin-top:2%">我的订单</van-row>
    <div v-for="(order,index) in orderList" :key="index">
      <div class="orderItem">
        <van-row @click="clickOrderItem(order.houseId)">
          <van-col offset="1" span="8">
            <van-image fit="fill" height="150" :src="getImg(order.houseTitleImg)" />
          </van-col>
          <div style="font-size:14px;">
            <van-col offset="1" span="14">
              <span>房屋名：{{order.houseName}}</span>
            </van-col>
            <van-col offset="1" span="14">
              <span>屋主电话：{{order.userPhone}}</span>
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
            <van-step>住房评价</van-step>
          </van-steps>
        </van-row>
        <van-row type="flex" justify="center" style="font-size:14px">
          订单状态：
          <span style="color:#DAA520">{{order.orderTip}}</span>
        </van-row>
      </div>
      <van-row style="padding-top:0.8%">
        <van-col offset="18">
          <van-button
            @click="deleteOrder(order.orderId)"
            v-if="order.orderState==='已下单'"
            plain
            type="warning"
            size="small"
          >撤回订单</van-button>
        </van-col>
        <van-col offset="17">
          <van-button
            @click="clcikBoard(order.orderId)"
            v-if="order.orderState==='已接单'"
            plain
            type="warning"
            size="small"
          >已入住房屋</van-button>
        </van-col>
        <van-col offset="19">
          <van-button
            @click="clickComment(order.orderId,order.houseId,order.houseTitleImg,order.houseName,order.orderPrice)"
            v-if="order.orderState==='已完成'"
            plain
            type="warning"
            size="small"
          >评价</van-button>
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
    getOrders() {
      this.$axios.get("/consumer/consumerOrders").then(response => {
        let data = response.data
        if (data != null) {
          for (let i = 0; i < data.length; i++) {
            data[i].activeColor = "#9ACD32"

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
                data[i].active = 1
                data[i].orderTip = "屋主拒绝了本次订单"
                data[i].activeColor = "#EE0000"
                break
              case "已入住":
                data[i].active = 2
                data[i].orderTip = "租客住宿中"
                break
              case "已完成":
                data[i].active = 3
                data[i].orderTip = "对这次住房体验进行评价吧~"
                break
              case "已评价":
                data[i].active = 4
                data[i].orderTip = "订单完成"
                break
            }
          }
          this.orderList = data
        } else {
          alert("您当前没有订单，快去挑选自己喜爱的房屋吧！")
        }
      })
    },
    clickOrderItem(houseId) {
      this.$router.push({ path: "/houseDetail", query: { id: houseId } })
    },
    deleteOrder(orderId) {
      this.$dialog
        .confirm({
          title: "撤回订单",
          message: "屋主在未接单之前，您可以撤回此订单，确定要继续吗？"
        })
        .then(() => {
          this.$axios.delete("/consumer/consumerOrder/" + orderId).then(response => {
            if (response.data == "success") {
              alert("撤回成功")
              this.getOrders()
            } else {
              alert("系统异常！")
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
    clcikBoard(orderId) {
      this.$dialog
        .confirm({
          title: "入住房屋",
          message:
            "请在您已到达房屋后，点击此按钮方便系统对订单状态的及时更新（如果没有点击，将会在离开房屋时间到后自动更新订单信息）"
        })
        .then(() => {
          let formData = {}
          formData.orderId = orderId
          formData.orderState = "已入住"

          this.$axios.put("/consumer/consumerOrder",formData).then(response => {
            if (response.data == "success") {
              alert("操作成功")
              this.getOrders()
            } else {
              alert("系统异常！")
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
    clickComment(orderId,houseId,houseTitleImg,houseName,orderExpense){
      this.$router.push({name:'ComsumerComment',
                        params :
                         { orderId: orderId,
                           houseId:houseId,
                           houseTitleImg:houseTitleImg,
                           houseName:houseName,
                           orderExpense:orderExpense
                         } })
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