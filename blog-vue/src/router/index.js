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
          path: 'timeLine',
          component: () => import('@/views/User/TimeLine/index.vue'),
          children: [
            {
              path: '',
              name: 'DefaultTime',
              redirect: { name: 'ZScroll' }
            },
            {
              name: 'XScroll',
              path: 'xScroll',
              component: () => import('@/views/User/TimeLine/XScroll/index.vue'),
            },
            {
              name: 'ZScroll',
              path: 'zScroll',
              component: () => import('@/views/User/TimeLine/ZScroll/index.vue'),
            }
          ]
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
        },
        {
          name: 'NotFound',
          path: '404',
          component: () => import('@/components/NotFound.vue')
        }
      ]
    },
    {
      name: 'Admin',
      path: '/admin',
      component: () => import('@/views/Admin/index.vue'),
      children: [
        {
          path: '',
          redirect: { name: 'DashBoard' }
        },

        {
          name: 'DashBoard',
          path: 'dashBoard',
          component: () => import('@/views/Admin/DashBoard/index.vue')
        },
        {
          name: "EditArticle",
          path: 'editArticle',
          component: () => import('@/views/Admin/EditArticle/index.vue'),
          children: [

            {
              path: '',
              redirect: { name: 'ArticleList' }
            },
            {
              name: "ArticleList",
              path: 'articleList',
              component: () => import('@/views/Admin/EditArticle/ArticleList/index.vue'),
            },
            {
              name: "ToEditArticle",
              path: 'toEditArticle/:editArticleId?',
              component: () => import('@/views/Admin/EditArticle/ToEditArticle/index.vue'),
            },
            {
              name: "LabelList",
              path: 'labelList',
              component: () => import('@/views/Admin/EditArticle/LabelList/index.vue'),
            }

          ]
        },
        {
          name: 'Comment',
          path: 'comment',
          component: () => import('@/views/Admin/Comment/index.vue')
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
          path: 'manageUser',
          component: () => import('@/views/Admin/ManageUser/index.vue')
        },
        {
          name: 'PersonalCenter',
          path: 'personalCenter',
          component: () => import('@/views/Admin/PersonalCenter/index.vue')

        }
      ]
    },
    {
      name: 'Start',
      path: '/star',
      component: () => import('@/components/StarSky.vue'),
    }
  ],
});

export default router
