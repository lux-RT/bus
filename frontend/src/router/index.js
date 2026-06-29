import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/fleet',
    children: [
      {
        path: 'fleet',
        name: 'Fleet',
        component: () => import('@/views/fleet/index.vue'),
        meta: { title: '车队', icon: 'OfficeBuilding' }
      },
      {
        path: 'line',
        name: 'Line',
        component: () => import('@/views/line/index.vue'),
        meta: { title: '线路', icon: 'MapLocation' }
      },
      {
        path: 'vehicle',
        name: 'Vehicle',
        component: () => import('@/views/vehicle/index.vue'),
        meta: { title: '车辆', icon: 'Van' }
      },
      {
        path: 'driver',
        name: 'Driver',
        component: () => import('@/views/driver/index.vue'),
        meta: { title: '司机', icon: 'UserFilled' }
      },
      {
        path: 'violation',
        name: 'Violation',
        redirect: '/violation/list',
        meta: { title: '违章', icon: 'WarningFilled' },
        children: [
          {
            path: 'list',
            name: 'ViolationList',
            component: () => import('@/views/violation/index.vue'),
            meta: { title: '违章记录', icon: 'Document' }
          },
          {
            path: 'query',
            name: 'ViolationQuery',
            component: () => import('@/views/violation/query.vue'),
            meta: { title: '违章查询', icon: 'Search' }
          }
        ]
      },
      {
        path: 'station',
        name: 'Station',
        component: () => import('@/views/station/index.vue'),
        meta: { title: '站点', icon: 'LocationFilled' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

// 路由守卫：未登录跳转到登录页
router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem('isLoggedIn')
  if (to.path !== '/login' && !isLoggedIn) {
    next('/login')
  } else if (to.path === '/login' && isLoggedIn) {
    next('/')
  } else {
    next()
  }
})

export { routes }
export default router
