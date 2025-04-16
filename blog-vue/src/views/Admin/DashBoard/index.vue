<script setup>
import QuickFind from './components/QuickFind.vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import InfoCard from './components/InfoCard.vue';
import RecentArticle from './components/RecentArticle.vue';
import { onMounted, ref } from 'vue';
import { getCommentNumAPI } from '@/api/comment';
import { getUVPVAPI } from '@/api/uvpv';
import { getUserNumAPI } from '@/api/adminUser';
import { getArticleListAPI, getArticleNumAPI } from '@/api/adminArticle';
import { getArticleByTimeAPI } from '@/api/article';

const numberData = ref([
  { title: "文章", number: 0, icon: "article.svg" },
  { title: "用户", number: 0, icon: "user.svg" },
  { title: "评论", number: 0, icon: "comment.svg" },
  { title: "浏览量", number: 0, icon: "visible.svg" }
]);
const quickFindData = [
  { title: "个人中心", url: "ManageUser", icon: "user.svg" },
  { title: "创建文章", url: "ToEditArticle", icon: "article.svg" },
  { title: "管理用户", url: "ManageUser", icon: "manageUser.svg" },
  { title: "附件上传", url: "File", icon: "file.svg" }
];
const articleList = ref([]);

onMounted(async () => {
  const [articleRes, commentRes, userRes, UVPVRes, articleListRes] = await Promise.all([
    getArticleNumAPI(),
    getCommentNumAPI(),
    getUserNumAPI(),
    getUVPVAPI(),
    getArticleListAPI()
  ]);

  numberData.value = [
    { ...numberData.value[0], number: articleRes.data },
    { ...numberData.value[1], number: userRes.data },
    { ...numberData.value[2], number: commentRes.data },
    { ...numberData.value[3], number: UVPVRes.data.uv }
  ];
  articleList.value = articleListRes.data;
  console.log(articleList.value);

})

</script>

<template>
  <HeadOperation :icon="'dashboard.svg'" :title="'仪表盘'"></HeadOperation>
  <div class="admin-container">
    <div class="info-cards">
      <template v-for="data in numberData" :key="data">
        <InfoCard :data="data"></InfoCard>
      </template>
    </div>
    <div style="margin-top:10px; display: flex; justify-content: space-between;">
      <div class="out-box">
        <h3>任意门</h3>
        <hr>
        <div class="quick-finds">
          <template v-for="data in quickFindData" :key="data">
            <QuickFind :data="data"></QuickFind>
          </template>
        </div>
      </div>
      <div class="out-box">
        <h3>最近文章</h3>
        <hr>
        <div class="recent-articles">
          <template v-for="article in articleList" :key=" article.id">
            <RecentArticle :data="article"></RecentArticle>

          </template>

        </div>
      </div>
    </div>
  </div>

</template>

<style scoped lang="scss">
.admin-container {
  background: #f1f4f8;

  .info-cards {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .out-box {
    width: 49.5%;
    background: #fff;
    border-radius: 5px;
    padding: 12px 14px;

    hr {
      margin: 6px 0;
      border: none;
      border-top: 1px solid #e2e6ed;
    }

    .quick-finds {
      width: 100%;
      display: grid;
      max-height: calc(100vh - 270px);
      grid-template-columns: repeat(auto-fill, 190px);
      gap: 10px;
      margin-top: 12px;
    }

    .recent-articles {
      max-height: calc(100vh - 270px);
      overflow: auto;

      &>:first-child {
        padding-top: 0;
      }
    }
  }


}
</style>
