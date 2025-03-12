<script setup>
import EditArticleCard from './components/EditArticleCard.vue';
import { ref, watch } from 'vue';
import { Search } from '@element-plus/icons-vue'
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import router from '@/router';
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
const inputValue = ref('');

const selectedRows = ref([]);

const showSearch = ref(true);

const creatArticle = () => {
  router.push({ name: 'ToEditArticle' })
}

const getCategories = () => {
  console.log('getCategories')
}
const buttonArr = [
  { name: "分类", onClick: getCategories },
  { name: "新建", onClick: creatArticle },
];


const handleSelectionChange = (selection) => {
  selectedRows.value = selection;
  console.log("选中的行数据：", selectedRows.value);
};

watch(() => selectedRows.value.length, newLength => {
  if (newLength > 0) showSearch.value = false
  else showSearch.value = true
})

</script>

<template>

  <HeadOperation :title="'文章'" :icon="'article.svg'" :button-arr="buttonArr">
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

      <div class="search-delete">
        <el-input v-if="showSearch" v-model="inputValue" style="max-width: 300px;" placeholder="输入关键词搜索">
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
        <el-button v-else type="danger">删除</el-button>
      </div>
    </div>

  </div>



</template>

<style scoped lang="scss">
.edit-article {
  position: relative;
  padding: 10px 20px;



  .search-delete {
    position: absolute;
    top: 10px;
    left: 87px;
    z-index: 1;
  }
}
</style>
