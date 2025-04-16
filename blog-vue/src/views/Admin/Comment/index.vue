<script setup>
import CommentCard from './components/CommentCard.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import { onMounted, ref } from 'vue';
import { getAllCommentAPI } from '@/api/comment';

const tableData = ref([]);

const deleteFunction = () => {

}

const searchFunction = () => {

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
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="tableData">
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
