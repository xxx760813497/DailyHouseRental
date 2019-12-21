<template>
  <div style="padding-top:5%">
    <div class="searchDiv">
      <van-row type="flex" justify="center" style="padding-top:3%">
        <van-col span="15" style="border-bottom: 1px solid #ffb90f;">
          <van-field value="泉州" label="城市" left-icon="bar-chart-o" />
        </van-col>
      </van-row>

      <van-row type="flex" justify="center">
        <van-col span="15" style="border-bottom: 1px solid #ffb90f;">
          <van-field value="12.8-12.9" label="入住时间" left-icon="notes-o" />
        </van-col>
      </van-row>

      <van-row type="flex" justify="center">
        <van-col span="15" >
          <van-field value="洛江区" label="地点" left-icon="location-o" />
        </van-col>
      </van-row>

      <van-row type="flex" justify="center" style="padding-bottom:5%">
        <van-col span="10" offset="3">
          <van-button round type="warning">搜索房源</van-button>
        </van-col>
      </van-row>
    </div>
  </div>
</template>

<script>
</script>

<style scoped>
.searchDiv {
  width: 90%;
  margin-left: 5%;
  border: 1px solid #ffb90f;
  border-radius: 50px/100px;
}
</style>