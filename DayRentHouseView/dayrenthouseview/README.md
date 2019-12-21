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
