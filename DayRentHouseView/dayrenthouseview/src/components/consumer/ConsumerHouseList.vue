<template>
  <div>
    <div id="houseListDiv">
      <van-row @click="dateSecltPopShow=true" class="timeDivClass" type="flex" justify="center">
        <van-col>
          <span>{{selectForm.date}}</span>
        </van-col>
      </van-row>

      <van-popup v-model="dateSecltPopShow" round>
          <inlineCalendar  mode="during" :minDate="new Date()" @change="dateOnChange" style="width:300px" />
          <van-row  type="flex" justify="center">
            <van-button type="default" @click="clickTimeOK">确认</van-button>
          </van-row>
      </van-popup>

      <van-dropdown-menu  active-color="#ee0a24" style="margin-top:3%">
        <van-dropdown-item @change="clickItem1" v-model="houseListDiv.value1" :options="houseListDiv.option1" />
        <van-dropdown-item @change="clickItem2" v-model="houseListDiv.value2" :options="houseListDiv.option2" />
      </van-dropdown-menu>

      <div v-for="(house,index) in houseListDiv.houseList" :key="index">
        <van-row style="background-color:#F4F4F4;margin-top:2%" @click="getHouseDetail(house.id)">
          <van-col span="8">
            <van-image fit="fill" width="120" height="140" :src="getImg(house.houseTitleImg)" />
          </van-col>
          <van-col span="15" offset="1">
            <span>房名：{{house.name}}</span>
            <br />
            <span>地址：{{house.location}}</span>
            <br />
            <span>价格：{{house.price}}</span>
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
      selectForm: {
        location: "",
        address: "",
        round: 10000,
        date:"",
        allDates: []
      },
      houseListDiv: {
        value1: 0,
        value2: 0,
        option1: [

          { text: "10km以内", value: 0 },
          { text: "5km以内", value: 1 },
          { text: "2km以内", value: 2 }
          
        ],
        option2: [
          { text: "默认排序", value: 0 },
          { text: "价格降序", value: 1 },
          { text: "价格升序", value: 2 }
        ],
        houseList: []
      }
    }
  },
  computed: {},
  watch: {},
  methods: {

    dateOnChange(date){
      let selectDates = date.map(item => item.format("YYYY-MM-DD"))
      if(selectDates.length>1){
        this.selectForm.date=selectDates[0].substring(6)+'入住，'+selectDates[1].substring(6)+'离开'
        this.selectForm.allDates=this.getAllDate(selectDates[0],selectDates[1]);
      }
    },
    clickTimeOK(){
      this.$axios
          .post("/selectHouseByAddress", this.selectForm)
          .then(response => {
            let data=response.data
            this.houseListDiv.houseList=data
            dateSecltPopShow=false
          })
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
    getImg(imgPath) {
      if (imgPath == null) {
        return
      }
      const img = imgPath.substring(9)
      let path = "@/assets/"
      return require("@/assets/" + img)
    },
    clickItem1(value){
      switch(value){
        case 0:
          this.selectForm.round=10000
          this.$axios
          .post("/selectHouseByAddress", this.selectForm)
          .then(response => {
            let data=response.data
            this.houseListDiv.houseList=data
          })
          break
        case 1:
          this.selectForm.round=5000
          this.$axios
          .post("/selectHouseByAddress", this.selectForm)
          .then(response => {
            let data=response.data
            this.houseListDiv.houseList=data
          })
          break
        case 2:
          this.selectForm.round=2000
          this.$axios
          .post("/selectHouseByAddress", this.selectForm)
          .then(response => {
            let data=response.data
            this.houseListDiv.houseList=data
          })
          break
        default:
          alert('系统异常')
          break
      }



    },
    clickItem2(value){
      
      let tempMinList=[]
      switch(value){
        case 0:
          for(let i=0;i<this.houseListDiv.houseList.length;i++){
            let tempmin=i
            for(let j=i;j<this.houseListDiv.houseList.length;j++){
                if(this.houseListDiv.houseList[j].distance<this.houseListDiv.houseList[i].distance){
                  tempmin=j;
                }
            }
            let tempHouse=this.houseListDiv.houseList[i]
            this.houseListDiv.houseList[i]=this.houseListDiv.houseList[tempmin]
            this.houseListDiv.houseList[tempmin]=tempHouse
          }
          break
          break
        case 1:
          for(let i=0;i<this.houseListDiv.houseList.length;i++){
            let tempmin=i
            for(let j=i;j<this.houseListDiv.houseList.length;j++){
                if(this.houseListDiv.houseList[j].price<this.houseListDiv.houseList[i].price){
                  tempmin=j;
                }
            }
            let tempHouse=this.houseListDiv.houseList[i]
            this.houseListDiv.houseList[i]=this.houseListDiv.houseList[tempmin]
            this.houseListDiv.houseList[tempmin]=tempHouse
          }
          break
        case 2:
          let tempMaxList=[]
          for(let i=0;i<this.houseListDiv.houseList.length;i++){
            let tempmax=i
            for(let j=i;j<this.houseListDiv.houseList.length;j++){
                if(this.houseListDiv.houseList[j].price>this.houseListDiv.houseList[i].price){
                  tempmax=j;
                }
            }
            let tempHouse=this.houseListDiv.houseList[i]
            this.houseListDiv.houseList[i]=this.houseListDiv.houseList[tempmax]
            this.houseListDiv.houseList[tempmax]=tempHouse
          }
          break
        default:
          alert('异常数据')
      }
    },
    getHouseDetail(houseId) {
      this.$router.push({ path: "/houseDetail", query: { id: houseId } })
    }
  },
  created() {
      this.selectForm.address = this.$route.query.address
      this.selectForm.date = this.$route.query.date
      
      let allDates0=this.$route.query.allDates

        //这一步是因为在刷新时，vue-router会将只有一个元素的数组转换成字符串
        //而后端只接收数组
      if('string'==typeof(allDates0)){
          this.selectForm.allDates.push(allDates0)
      }else{
          this.selectForm.allDates=allDates0
      }
      this.$axios
          .post("/selectHouseByAddress", this.selectForm)
          .then(response => {
            let data=response.data
            this.houseListDiv.houseList=data
            this.selectHouseDivShow=false
          })
  },
  mounted() {
      
  },
  beforeCreate() {},
  beforeMount() {},
  beforeUpdate() {},
  updated() {},
  beforeDestroy() {},
  destroyed() {},
  activated() {},
  components: {}
}
</script>

<style  scoped>
.timeDivClass{
  padding-top:3%;
  height: 30px;
  border: 2px solid#EEDFCC;
  border-radius: 10px/10px;
}
</style>