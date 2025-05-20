<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue';
import ArticleNav from './components/ArticleNav.vue';
import { getArticleByTimeAPI } from '@/api/article';

let tempPaddingLeft = 0;
let tempPaddingRight = 0;
const articles = ref([]);
onMounted(async () => {
  const res = await getArticleByTimeAPI();
  articles.value = res.data;
  const selectionDiv = document.querySelector('.selection');
  if (selectionDiv) {
    tempPaddingLeft = selectionDiv.style.paddingLeft;
    tempPaddingRight = selectionDiv.style.paddingRight;
    selectionDiv.style.paddingLeft = '0';
    selectionDiv.style.paddingRight = '0';
  }

})

onBeforeUnmount(() => {
  const selectionDiv = document.querySelector('.selection');
  if (selectionDiv) {
    selectionDiv.style.paddingLeft = tempPaddingLeft;
    selectionDiv.style.paddingRight = tempPaddingRight;
  }
})
</script>

<template>
  <router-view :articles="articles"></router-view>
</template>

<style scoped lang="scss"></style>
