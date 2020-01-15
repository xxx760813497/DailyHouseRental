<template>
  <div class>
    <component :is="currentItem"></component>

    <van-tabbar v-model="itemIndex" >
      <van-tabbar-item icon="home-o">房屋管理</van-tabbar-item>
      
      <van-tabbar-item icon="friends-o">信息修改</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import HomeManage from "@/components/administrator/HomeManage.vue"
export default {
  data() {
    return {
      currentItem:'homeManage',
      itemIndex:0
    }
  },
  components: {
    homeManage: HomeManage
  },
  methods: {},
  created() {}
}
</script>

<style lang='scss' scoped>
</style>