<script setup>
import LabelListCard from './components/LabelListCard.vue';
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import SearchTable from '../../Layout/components/SearchTable.vue';
import { onMounted, ref } from 'vue';
import { getLabelsAPI } from '@/api/article';
import { deleteLabelAPI, uploadLabelAPI } from '@/api/adminArticle';
import { ElMessage } from 'element-plus';

const tableData = ref([]);
const showCreateLabel = ref(false);
const createLabelValue = ref('');
const HandelShowCreatLabel = () => {
  showCreateLabel.value = true;
}

const createLabel = async () => {
  await uploadLabelAPI(createLabelValue.value);
  ElMessage.success('创建成功!');
  close();
  const res = await getLabelsAPI();
  tableData.value = res.data;
}
const buttonArr = [
  { name: "新建", onClick: HandelShowCreatLabel }
];

const deleteFunction = async (selectedRows) => {
  const idsToDelete = selectedRows.map(item => item.id);
  await deleteLabelAPI(idsToDelete);
  tableData.value = tableData.value.filter(item => !idsToDelete.includes(item.id));
  ElMessage.success('删除成功!');
}
const searchFunction = () => {

}
const handleDeleteSuccess = (deletedId) => {
  tableData.value = tableData.value.filter(item => item.id !== deletedId);
};

const close = () => {
  showCreateLabel.value = false;
  createLabelValue.value = "";
}

onMounted(async () => {
  const res = await getLabelsAPI();
  tableData.value = res.data;
})
</script>

<template>
  <HeadOperation :title="'文章标签'" :icon="'article.svg'" :button-arr="buttonArr">
  </HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <SearchTable @to-delete="deleteFunction" @to-search="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <LabelListCard :data="row" @delete-success="handleDeleteSuccess"> </LabelListCard>
        </template>
      </SearchTable>
    </div>
    <el-dialog title="创建标签" v-model="showCreateLabel" width="400px" :before-close="close">
      <el-input v-model="createLabelValue" placeholder="请输入标签名称"></el-input>
      <div style="display: flex; margin-top: 10px;">
        <el-button text type="primary" @click="createLabel">确认</el-button>
        <el-button text type="danger" @click="close">取消</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<style scoped lang="scss">
.edit-article {
  padding: 15px 20px;
}
</style>
