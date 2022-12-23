import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store'

const routes = [
  {
    path: '/',
    redirect: '/home',
    component: () => import('@/components/Structure'),
    children: [
      {
        path: '/home',
        name: '首页',
        component: () => import('@/views/Home')
      }
    ]
  },{
    path: '/struct',
    component: () => import('@/components/Structure'),
    children: [
      {
        path: '/browsing',
        name: '编码管理',
        icon: 'coin',
        component: () => import('@/views/Browsing/BrowsingPage'),
        children: [
          {
            path: '/coding-area',
            name: '编码区管理',
            component: () => import('@/views/Browsing/CodingArea')
          },{
            path: '/coding',
            name: '编码管理',
            component: () => import('@/views/Browsing/Coding')
          }
          // ,{
          //   path: '/rest',
          //   name: '预留',
          //   component: () => import('@/views/Browsing/Rest')
          // }
        ]
      },{
        path: '/system',
        name: '系统管理',
        icon: 'setting',
        component: () => import('@/views/System/SystemPage'),
        children: [
          {
            path: '/department',
            name: '部门管理',
            component: () => import('@/views/System/Department')
          },{
            path: '/user',
            name: '用户管理',
            component: () => import('@/views/System/User')
          },{
            path: '/role',
            name: '角色组管理',
            component: () => import('@/views/System/Role')
          },{
            path: '/menu',
            name: '菜单管理',
            component: () => import('@/views/System/Menu')
          },{
            path: '/program',
            name: '应用程序管理',
            component: () => import('@/views/System/Program')
          },{
            path: '/dbms',
            name: 'DBMS管理',
            component: () => import('@/views/System/DBMS')
          },
        ]
      },{
        path: '/history',
        name: '连接历史记录',
        icon: 'link',
        component: () => import('@/views/Monitoring/MonitoringPage'),
        children: [
          {
            path: '/login-history',
            name: '登录历史',
            icon: 'edit',
            component: () => import('@/views/Monitoring/LoginHistory')
          },{
            path: '/access-history',
            name: '用户连接历史',
            icon: 'edit',
            component: () => import('@/views/Monitoring/UserHistory')
          },{
            path: '/menu-history',
            name: '用户菜单访问历史',
            icon: 'edit',
            component: () => import('@/views/Monitoring/MenuHistory')
          }
        ]
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  store.commit('changeIndex', sessionStorage.getItem('defaultIndex'))
  store.commit('changeBreadList', JSON.parse(sessionStorage.getItem('breadList')))
  next()
})

export default router
