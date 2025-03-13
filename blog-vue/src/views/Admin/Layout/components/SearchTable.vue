<script setup>
import { Search } from '@element-plus/icons-vue'
import { ref, watch } from 'vue';
const inputValue = ref('');

const props = defineProps({
  searchFunction: Function,
  deleteFunction: Function,
  tableData: Array,
});


console.log(props.tableData);


const selectedRows = ref([]);
const showSearch = ref(true);
const handleSelectionChange = (selection) => {
  selectedRows.value = selection;
  console.log("选中的行数据：", selectedRows.value);
};

watch(() => selectedRows.value.length, newLength => {
  if (newLength > 0) showSearch.value = false;
  else showSearch.value = true;
})

const toSearch = () => {
  props.searchFunction('321321');
}

const toDelete = () => {
  props.deleteFunction();
}

</script>

<template>
  <div class="search-table" v-if="props.tableData">
    <el-table :data="props.tableData" row-key="id" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column style="width:  calc(100% - 55px);">
        <template #default="{ row }">
          <slot :row="row"></slot>
        </template>
      </el-table-column>
    </el-table>

    <div class="search-delete">
      <el-input v-if="showSearch" v-model="inputValue" style="max-width: 300px;" placeholder="输入关键词搜索"
        @keydown.enter="toSearch">
        <template #append>
          <el-button :icon="Search" @click="toSearch" />
        </template>
      </el-input>
      <el-button v-else type="danger" @click="toDelete">删除</el-button>
    </div>
  </div>
</template>

<style scoped lang="scss">
.search-table {
  position: relative;

  .search-delete {
    position: absolute;
    z-index: 1;
    top: 0px;
    left: 65px;
  }
}
</style>
