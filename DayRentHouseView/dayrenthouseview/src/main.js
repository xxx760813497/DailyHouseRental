import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Vant from 'vant'
import axios from 'axios'
import qs from 'qs'
import 'vant/lib/index.css'
import '@/assets/css/global.css'
import BaiduMap from 'vue-baidu-map'
Vue.use(Vant)
Vue.use(BaiduMap, {
  ak: 'MwYHY5IRaImP4rvu6U19MNrmk77rKT9O'
})

Vue.prototype.$axios = axios
Vue.prototype.$qs = qs;

// axios 配置
axios.defaults.withCredentials = true
axios.defaults.timeout = 5000;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
axios.defaults.baseURL = 'http://localhost:8080';
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
