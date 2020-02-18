<template>
  <div class="firstLoginBg">
      <van-row type="flex" justify="center" style="padding-top:15%">
        <span style="font-size:20px">Shared Day-rent System</span>
      </van-row>
      <van-row type="flex" justify="center" style="padding-top:5%">
        <span>请选择您的身份：</span>
      </van-row>

    <van-row type="flex" justify="center" style="padding-top:20%">
      <van-col span="18">
        <van-button
          icon="star-o"
          type="primary"
          size="large"
          round
          id="imzk"
          @click="clickConsumer"
        >我是租客</van-button>
      </van-col>
    </van-row>
    <van-row type="flex" justify="center">
      <van-col span="18">
        <van-button
          icon="manager-o"
          type="primary"
          size="large"
          round
          id="imfd"
          @click="clickMaster"
          style="margin-top:20px"
        >我是房东</van-button>
      </van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userStatus:''
    }
  },
  methods: {
    clickMaster() {
      this.userStatus='master'
      console.log(this.userStatus)
      this.$axios.post('/user/updateStatus',this.userStatus)
                    .then(response=>{
                      let data=response.data
                      if(data.msg=='success'){
                        this.$router.push("/masterHome")
                      }else{
                        alert('未知异常')
                        this.$router.push('/login')
                      }
                    }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
      
    },
    clickConsumer() {
      this.userStatus='consumer'
      this.$axios.post('/user/updateStatus',this.userStatus)
                    .then(response=>{
                      let data=response.data
                      if(data.msg=='success'){
                        this.$router.push("/consumerHome")
                      }else{
                        alert('未知异常')
                        this.$router.push('/login')
                      }
                    }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    }
  }
}
</script>

<style scoped>
.firstLoginBg {

  background-color: yellowgreen;
}
</style>
