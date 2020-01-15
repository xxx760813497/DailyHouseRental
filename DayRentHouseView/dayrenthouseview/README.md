# dayrenthouseview

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

###2019.12.7
```
解决前后端跨域问题，以及session丢失，每次访问都是一个新的session
vue+springboot
在springboot中配置
@Configuration
public class MySpringMvcConfig  implements WebMvcConfigurer {
    
    //跨域问题，修改请求头
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {

            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .maxAge(3600)
                        .allowCredentials(true);
            }
        };
    }

}

在axios中设置
axios.defaults.withCredentials = true
```

###整合了百度地图
```
在发布房屋上主要使用到了定位、检索提示、地址解析功能
  0.安装百度地图组件
    npm install vue-baidu-map --save
  1.定位功能
  MyLocation() {
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        if (geolocation.getStatus() == BMAP_STATUS_SUCCESS) {
          let myGeo = new BMap.Geocoder()
          myGeo.getLocation(r.point, result => {
            if (result) {
              this.addHouseForm.location = result.address
            }
          })
        } else {
          alert("自动定位失败，请手动添加")
        }
      })
    },
  2.检索提示
  MapSugges() {
      // var map = new BMap.Map("l-map");
      var ac = new BMap.Autocomplete({ input: "suggestId" })
      var myValue
      ac.addEventListener("onconfirm", e => {
        var _value = e.item.value
        myValue =
          _value.province +
          _value.city +
          _value.district +
          _value.street +
          _value.business
        this.addHouseForm.location = myValue

        var myGeo = new BMap.Geocoder()
        myGeo.getPoint(
        this.addHouseForm.location,
        point => {
          if (point) {
            this.center.lat = point.lat
          this.center.lng = point.lng
          this.zoom = 16
          } else {
            alert("地址选择错误!")
          }
        },
        this.addHouseForm.location.slice(0, 3)
      )
      })
    },
    3.地址解析（将地名转换为经纬度，用于存入数据库中）
    getHousePoint() {
      var myGeo = new BMap.Geocoder('l-map')
      myGeo.getPoint(
        this.addHouseForm.location,
        point => {
          if (point) {
            this.addHouseForm.address = point.lng + "," + point.lat
          } else {
            alert("地址选择错误!")
          }
        },
        this.addHouseForm.location.slice(0, 3)
      )
    },

###采坑 关于v-for中设置图片的src
```
最开始的时候是想着直接赋值把for中的对象的图片绝对路径赋给src，显然，src是不能这样做的，src会直接将js表达式解析成字符串，
然后再通过v-bind去绑定src，结果仍然出错，后端返回的是一个不在本项目文件中的绝对路径，经过修改后，将后端返回的图片路径改到了本项目中的assets文件夹中，在这个文件夹下的文件会经过webpack解析，所以还需要用require函数引入，结果，依然不行。
最后，我再查了一些资料，发现require的参数不能是变量，只能是常量，而我传入的是一house.houseTitleImg，也就是一个JSON变量，所以我将src绑定到了一个自己写的方法中，通过这个方法将图片的路径转换成一个常量+变量，也就将参数变为了常量，最后返回执行require方法，解决了这个问题。
```
  <van-image
  height="120"
  :src="getImg(house.houseTitleImg)"
  />
getImg(imgPath){
      const img = imgPath.substring(9);
      let path='@/assets/'
      return require('@/assets/'+img); 
    }

###采坑，发现在component标签中的组件中使用vant提供的uploader上传文件中发送请求会刷新网页（没有找到解决方法，只好将功能移动到了新的组件中）
