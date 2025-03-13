<script setup>
import QuickFind from './components/QuickFind.vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import InfoCard from './components/InfoCard.vue';
import RecentArticle from './components/RecentArticle.vue';
</script>

<template>
  <HeadOperation :icon="'dashboard.svg'" :title="'仪表盘'"></HeadOperation>
  <div class="admin-container">
    <div class="info-cards">
      <InfoCard></InfoCard>
      <InfoCard></InfoCard>
      <InfoCard></InfoCard>
      <InfoCard></InfoCard>
    </div>
    <div style="margin-top:10px; display: flex; justify-content: space-between;">
      <div class="out-box">
        <h3>任意门</h3>
        <hr>
        <div class="quick-finds">
          <QuickFind></QuickFind>
          <QuickFind></QuickFind>
          <QuickFind></QuickFind>
          <QuickFind></QuickFind>
          <QuickFind></QuickFind>
        </div>
      </div>
      <div class="out-box">
        <h3>最近文章</h3>
        <hr>
        <div class="recent-articles">
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
          <RecentArticle></RecentArticle>
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
