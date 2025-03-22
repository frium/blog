<script setup>
import EditArticleCard from './components/EditArticleCard.vue';
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import SearchTable from '../../Layout/components/SearchTable.vue';
import router from '@/router';
import { onMounted, ref } from 'vue';
import { getArticleListAPI } from '@/api/adminArticle';

const tableData = ref([]);

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
const handleDeleteSuccess = (deletedId) => {
  tableData.value = tableData.value.filter(item => item.id !== deletedId);
};
onMounted(async () => {
  const res = await getArticleListAPI();
  tableData.value = res.data;
})
</script>

<template>

  <HeadOperation :title="'文章'" :icon="'article.svg'" :button-arr="buttonArr">
  </HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <EditArticleCard :data="row" @delete-success="handleDeleteSuccess"></EditArticleCard>
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
