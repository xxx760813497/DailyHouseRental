<template>
  <div class>
    <component :is="currentItem"></component>

    <van-tabbar v-model="itemIndex" @change="onChange" >
      <van-tabbar-item icon="home-o">房屋管理</van-tabbar-item>
      <van-tabbar-item icon="friends-o">评论管理</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import HomeManage from "@/components/administrator/HomeManage.vue"
import CommentaryManage from "@/components/administrator/CommentaryManage.vue"
export default {
  data() {
    return {
      currentItem:'homeManage',
      itemIndex:0
    }
  },
  components: {
    homeManage: HomeManage,
    commentaryManage:CommentaryManage
  },
  methods: {
    onChange(index) {
      switch(index){
        case 0:
          this.currentItem='homeManage'
          break
        case 1:
          this.currentItem='commentaryManage'
          break
      }
    },
  },
  created() {}
}
</script>

<style lang='scss' scoped>
</style>