<template>
  <div style="padding-top:5%">
    <van-row type="flex" justify="center">
        <span>我的店铺</span>
    </van-row>

    <div  style="background-color:#F4F4F4;margin-top:5%">
      <van-row>
        <van-col span="8">
          <van-image
            height="120"
            src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575797864434&di=a453d87c5afd13bd12384dd0bec2d1c2&imgtype=0&src=http%3A%2F%2Fimg05.jdzj.com%2Foledit%2FUploadFile%2Fnews2014c%2Fimage%2F20140710%2F20140710085728362836.jpg"
          />
        </van-col>
        <van-col span="15" offset="1">
          <span>房名：如家小店</span>
          <br />
          <span>地址：泉州市洛江区马甲镇</span>
          <br />
          <span>价格：￥100</span>
          <br />
          <span>本房订单成交量：10</span>
          <br />
          <span>评分：4.6</span>
          <br />
        </van-col>
        <van-col span="5" offset="11">
          <van-button size="small">查看详情</van-button>
          <!-- <van-button size="small">修改</van-button> -->
        </van-col>
    </van-row>
   </div>

    <div>
        <van-row type="flex" justify="center">
            <van-button plain hairline type="primary" @click="addHouse">添加房屋</van-button>
        </van-row>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },

  created() {
    
      // this.$axios.get('/houses',this.registerForm)
      //               .then(response=>{
      //                   console.log(response.data);
      //               })
  },

  methods: {
    addHouse(){
      this.$router.push('/addHouse')
    }
  }
}
</script>

<style scoped>
</style>