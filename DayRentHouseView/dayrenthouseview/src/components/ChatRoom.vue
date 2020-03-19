<template>
  <div>
    <van-row type="flex" justify="center" style="padding-top:5%">聊天室</van-row>
    
    <van-row >
        <ul>
            <li style="padding-top:3%" v-for="(message,index) in messages" :key=index>
                <span >用户{{message.userId}}:</span>
                <span style="padding-left:5%">{{message.message}}</span><br/>
                <span style="font-size:12px">said at {{message.date}}</span>
            </li>
        </ul>
    </van-row>

    <van-row style="position:fixed; bottom:9%">
        <van-row>
            <van-field
          v-model="message"
          label="留言"
          type="textarea"
          placeholder="请输入留言"
          autosize
        />
        </van-row>
        <van-row>
            <van-button type="default" @click="websocketsend(message)">发送消息</van-button>
        </van-row>
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userId:-1,
      websock: null,
      message:"",
      messages:[]
    }
  },

  methods:{
      initWebSocket(){ //初始化weosocket
        
      },
      websocketonopen(){ //连接建立之后执行send方法发送数据

      },
      websocketonerror(){//连接建立失败重连
        console.log('网络异常，请重新登录连接')
      },
      websocketonmessage(e){ //数据接收
       let data=JSON.parse(e.data)
        this.messages.push(data)
      },
      websocketsend(Data){//数据发送
        this.websock.send(Data);
        this.message=""
      },
      websocketclose(e){  //关闭
        console.log('断开连接',e);
      },

      getCurrentUserId(){
        this.$axios.get('/user/getCurrentUserId')
                    .then(response=>{
                      let data=response.data
                      this.userId=data



                      const wsuri = "ws://127.0.0.1:8080/websocket/"+this.userId;
                      this.websock = new WebSocket(wsuri);
                      this.websock.onmessage = this.websocketonmessage;
                      this.websock.onopen = this.websocketonopen;
                      this.websock.onerror = this.websocketonerror;
                      this.websock.onclose = this.websocketclose;
                    })
                    .catch(err=>{
                      alert(err)
                      this.$router.push('/login')
                    })
      }
  },

  created() {
    this.getCurrentUserId();
    this.initWebSocket();
  },
  destroyed() {
      this.websock.onclose()
  }
}
</script>

<style  scoped>
</style>