<script setup>
import { getSearchArticleNumAPI, searchArticleAPI } from '@/api/article';
import router from '@/router';
import { useArticleStore } from '@/stores/article';
import notificationToast from '@/utils/notificationToast ';
import { ref } from 'vue';
const artilceStore = useArticleStore();
const searchInfo = ref('');
const emit = defineEmits(['search-success'])
const handelSearchArticle = async () => {
  artilceStore.searchData.searchInfo = searchInfo.value;
  const num = await getSearchArticleNumAPI(artilceStore.searchData);
  if (num.data === 0) {
    notificationToast.success("未查询到相关数据!");
    emit('search-success');
    return;
  }
  artilceStore.articleNum = num.data;
  const res = await searchArticleAPI(artilceStore.searchData);
  artilceStore.articleArr = res.data;
  notificationToast.success("查询成功!");
  searchInfo.value = "";
  emit('search-success');
  router.push({ name: 'Home' });
}
</script>

<template>
  <div class="search-out-box">
    <input v-model="searchInfo" type="text" class="search" @keyup.enter="handelSearchArticle">
    <img src="/src/assets/icons/search.svg" alt="" @click="handelSearchArticle">
  </div>
</template>

<style scoped lang="scss">
.search-out-box {
  display: flex;
  background: rgb(60, 60, 60);
  border-radius: 10px;
  padding: 0 10px 0 5px;
  width: 100%;

  .search {
    width: 100%;
    height: 45px;
    border-radius: 10px;
    margin: auto;
    background: rgb(60, 60, 60);
    padding: 10px;
    margin: 0 auto;
    color: var(--text-color);
    font-size: 16px;
  }

  img {
    cursor: url('/src/assets/cursor/Hand.cur'), pointer;
  }


}
</style>
