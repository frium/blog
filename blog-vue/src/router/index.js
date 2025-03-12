import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: 'User',
      path: '/',
      component: () => import('@/views/User/index.vue'),
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
          component: () => import('@/views/User/Home/index.vue')
        }
      ]
    },
    {
      name: 'Admin',
      path: '/admin',
      component: () => import('@/views/Admin/index.vue'),
      children: [
        {
          name: "Login",
          path: 'login',
          component: () => import('@/views/Admin/Login/index.vue'),
        },
        {
          name: 'Dashboard',
          path: 'dashboard',
          component: () => import('@/views/Admin/Dashboard/index.vue')
        },
        {
          name: "EditArticle",
          path: 'editArticle',
          component: () => import('@/views/Admin/EditArticle/index.vue'),
        },
        {
          name: 'Comment',
          path: 'comment',
          component: () => import('@/views/Admin/Dashboard/index.vue')
        },
        {
          name: "File",
          path: 'file',
          component: () => import('@/views/Admin/File/index.vue'),
        },
        {
          name: 'Links',
          path: 'links',
          component: () => import('@/views/Admin/Links/index.vue')
        },
        {
          name: 'ManageUser',
          path: 'user',
          component: () => import('@/views/Admin/User/index.vue')
        }
      ]
    }
  ],
});

export default router
