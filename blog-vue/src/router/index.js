import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: 'User',
      path: '/',
      component: import('@/views/User/index.vue'),
      children: [
        {
          name: 'Home',
          path: '',
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
          name: 'Article',
          path: 'article/:articleId',
          component: () => import('@/views/Article/index.vue')
        },
        {
          name: 'Categories',
          path: 'categories',
          component: () => import('@/views/Categories/index.vue')
        },
        {
          name: 'Time',
          path: 'time',
          component: () => import('@/views/TimeLine/index.vue')
        },
        {
          name: 'Friends',
          path: 'friends',
          component: () => import('@/views/Friends/index.vue')
        },
        {
          name: 'Other',
          path: 'other',
          component: () => import('@/views/Home/index.vue')
        },
        {
          path: 'music',
          component: () => import('@/components/MusicPlayerStrip.vue')
        }
      ]
    },
    {
      path: '/admin',
      component: () => import('@/views/Admin/index.vue')

    }
  ],
})

export default router
