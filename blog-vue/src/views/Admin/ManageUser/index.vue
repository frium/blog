<script setup>
import HeadOperation from '../Layout/components/HeadOperation.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import UserInfoCard from './components/UserInfoCard.vue';
const createUser = () => {

}
const buttonArr = [
  { name: '添加', onClick: createUser }
];

const deleteFunction = () => {

}

const searchFunction = () => {

}

const tableData = [
  { username: "张三", auth: 1, createTime: "2024-03-10 12:00:00" },
  { username: "李四", auth: 2, createTime: "2024-03-11 15:30:00" },
  { username: "王五", auth: 3, createTime: "2024-03-12 09:45:00" }
];
</script>

<template>
  <HeadOperation :title="'用户'" :icon="'manageUser.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <div class="manage-user">
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <UserInfoCard :data="row" />
        </template>
      </SearchTable>
    </div>
  </div>
</template>

<style scoped lang="scss">
.manage-user {
  padding: 15px 20px;

}
</style>
