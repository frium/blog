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
          path: '',
          name: 'DefaultHome',
          redirect: { name: 'Home' }
        },
        {
          name: 'Home',
          path: 'home',
          component: () => import('@/views/User/Home/index.vue'),
          children: [
            {
              name: 'Page',
              path: 'page/:pageId',
              component: () => import('@/views/User/Home/index.vue'),
            }
          ]
        },
        {
          name: 'Article',
          path: 'article/:articleId',
          component: () => import('@/views/User/Article/index.vue')
        },
        {
          name: 'Categories',
          path: 'categories',
          component: () => import('@/views/User/Categories/index.vue')
        },
        {
          name: 'Time',
          path: 'time',
          component: () => import('@/views/User/TimeLine/index.vue')
        },
        {
          name: 'Friends',
          path: 'friends',
          component: () => import('@/views/User/Friends/index.vue')
        },
        {
          name: 'Other',
          path: 'other',
          component: () => import('@/views/User/Other/index.vue')
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
});

export default router
