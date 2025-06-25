<script setup>
import SearchTable from '../Layout/components/SearchTable.vue';
import FileInfoCard from './components/FileInfoCard.vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import { onMounted, ref } from 'vue';
import UploadFileDialog from './components/UploadFileDialog.vue';
import { deleteFileAPI, getAllFilesAPI, searchFileByNameAPI } from '@/api/file';
import { ElMessage } from 'element-plus';

const fileData = ref([]);
const uploadFileDialog = ref(null);
const deleteFunction = async (selectedRows) => {
  const idsToDelete = selectedRows.map(item => item.id);
  await deleteFileAPI(idsToDelete);
  fileData.value = fileData.value.filter(item => !idsToDelete.includes(item.id));
  ElMessage.success('删除成功!');
}

const searchFunction = async (searchInfo) => {
  const res = await searchFileByNameAPI(searchInfo);
  fileData.value = res.data;
  ElMessage.success('搜索成功!')
}
const showUploadFile = ref(false);
const toUploadFile = () => {
  showUploadFile.value = true;
}
const buttonArr = [
  { name: '上传', onClick: toUploadFile }
]

const handleDialogClosed = async () => {
  if (uploadFileDialog.value) {
    uploadFileDialog.value.resetState();
  }
  const res = await getAllFilesAPI();
  fileData.value = res.data;
};
const handleDeleteSuccess = (deletedId) => {
  fileData.value = fileData.value.filter(item => item.id !== deletedId);
};


onMounted(async () => {
  const res = await getAllFilesAPI();
  fileData.value = res.data;
})
</script>

<template>
  <HeadOperation :title="'附件'" :icon="'file.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <div class="file">
      <SearchTable @to-delete="deleteFunction" @to-search="searchFunction" :table-data="fileData">
        <template v-slot:default="{ row }">
          <FileInfoCard :data="row" @delete-success="handleDeleteSuccess" />
        </template>
      </SearchTable>
    </div>
    <el-dialog title="上传附件" v-model="showUploadFile" width="920px"
      style="overflow: auto; position: fixed;top: 35%; left: 50%;transform: translate(-50%, -50%);"
      @close="handleDialogClosed">
      <UploadFileDialog ref="uploadFileDialog" />
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.file {
  padding: 15px 20px;
}
</style>
