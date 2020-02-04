<template>
  <div style="padding-top:5%">
    <van-row type="flex" justify="center">
      <span>我的店铺</span>
    </van-row>
    <div v-for="(house,index) in housesList" :key="index">
      <van-row style="background-color:#F4F4F4;margin-top:2%">
        <van-col span="8">

          <van-image fit="fill" width="120" height="140" :src="getImg(house.houseTitleImg)" />
        </van-col>
        <van-col span="15" offset="1" style="font-size:14px">
          <span>房名：{{house.name}}</span>
          <br />
          <span>地址：{{house.location}}</span>
          <br />
          <span>价格：{{house.price}}</span>
          <br />
          <span>本房订单成交量：{{house.orderQuantity}}</span>
          <br />
          <span>评分：{{house.grade}}</span>
          <br />
          <div v-if="house.houseState!='审核通过'">
            <span>房屋状态：</span>
            <span style="color:red">{{house.houseState}}</span>
          </div>
          <div  v-if="house.noReadCommentaryNum>0" style="font-size:12px;color:red">
            {{house.noReadCommentaryNum}}条未回复评论
          </div>
        </van-col>
      </van-row>
      <van-row v-if="house.houseState=='审核通过'">
        <van-col span="5" offset="14">
          <van-button size="small" @click="getHouseDetail(house.id)">查看详情</van-button>
        </van-col>
        <van-col span="5">
          <van-button size="small" @click="updateHouseDetail(index)">修改信息</van-button>
        </van-col>
      </van-row>

      <van-row v-if="house.houseState=='审核未通过'">
        <van-col span="5" offset="14">
          <van-button size="small" @click="reapplyHouseDetail(index)">重新申请</van-button>
        </van-col>
        <van-col>
          <van-button size="small" @click="houseWithdraw(house.id)">取消申请</van-button>
        </van-col>
      </van-row>
    </div>

    <!--房屋修改弹窗-->
    <van-dialog
      v-model="houseUpdateShow"
      :title="this.houseDetail.name"
      :show-confirm-button="false"
      width="357px"
    >
      <div>
        <van-row>
          <van-cell-group>
            <van-field v-model="houseDetail.name" label="房屋名：" placeholder="请输入房屋名" required />
            <!-- <div style="font-size:14px;padding-left:2%">
              <span style="color:red;">*</span>
              <span>房屋首图：</span>
              <van-button
                type="warning"
                size="small"
                style="margin-left:5%"
                @click="houseTitleImgUpShow=true"
              >点击替换房屋首图</van-button>
            </div>-->
            <!-- <div style="font-size:14px;padding-left:2%;margin-top:3%">
              <span style="color:red;">*</span>
              <span>房屋内部：</span>
              <van-button type="warning" size="small" style="margin-left:5%" @click="houseImgsUpShow=true" >点击替换房屋内部图片</van-button>
            </div>-->
            <van-field
              v-model="houseDetail.describe"
              label="房屋介绍："
              placeholder="房屋的介绍"
              required
              rows="5"
              type="textarea"
              maxlength="200"
            />
          </van-cell-group>
        </van-row>
        <div style="padding-left:2%">
          <van-row>
            <span>房屋可提供的设备：</span>
          </van-row>
          <van-row style="padding-top:2%">
            <van-col span="8">
              <span>热水：</span>
              <van-switch size="25" v-model="equipments.reshui" />
            </van-col>

            <van-col span="8">
              <span>浴液：</span>
              <van-switch size="25" v-model="equipments.yuye" />
            </van-col>

            <van-col span="8">
              <span>牙具：</span>
              <van-switch size="25" v-model="equipments.yaju" />
            </van-col>

            <van-col span="8">
              <span>毛巾：</span>
              <van-switch size="25" v-model="equipments.maojin" />
            </van-col>

            <van-col span="8">
              <span>浴缸：</span>
              <van-switch size="25" v-model="equipments.yugang" />
            </van-col>

            <van-col span="8">
              <span>纸巾：</span>
              <van-switch size="25" v-model="equipments.zhijin" />
            </van-col>

            <van-col span="8">
              <span>空调：</span>
              <van-switch size="25" v-model="equipments.kongtiao" />
            </van-col>

            <van-col span="8">
              <span>暖气：</span>
              <van-switch size="25" v-model="equipments.nuanqi" />
            </van-col>

            <van-col span="8">
              <span>电视：</span>
              <van-switch size="25" v-model="equipments.dianshi" />
            </van-col>

            <van-col span="8">
              <span>Wifi：</span>
              <van-switch size="25" v-model="equipments.wifi" />
            </van-col>

            <van-col span="8">
              <span>冰箱：</span>
              <van-switch size="25" v-model="equipments.bingxiang" />
            </van-col>

            <van-col span="8">
              <span>洗衣机：</span>
              <van-switch size="25" v-model="equipments.xiyiji" />
            </van-col>

            <van-col span="8">
              <span>停车场：</span>
              <van-switch size="25" v-model="equipments.tingchechang" />
            </van-col>
          </van-row>
        </div>
        <van-divider style="padding-left:2%" />
        <van-row>
          <van-field
            v-model="houseDetail.price"
            label="房屋租价：￥"
            placeholder="请输入租客住一晚所需要的金额"
            required
          />
        </van-row>
        <van-row type="flex" justify="center">
          <van-col>
            <van-button round type="info" @click="houseUpdateClick">提交</van-button>
          </van-col>
        </van-row>
      </div>
    </van-dialog>

    <!--房屋首图修改弹窗-->
    <van-dialog v-model="houseTitleImgUpShow" title="房屋首图" show-cancel-button width="350px">
      <van-row>
        <van-col span="6" offset="1">
          <van-image width="120" height="120" :src="getImg(this.houseDetail.houseTitleImg)" />
        </van-col>
        <van-col span="4" offset="4">
          <van-icon size="50" name="play" style="margin-top:45%" />
        </van-col>
        <van-col span="8" offset="1">
          <van-uploader
            v-model="titleUpdateFile"
            :after-read="titleImgUpdate"
            :before-read="beforeRead"
            :max-count="1"
          />
        </van-col>
      </van-row>
    </van-dialog>

    <!--房屋内部图片修改弹窗-->
    <van-dialog v-model="houseImgsUpShow" title="房屋内部图片" show-cancel-button width="300px">
      <van-row>
        <van-image
          v-for="imgPath in houseDetail.houseImgs"
          :key="imgPath"
          width="90"
          height="85"
          :src="getImg(imgPath)"
          style="margin-left:5%"
        />
      </van-row>

      <van-row type="flex" justify="center" style="margin-top:10%;margin-button:10%">
        <van-icon size="30" name="down" />
      </van-row>

      <van-row>
        <van-uploader
          v-model="houseImgsUpdateFile"
          :before-read="beforeRead"
          :max-count="6"
          style="margin-left:5%"
          :after-read="houseImgsUp"
        />
      </van-row>
    </van-dialog>

    <div>
      <van-row type="flex" justify="center" style="margin-top:2%">
        <van-button plain hairline type="primary" @click="addHouse">发布新房屋</van-button>
      </van-row>
    </div>

    <!--房屋重新申请弹窗-->
    <van-dialog
      v-model="houseReapplyShow"
      title="请您重新确认房屋信息"
      :show-confirm-button="false"
      width="357px"
    >
      <div>
        <van-row>
          <van-cell-group>
            <van-field v-model="houseDetail.name" label="房屋名：" placeholder="请输入房屋名" required />
            <!-- <div style="font-size:14px;padding-left:2%">
              <span style="color:red;">*</span>
              <span>房屋首图：</span>
              <van-button
                type="warning"
                size="small"
                style="margin-left:5%"
                @click="houseTitleImgUpShow=true"
              >点击替换房屋首图</van-button>
            </div>-->
            <!-- <div style="font-size:14px;padding-left:2%;margin-top:3%">
              <span style="color:red;">*</span>
              <span>房屋内部：</span>
              <van-button type="warning" size="small" style="margin-left:5%" @click="houseImgsUpShow=true" >点击替换房屋内部图片</van-button>
            </div>-->
            <van-field
              v-model="houseDetail.describe"
              label="房屋介绍："
              placeholder="房屋的介绍"
              required
              rows="5"
              type="textarea"
              maxlength="200"
            />
          </van-cell-group>
        </van-row>
        <div style="padding-left:2%">
          <van-row>
            <span>房屋可提供的设备：</span>
          </van-row>
          <van-row style="padding-top:2%">
            <van-col span="8">
              <span>热水：</span>
              <van-switch size="25" v-model="equipments.reshui" />
            </van-col>

            <van-col span="8">
              <span>浴液：</span>
              <van-switch size="25" v-model="equipments.yuye" />
            </van-col>

            <van-col span="8">
              <span>牙具：</span>
              <van-switch size="25" v-model="equipments.yaju" />
            </van-col>

            <van-col span="8">
              <span>毛巾：</span>
              <van-switch size="25" v-model="equipments.maojin" />
            </van-col>

            <van-col span="8">
              <span>浴缸：</span>
              <van-switch size="25" v-model="equipments.yugang" />
            </van-col>

            <van-col span="8">
              <span>纸巾：</span>
              <van-switch size="25" v-model="equipments.zhijin" />
            </van-col>

            <van-col span="8">
              <span>空调：</span>
              <van-switch size="25" v-model="equipments.kongtiao" />
            </van-col>

            <van-col span="8">
              <span>暖气：</span>
              <van-switch size="25" v-model="equipments.nuanqi" />
            </van-col>

            <van-col span="8">
              <span>电视：</span>
              <van-switch size="25" v-model="equipments.dianshi" />
            </van-col>

            <van-col span="8">
              <span>Wifi：</span>
              <van-switch size="25" v-model="equipments.wifi" />
            </van-col>

            <van-col span="8">
              <span>冰箱：</span>
              <van-switch size="25" v-model="equipments.bingxiang" />
            </van-col>

            <van-col span="8">
              <span>洗衣机：</span>
              <van-switch size="25" v-model="equipments.xiyiji" />
            </van-col>

            <van-col span="8">
              <span>停车场：</span>
              <van-switch size="25" v-model="equipments.tingchechang" />
            </van-col>
          </van-row>
        </div>
        <van-divider style="padding-left:2%" />
        <van-row>
          <van-field
            v-model="houseDetail.price"
            label="房屋租价：￥"
            placeholder="请输入租客住一晚所需要的金额"
            required
          />
        </van-row>
        <van-row type="flex" justify="center">
          <van-col>
            <van-button round type="info" @click="houseReapplyClick">再次申请</van-button>
          </van-col>
        </van-row>
      </div>
    </van-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      housesList: [],
      houseDetail: {},
      equipments: {
        reshui: false,
        yuye: false,
        yaju: false,
        maojin: false,
        yugang: false,
        zhijin: false,
        kongtiao: false,
        nuanqi: false,
        dianshi: false,
        wifi: false,
        bingxiang: false,
        xiyiji: false,
        tingchechang: false
      },
      houseUpdateShow: false,
      houseTitleImgUpShow: false,
      houseImgsUpShow: false,
      titleUpdateFile: [],
      titleUpdatePath: "",
      houseImgsUpdateFile: [],
      houseUpdateForm: {
        id: "",
        name: "",
        describe: "",
        price: -1,
        // titleImg: "",
        // imgList: [],
        equipmentsList: []
      },
      houseReapplyShow: false,
      allNoCommentaryNumber:0
    }
  },

  created() {
    this.getHouseList()
  },

  methods: {
    addHouse() {
      this.$router.push("/addHouse")
    },
    getImg(imgPath) {
      if (imgPath == null) {
        return
      }
      const img = imgPath.substring(9)
      let path = "@/assets/"
      return require("@/assets/" + img)
    },
    getHouseList() {
      this.$axios.get("/houses").then(response => {
        let data = response.data
        this.housesList = data
        for(let i=0;i<this.housesList.length;i++){
          this.allNoCommentaryNumber+=this.housesList[i].noReadCommentaryNum
        }
        this.sendNoReadNum()
      })
    },
    getHouseDetail(houseId) {
      this.$router.push({ path: "/houseDetail", query: { id: houseId } })
    },
    updateHouseDetail(index) {
      this.houseUpdateShow = true
      this.houseDetail = this.housesList[index]

      //修改后端传来的全文件路径为vue可识别的路径
      //加if判断是为了防止用户再一次点击了相同的房屋修改后，又转换了一次
      if (
        typeof this.houseDetail.houseImgs == "string" &&
        this.houseDetail.houseImgs.constructor == String
      ) {
        let houseImgs = this.houseDetail.houseImgs.split(",")

        for (let imgPath in houseImgs) {
          houseImgs[imgPath] = houseImgs[imgPath].substr(
            houseImgs[imgPath].indexOf("src") + 3
          )
          houseImgs[imgPath] = houseImgs[imgPath].replace(/\\/g, "/")
          houseImgs[imgPath] = "@" + houseImgs[imgPath]
        }
        this.houseDetail.houseImgs = houseImgs
      }

      //修改后端传来的设备字符串转为设备数组，并与data的equipments匹配
      //加if的理由同上
      if (
        typeof this.houseDetail.equipments == "string" &&
        this.houseDetail.equipments.constructor == String
      ) {
        let equipments = this.houseDetail.equipments.split(",")
        for (let equipment in this.equipments) {
          this.equipments[equipment] = false
        }

        for (let equipment in equipments) {
          for (let thisEquipment in this.equipments) {
            if (equipments[equipment] == thisEquipment) {
              this.equipments[thisEquipment] = true
            }
          }
        }
      }
    },
    beforeRead(file) {
      if (file.type !== "image/jpeg" && file.type !== "image/png") {
        alert("请上传 jpg或者png 格式图片")
        return false
      }
      return true
    },
    titleImgUpdate(file) {
      let formData = new FormData()
      formData.append("file", file.file)

      this.$axios
        .post("/houseTitleImg", formData, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(response => {
          let data = response.data
          if (data != "error") {
            this.titleUpdatePath = data.msg
          }
        })
    },
    houseImgsUp(file) {
      let formData = new FormData()
      formData.append("file", file.file)

      this.$axios
        .post("/houseImgs", formData, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(response => {
          let data = response.data
          console.log("ok")
        })
    },
    houseUpdateClick() {
      this.houseUpdateForm.id = this.houseDetail.id
      this.houseUpdateForm.name = this.houseDetail.name
      this.houseUpdateForm.describe = this.houseDetail.describe
      this.houseUpdateForm.price = this.houseDetail.price

      for (let item in this.houseUpdateForm) {
        if (
          (this.houseUpdateForm[item] == null ||
            this.houseUpdateForm[item].length == 0) &&
          item != "equipmentsList"
        ) {
          alert("请将信息填充完整！")
          return
        }
      }

      let equipments = this.equipments
      for (let equipment in equipments) {
        if (
          equipments[equipment] == true &&
          this.houseUpdateForm.equipmentsList.indexOf(equipment) == -1
        ) {
          this.houseUpdateForm.equipmentsList.push(equipment)
        }
      }

      this.$axios.put("/house", this.houseUpdateForm).then(response => {
        let data = response.data
        if ((data.msg = "success")) {
          alert("修改成功")
          this.getHouseList()
        } else {
          alert("系统异常，请重试")
        }
        this.houseUpdateShow = false
      })
    },
    reapplyHouseDetail(index) {
      this.houseReapplyShow = true
      this.houseDetail = this.housesList[index]

      //修改后端传来的全文件路径为vue可识别的路径
      //加if判断是为了防止用户再一次点击了相同的房屋修改后，又转换了一次
      if (
        typeof this.houseDetail.houseImgs == "string" &&
        this.houseDetail.houseImgs.constructor == String
      ) {
        let houseImgs = this.houseDetail.houseImgs.split(",")

        for (let imgPath in houseImgs) {
          houseImgs[imgPath] = houseImgs[imgPath].substr(
            houseImgs[imgPath].indexOf("src") + 3
          )
          houseImgs[imgPath] = houseImgs[imgPath].replace(/\\/g, "/")
          houseImgs[imgPath] = "@" + houseImgs[imgPath]
        }
        this.houseDetail.houseImgs = houseImgs
      }

      //修改后端传来的设备字符串转为设备数组，并与data的equipments匹配
      //加if的理由同上
      if (
        typeof this.houseDetail.equipments == "string" &&
        this.houseDetail.equipments.constructor == String
      ) {
        let equipments = this.houseDetail.equipments.split(",")
        for (let equipment in this.equipments) {
          this.equipments[equipment] = false
        }

        for (let equipment in equipments) {
          for (let thisEquipment in this.equipments) {
            if (equipments[equipment] == thisEquipment) {
              this.equipments[thisEquipment] = true
            }
          }
        }
      }
    },
    houseReapplyClick() {
      this.houseUpdateForm.id = this.houseDetail.id
      this.houseUpdateForm.name = this.houseDetail.name
      this.houseUpdateForm.describe = this.houseDetail.describe
      this.houseUpdateForm.price = this.houseDetail.price

      for (let item in this.houseUpdateForm) {
        if (
          (this.houseUpdateForm[item] == null ||
            this.houseUpdateForm[item].length == 0) &&
          item != "equipmentsList"
        ) {
          alert("请将信息填充完整！")
          return
        }
      }

      let equipments = this.equipments
      for (let equipment in equipments) {
        if (
          equipments[equipment] == true &&
          this.houseUpdateForm.equipmentsList.indexOf(equipment) == -1
        ) {
          this.houseUpdateForm.equipmentsList.push(equipment)
        }
      }

      this.$axios.post("/reapplyHouse", this.houseUpdateForm).then(response => {
        let data = response.data
        if ((data = "success")) {
          alert("申请成功")
          this.getHouseList()
          this.houseReapplyShow = false
        } else {
          alert("系统异常，请重试")
        }
        this.houseUpdateShow = false
      })
    },
    houseWithdraw(houseId) {
      this.$dialog
        .confirm({
          title: "撤销申请",
          message: "本操作将会导致本条房屋信息清空，确定要继续吗？"
        })
        .then(() => {
          // on confirm
          this.$axios.delete("/house/"+houseId)
                      .then(response=>{
                        let data =response.data;
                        if(data=='success'){
                          alert('撤销成功');
                          this.getHouseList()
                        }
                      })
        })
    },
    sendNoReadNum(){
      this.$emit('func',this.allNoCommentaryNumber)
    }
  }
}
</script>

<style scoped>
</style>