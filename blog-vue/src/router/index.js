import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: 'Home',
      path: '/',
      component: () => import('@/views/Home/index.vue'),
      children: [
        {
          name: 'Page',
          path: 'page/:pageId',
          component: () => import('@/views/Home/index.vue'),
        }
      ]
    },
    {
      name: 'Categories',
      path: '/categories',
      component: () => import('@/views/Home/index.vue')
    }, {
      name: 'Time',
      path: '/time',
      component: () => import('@/views/Home/index.vue')
    }, {
      name: 'Friends',
      path: '/friends',
      component: () => import('@/views/Home/index.vue')
    }, {
      name: 'Other',
      path: '/other',
      component: () => import('@/views/Home/index.vue')
    }


  ],
})

export default router
