<script setup>
import HeadOperation from '../Layout/components/HeadOperation.vue';
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
</script>

<template>
  <HeadOperation :title="'友链'" :icon="'article.svg'">
    <ListOperation></ListOperation>
  </HeadOperation>
  <div class="admin-container">
    <div class="edit-article">
      <el-table ref="multipleTableRef" :data="tableData" row-key="id" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" :selectable="selectable" width="55" />
        <el-table-column style="width:  calc(100% - 55px);">
          <template #default="{ row }">
            <EditArticleCard></EditArticleCard>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
