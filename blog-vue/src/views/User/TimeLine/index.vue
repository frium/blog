<script setup>
import { onBeforeUnmount, onMounted, ref, unref } from 'vue';
import { getArticleByTimeAPI } from '@/api/article';
import { useHead } from '@vueuse/head';

let tempPaddingLeft = 0;
let tempPaddingRight = 0;
const articles = ref([]);
const selectionDiv = document.querySelector('.selection');
if (selectionDiv) {
  tempPaddingLeft = selectionDiv.style.paddingLeft;
  tempPaddingRight = selectionDiv.style.paddingRight;
  selectionDiv.style.paddingLeft = '0';
  selectionDiv.style.paddingRight = '0';
}
const keywords = ref('');
useHead({
  meta: [
    {
      name: 'keywords',
      content: keywords
    }
  ]
})
onMounted(async () => {
  const res = await getArticleByTimeAPI();
  articles.value = res.data;
  keywords.value = unref(articles).map(article => article.title).join(',')
})

onBeforeUnmount(() => {
  setTimeout(() => {
    const selectionDiv = document.querySelector('.selection');
    if (selectionDiv) {
      selectionDiv.style.paddingLeft = tempPaddingLeft;
      selectionDiv.style.paddingRight = tempPaddingRight;
    }
  }, 300)
})
</script>

<template>
  <router-view :articles="articles"></router-view>
</template>

<style scoped lang="scss"></style>
