import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '@/components/Login.vue'
import FristLogin from '@/components/FirstLogin.vue'
import Register from '@/components/Register.vue'

import MasterHome from '@/components/master/MasterHome.vue'
import MasterStore from '@/components/master/MasterStore.vue'
import AddHouse from '@/components/master/AddHouse.vue'

import AdminHome from '@/components/administrator/AdminHome.vue'
import AdminHouseDetail from '@/components/administrator/AdminHouseDetail.vue'

import ConsumerHome from '@/components/consumer/ConsumerHome.vue'
import ConsumerHouseList from '@/components/consumer/ConsumerHouseList.vue'

import HouseDetail from '@/components/HouseDetail.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login',component:Login},
  { path: '/firstLogin', component: FristLogin },
  { path: '/register', component: Register },
  

  { path: '/masterHome', component: MasterHome},
  { path: '/masterStore', component: MasterStore },
  { path: '/addHouse', component: AddHouse },

  { path: '/adminHome', component: AdminHome },
  { path: '/adminHouseDetail', component: AdminHouseDetail },

  { path: '/consumerHome', component: ConsumerHome },
  { path: '/consumerHouseList', component: ConsumerHouseList },
  
  { path: '/houseDetail', component: HouseDetail }


  
]

const router = new VueRouter({
  routes
})

export default router
