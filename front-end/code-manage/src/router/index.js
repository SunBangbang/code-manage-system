import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store'
import { getChildren, setIn } from '@/assets/js/getMenu'

let childs = []
let d = sessionStorage.getItem('arr')
if(d) {
  childs = getChildren(JSON.parse(d))
}else {
  childs = []
}
// {
//   path: '/struct',
//       redirect: '/home',
//     component: () => import('@/components/Structure'),
//     children: [
//   {
//     path: '/home',
//     name: '首页',
//     component: () => import('@/views/Home')
//   }
// ]
// },
const routes = [
  {
    path: '/',
    name: '登录',
    component: () => import('@/views/Login')
  },{
    path: '/struct',
    name: 'main',
    component: () => import('@/components/Structure'),
    children: childs
  },{
    path: '/struct',
    redirect: '/home',
    component: () => import('@/components/Structure'),
    children: [
      {
        path: '/home',
        name: '首页',
        component: () => import('@/views/Home')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem('token')
  const arr = sessionStorage.getItem('arr')
  store.commit('changeMenu', JSON.parse(arr))
  store.commit('changeIndex', sessionStorage.getItem('defaultIndex'))
  store.commit('changeBreadList', JSON.parse(sessionStorage.getItem('breadList')))
  if(!token) {
    if(to.path === '/') next()
    else next('/')
  }else {
    // routes[1].children = [...JSON.parse(arr)]
    routes[1].children = getChildren(JSON.parse(arr))
    setIn(JSON.parse(arr), router)
    next()
  }
})

export default router
