<script setup>
import { onMounted, ref } from 'vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import UserInfoCard from './components/UserInfoCard.vue';
import { deleteUsersAPI, getUsersAPI, searchUserByNameAPI } from '@/api/adminUser';
import CreatUser from './components/CreatUser.vue';
import { ElMessage } from 'element-plus';

const showCreateUser = ref(false);

const createUser = () => {
  showCreateUser.value = true;
}
const buttonArr = [
  { name: '添加', onClick: createUser }
];

const deleteFunction = async (selectedRows) => {
  const idsToDelete = selectedRows.map(item => item.id);
  await deleteUsersAPI(idsToDelete);
  tableData.value = tableData.value.filter(item => !idsToDelete.includes(item.id));
  ElMessage.success('删除成功!');
}

const searchFunction = async (searchInfo) => {
  const res = await searchUserByNameAPI(searchInfo);
  tableData.value = res.data;
  ElMessage.success('搜索成功!')
}

const handelShowDialog = () => {
  showCreateUser.value = false;
}

const handleDeleteSuccess = async () => {
  const res = await getUsersAPI();
  tableData.value = res.data;
}

const tableData = ref([]);

onMounted(async () => {
  const res = await getUsersAPI();
  tableData.value = res.data;
})
</script>

<template>
  <HeadOperation :title="'用户'" :icon="'manageUser.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <div class="manage-user">
      <SearchTable @to-delete="deleteFunction" @to-search="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <UserInfoCard :data="row" @delete-success="handleDeleteSuccess" />
        </template>
      </SearchTable>
    </div>
    <el-dialog title="创建用户" v-model="showCreateUser" width="550px" style="overflow: auto;">
      <CreatUser @submit-success="handleDeleteSuccess" :handel-show-dialog="handelShowDialog"></CreatUser>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.manage-user {
  padding: 15px 20px;

}
</style>
