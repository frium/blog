<script setup>
import HeadOperation from '../Layout/components/HeadOperation.vue';
import LinkCard from './components/LinkCard.vue';
import { Search } from '@element-plus/icons-vue'
import AddLink from './components/AddLink.vue';
import { ref } from 'vue';
const tableData = [
  {
    id: 1,

  },
  {
    id: 2,

  },
  {
    id: 3,
  },
  {
    id: 4,
  },
  {
    id: 5,
  },
  {
    id: 6,
  },
  {
    id: 7,
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
</script>

<template>
  <HeadOperation :title="'友链'" :icon="'links.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <el-table ref="multipleTableRef" :data="tableData" row-key="id" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" :selectable="selectable" width="55" />
        <el-table-column style="width:  calc(100% - 55px);">
          <template #default="{ row }">
            <LinkCard></LinkCard>
          </template>
        </el-table-column>
      </el-table>
      <div class="search">
        <el-input v-model="inputValue" style="max-width: 300px;" placeholder="输入关键词搜索">
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </div>
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

  .search {
    position: absolute;
    z-index: 1;
    top: 10px;
    left: 90px;
  }
}
</style>
