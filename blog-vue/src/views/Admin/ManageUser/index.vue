<script setup>
import { onMounted, ref } from 'vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import UserInfoCard from './components/UserInfoCard.vue';
import { getUsersAPI } from '@/api/adminUser';
import CreatUser from './components/CreatUser.vue';

const showCreateUser = ref(false);

const createUser = () => {
  showCreateUser.value = true;
}
const buttonArr = [
  { name: '添加', onClick: createUser }
];

const deleteFunction = () => {

}

const searchFunction = () => {

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
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="tableData">
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
