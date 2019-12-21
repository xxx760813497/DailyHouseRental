<template>
  <div class="LoginDiv">
    <div class="topDiv">
      <van-row type="flex" justify="center">
        <van-col span="18" offset="3">
          <span style="font-size:18px">欢迎注册Shared Day-rent System</span>
        </van-col>
      </van-row>
      <van-row type="flex" justify="center">
        <van-col span="10" offset="2">
          <span style="font-size: 15px;">开始你的共享生活</span>
        </van-col>
      </van-row>
    </div>

    <van-row type="flex" justify="center">
      <van-col span="20">
        <div class="formDiv">
          <van-cell-group>
            <van-field
              left-icon="user-o"
              label-width="55"
              label="用户名"
              placeholder="请输入用户名"
              v-model="registerForm.phonenum"
            />
            <van-field
              left-icon="star-o"
              label-width="55"
              :type="pwdInputType"
              label="密码"
              placeholder="请输入密码"
              :right-icon="pwdInputImg"
              @click-right-icon="showPassword"
              v-model="registerForm.password"
            />
            <!-- <van-field
              left-icon="like-o"
              label-width="55"
              label="昵称"
              placeholder="请输入昵称"
              v-model="registerForm.nickname"
            /> -->
          </van-cell-group>
        </div>
      </van-col>
    </van-row>
    <van-row type="flex" justify="center" style="padding-top:5%">
      <van-col span="8" style="padding-left:10%">
        <van-button type="primary" @click="clickRegister">注册</van-button>
      </van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pwdInputType: "password",
      pwdInputImg: "closed-eye",
      registerForm: {
        phonenum: "",
        password: "",
      }
    }
  },
  methods: {
    clickRegister() {
      
      this.$axios.post('/register',this.registerForm)
                    .then(response=>{
                        let data =response.data
                        switch(data.msg){
                          case 'deficient':
                            alert('请将信息填写完整')
                            break
                          case 'phonenumExist':
                            alert('账号已存在，请更换')
                            break
                          case 'success':
                            alert('注册成功')
                            this.$router.push("/login")
                            break
                          case 'failed':
                            alert('系统异常，注册失败')
                            break
                        }
                    })
                    
    },
    showPassword() {
      this.pwdInputType = this.pwdInputType === "password" ? "text" : "password"
      this.pwdInputImg =
        this.pwdInputImg === "closed-eye" ? "eye-o" : "closed-eye"
    }
  }
}
</script>

<style scoped>
.LoginDiv {
  background-color: bisque;
}
.formDiv {
  padding-top: 25%;
}
.topDiv {
  padding-top: 20%;
}
</style>
