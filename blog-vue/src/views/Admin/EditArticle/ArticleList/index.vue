<script setup>
import EditArticleCard from './components/EditArticleCard.vue';
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import SearchTable from '../../Layout/components/SearchTable.vue';
import { onMounted, ref } from 'vue';
import { deleteArticleAPI, getArticleListAPI, searchArticleByNameAPI } from '@/api/adminArticle';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

const tableData = ref([]);
const router = useRouter();
const creatArticle = () => {
  router.push({ name: 'ToEditArticle' })
}

const toLabelList = () => {
  router.push({ name: 'LabelList' })
}
const buttonArr = [
  { name: "分类", onClick: toLabelList },
  { name: "新建", onClick: creatArticle },
];

const deleteFunction = async (selectedRows) => {
  const idsToDelete = selectedRows.map(item => item.id);
  await deleteArticleAPI(idsToDelete);
  tableData.value = tableData.value.filter(item => !idsToDelete.includes(item.id));
  ElMessage.success('删除成功!');
}

const searchFunction = async (searchInfo) => {
  const res = await searchArticleByNameAPI(searchInfo);
  tableData.value = res.data;
  ElMessage.success('搜索成功!');
}
const handleDeleteSuccess = (deletedId) => {
  tableData.value = tableData.value.filter(item => item.id !== deletedId);
};
const fetchArticleList = async () => {
  const res = await getArticleListAPI();
  tableData.value = res.data;
};

fetchArticleList();
</script>

<template>

  <HeadOperation :title="'文章'" :icon="'article.svg'" :button-arr="buttonArr">
  </HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <SearchTable @to-delete="deleteFunction" @to-search="searchFunction" :table-data="tableData">
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
