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
    <!-- <el-table ref="multipleTableRef" :data="tableData" row-key="id" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" :selectable="selectable" width="55" />
        <el-table-column style="width:  calc(100% - 55px);">
          <template #default="{ row }">
            <EditArticleCard></EditArticleCard>
          </template>
        </el-table-column>
      </el-table>

      <div class="search-delete">
        <el-input v-if="showSearch" v-model="inputValue" style="max-width: 300px;" placeholder="输入关键词搜索">
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
        <el-button v-else type="danger">删除</el-button>
      </div> -->
  </div>



</template>

<style scoped lang="scss">
.edit-article {
  padding: 10px 20px;
}
</style>
