<script setup>
import HeadOperation from '../Layout/components/HeadOperation.vue';
import LinkCard from './components/LinkCard.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import AddLink from './components/AddLink.vue';
import { onMounted, ref } from 'vue';
import { deleteLinkAPI, getLinksAPI, searchLinksByNameAPI } from '@/api/links';
import { ElMessage } from 'element-plus';

const tableData = ref([]);


const showAddLink = ref(false);
const addLink = () => {
  showAddLink.value = true;
}

const buttonArr = [{
  name: '添加',
  onClick: addLink
}]

const deleteFunction = async (selectedRows) => {
  const idsToDelete = selectedRows.map(item => item.id);
  await deleteLinkAPI(idsToDelete);
  tableData.value = tableData.value.filter(item => !idsToDelete.includes(item.id));
  ElMessage.success('删除成功!');
}

const searchFunction = async (searchInfo) => {
  const res = await searchLinksByNameAPI(searchInfo);
  tableData.value = res.data;
  ElMessage.success('搜索成功!')
}
const addLinkRef = ref(null);
const handelShowLink = () => {
  showAddLink.value = false;
  addLinkRef.value.resetState();
}
const handleSubmitSuccess = async () => {
  const res = await getLinksAPI();
  tableData.value = res.data;
}

const handleDeleteSuccess = (deletedId) => {
  tableData.value = tableData.value.filter(item => item.id !== deletedId);
};
onMounted(async () => {
  const res = await getLinksAPI();
  tableData.value = res.data;
})
</script>

<template>
  <HeadOperation :title="'友链'" :icon="'links.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <SearchTable @to-delete="deleteFunction" @to-search="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <LinkCard :data="row" @delete-success="handleDeleteSuccess" />
        </template>
      </SearchTable>
    </div>
    <el-dialog title="添加友链" v-model="showAddLink" width="550px" style="overflow: auto;" @close="handelShowLink">
      <AddLink :handelShowLink="handelShowLink" ref="addLinkRef" @submit-success="handleSubmitSuccess"></AddLink>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.edit-article {
  padding: 15px 20px;
}
</style>
