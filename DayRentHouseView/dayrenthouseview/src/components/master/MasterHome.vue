<template>
  <div>
    
    <component @func="getMsgFromChild" :is="currentItem"></component>
    
    <van-tabbar v-model="itemIndex" @change="onChange">
      <van-tabbar-item :info="masterStore.noReadCommentaryNum" icon="home-o">店面</van-tabbar-item>
      <van-tabbar-item icon="search">订单</van-tabbar-item>
      <van-tabbar-item icon="friends-o">我的</van-tabbar-item>
    </van-tabbar>

    
  </div>
</template>

<script>
import MasterStore from '@/components/master/MasterStore.vue'
import MasterOrderList from '@/components/master/MasterOrderList.vue'
export default {
  
  data() {
    return {
      itemIndex:0,
      currentItem:'masterStore',
      masterStore:{
        noReadCommentaryNum:"",
      }
    }
  },
  components:{
      masterStore:MasterStore,
      masterOrderList:MasterOrderList
  },
  

  created() {
    
  },

  methods: {
    onChange(index) {
      switch(index){
        case 0:
          this.currentItem='masterStore'
          break
        case 1:
          this.currentItem='masterOrderList'
          break
        case 2:
          this.currentItem='masterMyself'
          break
      }
    },
    getMsgFromChild(data){
      this.masterStore.noReadCommentaryNum=data
      if(this.masterStore.noReadCommentaryNum==0){
        this.masterStore.noReadCommentaryNum=""
      }
    }
  }
}

</script>

<style scoped>
</style>
