<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { getArticleByTimeAPI } from '@/api/article';

let tempPaddingLeft = '';
let tempPaddingRight = '';
let hasAdjustedPadding = false;
const articles = ref([]);

onMounted(() => {
  const selectionDiv = document.querySelector('.selection');
  if (!selectionDiv) return;

  // store original inline styles (could be empty string, meaning “use CSS rules”)
  tempPaddingLeft = selectionDiv.style.paddingLeft;
  tempPaddingRight = selectionDiv.style.paddingRight;

  selectionDiv.style.paddingLeft = '0';
  selectionDiv.style.paddingRight = '0';
  hasAdjustedPadding = true;
});

const fetchArticles = async () => {
  const res = await getArticleByTimeAPI();
  const list = Array.isArray(res.data) ? res.data : [];

  // sort newest first (createTime: "YYYY-MM-DD HH:mm:ss")
  articles.value = list
    .slice()
    .sort((a, b) => String(b?.createTime ?? '').localeCompare(String(a?.createTime ?? '')));
};

fetchArticles();

onBeforeUnmount(() => {
  if (!hasAdjustedPadding) return;

  // wait for route transition
  setTimeout(() => {
    const selectionDiv = document.querySelector('.selection');
    if (!selectionDiv) return;

    selectionDiv.style.paddingLeft = tempPaddingLeft;
    selectionDiv.style.paddingRight = tempPaddingRight;
  }, 300);
});
</script>

<template>
  <router-view :articles="articles"></router-view>
</template>

<style scoped lang="scss"></style>
