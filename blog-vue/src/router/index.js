import { useNProgress } from "@/hooks/useNProgress";
import { useGlobalInfoStore } from "@/stores/globalInfo";
import { useHead } from "@vueuse/head";
import { createRouter, createWebHistory } from "vue-router";

const { start, done } = useNProgress();
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: "User",
      path: "/",
      component: () => import("@/views/User/index.vue"),
      children: [
        {
          path: "",
          name: "DefaultHome",
          redirect: { name: "Home" },
          meta: {
            title: "首页",
            description:
              "主要记录项目实现时的问题收集、以前端为主的知识总结、以及一些零碎知识科普、开发时踩到的坑等",
          },
        },
        {
          name: "Home",
          path: "home",
          component: () => import("@/views/User/Home/index.vue"),
          meta: {
            title: "首页",
            description:
              "主要记录项目实现时的问题收集、以前端为主的知识总结、以及一些零碎知识科普、开发时踩到的坑等",
          },
          children: [
            {
              name: "Page",
              path: "page/:pageId",
              component: () => import("@/views/User/Home/index.vue"),
            },
          ],
        },
        {
          name: "Article",
          path: "article/:articleId",
          component: () => import("@/views/User/Article/index.vue"),
          meta: {
            title: "文章详情",
            description: "",
          },
        },
        {
          name: "Categories",
          path: "categories",
          component: () => import("@/views/User/Categories/index.vue"),
          meta: {
            title: "分类",
            description: "技术文章分类",
          },
        },
        {
          name: "Time",
          path: "timeLine",
          component: () => import("@/views/User/TimeLine/index.vue"),
          meta: {
            title: "时间线",
            description: "按照时间线浏览文章",
          },
          children: [
            {
              path: "",
              name: "DefaultTime",
              redirect: { name: "ZScroll" },
            },
            // {
            //   name: 'XScroll',
            //   path: 'xScroll',
            //   component: () => import('@/views/User/TimeLine/XScroll/index.vue'),
            // },
            {
              name: "ZScroll",
              path: "zScroll",
              component: () => import("@/views/User/TimeLine/ZScroll/index.vue"),
            },
          ],
        },
        {
          name: "Friends",
          path: "friends",
          component: () => import("@/views/User/Friends/index.vue"),
          meta: {
            title: "友人帐",
            description: "友人帐",
          },
        },
        {
          name: "Other",
          path: "other",
          component: () => import("@/views/User/Home/index.vue"),
        },
        {
          name: "NotFound",
          path: "404",
          component: () => import("@/components/NotFound.vue"),
          meta: {
            title: "404",
            description: "您访问的页面不存在",
            noindex: true,
            robots: "index, nofollow",
          },
        },
        { path: "/:pathMatch(.*)*", redirect: { name: "NotFound" } },
      ],
    },
    {
      name: "Admin",
      path: "/admin",
      component: () => import("@/views/Admin/index.vue"),
      children: [
        {
          path: "",
          redirect: { name: "DashBoard" },
        },

        {
          name: "DashBoard",
          path: "dashBoard",
          component: () => import("@/views/Admin/DashBoard/index.vue"),
        },
        {
          name: "EditArticle",
          path: "editArticle",
          component: () => import("@/views/Admin/EditArticle/index.vue"),
          children: [
            {
              path: "",
              redirect: { name: "ArticleList" },
            },
            {
              name: "ArticleList",
              path: "articleList",
              component: () => import("@/views/Admin/EditArticle/ArticleList/index.vue"),
            },
            {
              name: "ToEditArticle",
              path: "toEditArticle/:editArticleId?",
              component: () => import("@/views/Admin/EditArticle/ToEditArticle/index.vue"),
            },
            {
              name: "LabelList",
              path: "labelList",
              component: () => import("@/views/Admin/EditArticle/LabelList/index.vue"),
            },
          ],
        },
        {
          name: "Comment",
          path: "comment",
          component: () => import("@/views/Admin/Comment/index.vue"),
        },
        {
          name: "File",
          path: "file",
          component: () => import("@/views/Admin/File/index.vue"),
        },
        {
          name: "Links",
          path: "links",
          component: () => import("@/views/Admin/Links/index.vue"),
        },
        {
          name: "ManageUser",
          path: "manageUser",
          component: () => import("@/views/Admin/ManageUser/index.vue"),
        },
        {
          name: "GlobalSetting",
          path: "setting",
          component: () => import("@/views/Admin/Setting/index.vue"),
        },
      ],
    },
  ],
});

router.beforeEach((to) => {
  start();
  const globalInfoStore = useGlobalInfoStore();
  useHead({
    title: to.meta.title
      ? (globalInfoStore.globalInfo.siteName || "frium") + "'s blog - " + to.meta.title
      : "默认标题",
    meta: [
      {
        name: "description",
        content: to.meta.description || "",
      },
      {
        name: "robots",
        content: to.meta.robots || "index, follow",
      },
    ],
  });
});

router.afterEach((_) => {
  done();
});

export default router;
