<script setup>
import HeadOperation from '../Layout/components/HeadOperation.vue';
import LinkCard from './components/LinkCard.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import AddLink from './components/AddLink.vue';
import { ref } from 'vue';

const tableData = [
  {
    id: 1,
    title: 'Vue.js 官方文档',
    link: 'https://vuejs.org',
  },
  {
    id: 2,
    title: 'Element Plus 组件库',
    link: 'https://element-plus.org',
  },
  {
    id: 3,
    title: 'Longfish 的个人博客',
    link: 'https://longfish.site',
  },
  {
    id: 4,
    title: 'GitHub 主页',
    link: 'https://github.com',
  },
  {
    id: 5,
    title: 'MDN Web 文档',
    link: 'https://developer.mozilla.org',
  },
];


const showAddLink = ref(false);
const addLink = () => {
  showAddLink.value = true;
}

const buttonArr = [{
  name: '添加',
  onClick: addLink
}]

const deleteFunction = () => {
  console.log('del');
}

const searchFunction = (test) => {
  console.log(test);

}
</script>

<template>
  <HeadOperation :title="'友链'" :icon="'links.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <LinkCard :data="row" />
        </template>
      </SearchTable>
      <!-- <el-table ref="multipleTableRef" :data="tableData" row-key="id" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" :selectable="selectable" width="55" />
        <el-table-column style="width:  calc(100% - 55px);">
          <template #default="{ row }">
            <LinkCard></LinkCard>
          </template>
</el-table-column>
</el-table>
<div class="search">
  <el-input v-if="showSearch" v-model="inputValue" style="max-width: 300px;" placeholder="输入关键词搜索">
    <template #append>
            <el-button :icon="Search" />
          </template>
  </el-input>
  <el-button v-else type="danger">删除</el-button>
</div> -->
    </div>
    <el-dialog title="添加友链" v-model="showAddLink" width="550px" style="overflow: auto;">
      <AddLink></AddLink>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.edit-article {
  position: relative;
  padding: 10px 20px;


}
</style>
