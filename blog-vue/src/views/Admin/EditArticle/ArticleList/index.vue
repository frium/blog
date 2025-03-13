<script setup>
import EditArticleCard from './components/EditArticleCard.vue';
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import SearchTable from '../../Layout/components/SearchTable.vue';
import router from '@/router';

const tableData = [
  {
    id: 1,
    title: 'Vue.js 官方文档',
    link: 'https://vuejs.org',
  },
  {
    id: 2,
    title: 'Element Plus 组件库',
    link: 'https://element-plus.org',
  },
  {
    id: 3,
    title: 'Longfish 的个人博客',
    link: 'https://longfish.site',
  },
  {
    id: 4,
    title: 'GitHub 主页',
    link: 'https://github.com',
  },
  {
    id: 5,
    title: 'MDN Web 文档',
    link: 'https://developer.mozilla.org',
  },
];

const creatArticle = () => {
  router.push({ name: 'ToEditArticle' })
}

const getCategories = () => {
  console.log('getCategories')
}
const buttonArr = [
  { name: "分类", onClick: getCategories },
  { name: "新建", onClick: creatArticle },
];

const deleteFunction = () => {

}
const searchFunction = () => {

}
</script>

<template>

  <HeadOperation :title="'文章'" :icon="'article.svg'" :button-arr="buttonArr">
  </HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <EditArticleCard :data="row"></EditArticleCard>
        </template>
      </SearchTable>
    </div>
  </div>

</template>

<style scoped lang="scss">
.edit-article {
  padding: 15px 20px;
}
</style>
