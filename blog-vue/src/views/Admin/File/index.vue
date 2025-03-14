<script setup>
import SearchTable from '../Layout/components/SearchTable.vue';
import FileInfoCard from './components/FileInfoCard.vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import { ref } from 'vue';
import UploadFileDialog from './components/UploadFileDialog.vue';
const fileData = [
  {
    url: 'https://example.com/image1.jpg',
    fileName: 'File1.jpg',
    fileType: 'Image',
    fileSize: '2MB',
    createTime: '2025-03-14 10:00'
  },
  {
    url: 'https://example.com/image2.jpg',
    fileName: 'File2.png',
    fileType: 'Image',
    fileSize: '1.5MB',
    createTime: '2025-03-13 08:30'
  },
  {
    url: 'https://example.com/file3.pdf',
    fileName: 'Document.pdf',
    fileType: 'PDF',
    fileSize: '3MB',
    createTime: '2025-03-12 14:45'
  }
]
const uploadFileDialog = ref(null);

const deleteFunction = () => {
}

const searchFunction = () => {

}
const showUploadFile = ref(false);
const toUploadFile = () => {
  showUploadFile.value = true;
}
const buttonArr = [
  { name: '上传', onClick: toUploadFile }
]

const handleDialogClosed = () => {
  if (uploadFileDialog.value) {
    uploadFileDialog.value.resetState();
  }
};
</script>

<template>
  <HeadOperation :title="'附件'" :icon="'file.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <div class="file">
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="fileData">
        <template v-slot:default="{ row }">
          <FileInfoCard :data="row" />
        </template>
      </SearchTable>
    </div>
    <el-dialog title="上传附件" v-model="showUploadFile" width="920px" style="overflow: auto;" @close="handleDialogClosed">
      <UploadFileDialog ref="uploadFileDialog" />
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.file {
  padding: 15px 20px;
}
</style>
