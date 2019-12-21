import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/components/Login.vue'
import FristLogin from '@/components/FirstLogin.vue'
import MasterHome from '@/components/MasterHome.vue'
import ConsumerHome from '@/components/ConsumerHome.vue'
import Register from '@/components/Register.vue'
import MasterStore from '@/components/MasterStore.vue'
import AddHouse from '@/components/AddHouse.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login',component:Login},
  { path: '/firstLogin', component: FristLogin },

  { path: '/masterHome', component: MasterHome},

  { path: '/consumerHome', component: ConsumerHome },
  { path: '/register', component: Register },
  { path: '/masterStore', component: MasterStore },
  { path: '/addHouse', component: AddHouse }

  
]

const router = new VueRouter({
  routes
})

export default router
