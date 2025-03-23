<script setup>
import HeadOperation from '../Layout/components/HeadOperation.vue';
import LinkCard from './components/LinkCard.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import AddLink from './components/AddLink.vue';
import { onMounted, ref } from 'vue';
import { getLinksAPI } from '@/api/links';

const tableData = ref([]);


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
const addLinkRef = ref(null);
const handelShowLink = () => {
  showAddLink.value = false;
  addLinkRef.value.resetState();
}

onMounted(async () => {
  const res = await getLinksAPI();
  tableData.value = res.data;
})
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
    </div>
    <el-dialog title="添加友链" v-model="showAddLink" width="550px" style="overflow: auto;" @close="handelShowLink">
      <AddLink :handelShowLink="handelShowLink" ref="addLinkRef"></AddLink>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.edit-article {
  padding: 15px 20px;
}
</style>
