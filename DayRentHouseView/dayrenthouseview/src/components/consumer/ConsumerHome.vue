<template>
  <div>
    <component :is="currentItem"></component>
    <van-tabbar v-model="itemIndex" @change="onChange">
      <van-tabbar-item icon="home-o">租房</van-tabbar-item>
      <van-tabbar-item icon="search">订单</van-tabbar-item>
      <van-tabbar-item icon="friends-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import ConsumerRent from '@/components/consumer/ConsumerRent.vue'
import ConsumerOrderList from '@/components/consumer/ConsumerOrderList.vue'
import MasterMyself from '@/components/master/MasterMyself.vue'
export default {
  data() {
    return {
      itemIndex:0,
      currentItem:'consumerRent'
    }
  },
  components:{
      consumerRent:ConsumerRent,
      ConsumerOrderList:ConsumerOrderList,
      masterMyself:MasterMyself
  },
  

  created() {
    
  },

  methods: {
    onChange(index) {
      switch(index){
        case 0:
          this.currentItem='consumerRent'
          break
        case 1:
          this.currentItem='consumerOrderList'
          break
        case 2:
          this.currentItem='masterMyself'
          break
      }
    }
  }
}

</script>




<style scoped>
</style>
