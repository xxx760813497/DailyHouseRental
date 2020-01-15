<template>
  <div class="LoginDiv">
    <div class="topDiv">
      <van-row type="flex" justify="center">
        <van-col span="20" offset="3">
          <span style="font-size:22px">Shared Day-rent System</span>
        </van-col>
      </van-row>
      <van-row type="flex" justify="center">
        <span style="font-size: 15px;">享受大不同</span>
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
              v-model="loginForm.phonenum"/>
            <van-field
              left-icon="star-o"
              label-width="55"
              type="password"
              label="密码"
              placeholder="请输入密码"
              v-model="loginForm.password"
            />
          </van-cell-group>
        </div>
      </van-col>
    </van-row>
    <van-row type="flex" justify="end" style="margin-top:5%">
      <van-col span="8" style="padding-left:10%">
        <van-button type="primary" @click="clickLogin">登录</van-button>
      </van-col>
      <van-col span="11">
        <van-button type="primary" @click="clickRegister">新用户注册</van-button>
      </van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm:{
        phonenum:'',
        password:''
      }
    }
  },
  methods: {
    clickLogin() {
      this.$axios.post('/login',this.loginForm)
                    .then(response=>{
                        let data=response.data
                        if(data.msg=='success'){
                          alert("登录成功")
                          window.sessionStorage.setItem('user',data.user)

                          switch(data.status){
                            case null:
                              this.$router.push("/firstLogin")
                              break
                            case 'consumer':
                              this.$router.push('/consumerHome')
                              break
                            case 'master':
                              this.$router.push('/masterHome')
                              break
                            case 'administrator':
                              this.$router.push('/adminHome')
                          }
                        }else if(data.msg='error'){
                          alert('用户名或密码错误')
                        }else if(data.msg='deficient'){
                          alert('请将信息填写完整')
                        }
                    })
    },
    clickRegister() {
      this.$router.push("/Register")
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
