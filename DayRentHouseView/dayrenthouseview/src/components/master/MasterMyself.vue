<template>
  <div>
    <van-row type="flex" justify="center" style="padding-top:20%">{{timeTip}}，用户{{userPhone}}</van-row>

    <van-row style="margin-top:30%">
      <van-divider />
      <van-cell size="large" @click="updatePwdShow" icon="setting-o" title="修改密码" is-link />
    </van-row>
    <van-divider />
    <van-row>
      <van-cell size="large" @click="clickUpdateStatusShow" icon="user-o" title="切换身份" is-link />
    </van-row>
    <van-divider />
    <van-row>
      <van-cell size="large" icon="ellipsis" title="关于本软件" is-link />
      <van-divider />
    </van-row>
    <van-popup v-model="updatePasswordShow" position="bottom" :style="{ height: '20%' }">
      <van-field v-model="updatePwdInfo.newPassword" type="password" label="新密码" />
      <van-field v-model="updatePwdInfo.secondPassword" type="password" label="确认密码" />
      <van-col offset="18">
          <van-button @click="clickUpdatePwd" type="primary">确认修改</van-button>
      </van-col>
    </van-popup>

    <van-popup v-model="userStateShow" position="bottom" :style="{ height: '20%' }">
      <van-cell @click="clickConsumer" size="large" icon="user-o" title="租客" />

      <van-cell @click="clickMaster" size="large" icon="hotel-o" title="屋主"  />

    </van-popup>
  </div>
</template>

<script>
export default {
  data() {
    return {
      timeTip: "",
      userPhone:"",
      updatePasswordShow: false,
      updatePwdInfo: {
        newPassword: "",
        secondPassword: ""
      },
      userStateShow: false,
      userStatus: ""
    }
  },
  methods: {
    getTimeTip() {
      let timeNow = new Date()
      let hours = timeNow.getHours()
      let text = ``
      if (hours >= 0 && hours < 5) {
        text = `夜深了，请注意休息哦`
      } else if (hours >= 5 && hours <= 10) {
        text = `早上好`
      } else if (hours > 10 && hours <= 14) {
        text = `中午好`
      } else if (hours > 14 && hours <= 18) {
        text = `下午好`
      } else if (hours > 18 && hours <= 24) {
        text = `晚上好`
      }

      return text
    },
    updatePwdShow() {
      this.updatePwdInfo.newPassword = ""
      this.updatePwdInfo.secondPassword = ""
      this.updatePasswordShow = true
    },
    clickUpdatePwd() {
      if (
        this.updatePwdInfo.newPassword.length == 0 ||
        this.updatePwdInfo.secondPassword.length == 0
      ) {
        alert("补充完整信息")
        return
      }
      if (this.updatePwdInfo.newPassword != this.updatePwdInfo.secondPassword) {
        alert("两次输入密码不一致")
        return
      }
      this.$axios.post("/user/updatePassword", this.updatePwdInfo).then(response => {
        let data = response.data
        if (data == "success") {
          alert("修改成功")
        } else {
          alert("系统异常")
        }
        this.updatePasswordShow = false
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
    clickUpdateStatusShow() {
      this.userStatus = ""
      this.userStateShow = true
    },
    clickMaster() {
      this.userStatus = "master"
      this.$axios.post("/user/updateStatus", this.userStatus).then(response => {
        let data = response.data
        if (data.msg == "success") {
          alert('切换成功，请重新登录')
        } else {
          alert("未知异常")
        }
        this.userStateShow=false
        this.$router.push("/login")
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    },
    clickConsumer() {
      this.userStatus = "consumer"
      this.$axios.post("/user/updateStatus", this.userStatus).then(response => {
        let data = response.data
        if (data.msg == "success") {
          alert('切换成功，请重新登录')
        } else {
          alert("未知异常")
        }
        this.userStateShow=false
        this.$router.push("/login")
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
    }
  },
  created() {
    this.timeTip = this.getTimeTip()
    this.$axios.get('/all/userPhone')
            .then(response=>{
                this.userPhone=response.data
            }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      }).catch(err=>{
        console.log(err)
        this.$router.push('/login')
      })
  }
}
</script>

<style  scoped>
</style>