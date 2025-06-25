<script setup>
import CommentCard from './components/CommentCard.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import { onMounted, ref } from 'vue';
import { deleteCommentAPI, getAllCommentAPI, searchCommentByContentAPI } from '@/api/comment';
import { ElMessage } from 'element-plus';
const tableData = ref([]);

const deleteFunction = async (selectedRows) => {
  const idsToDelete = selectedRows.map(item => item.id);
  await deleteCommentAPI(idsToDelete);
  tableData.value = tableData.value.filter(item => !idsToDelete.includes(item.id));
  ElMessage.success('删除成功!');
}

const searchFunction = async (searchInfo) => {
  const res = await searchCommentByContentAPI(searchInfo);
  tableData.value = res.data;
  ElMessage.success('搜索成功!')
}
const handlePassSuccess = (id) => {
  const index = tableData.value.findIndex(item => item.id === id);
  tableData.value[index].status = true;
}

const handleDeleteSuccess = (id) => {
  tableData.value = tableData.value.filter(item => item.id !== id);
}
onMounted(async () => {
  const res = await getAllCommentAPI();
  tableData.value = res.data;
})
</script>

<template>
  <HeadOperation :title="'评论'" :icon="'comment.svg'"></HeadOperation>
  <div class="admin-container">
    <div class="comment">
      <SearchTable @to-delete="deleteFunction" @to-search="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <CommentCard :data="row" @delete-success="handleDeleteSuccess" @pass-success="handlePassSuccess" />
        </template>
      </SearchTable>
    </div>
  </div>
</template>

<style scoped lang="scss">
.comment {
  padding: 15px 20px;
}
</style>
