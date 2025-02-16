<script setup>
import { ref, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const numberOfPage = ref(99);

const route = useRoute();
const router = useRouter();
const pageId = computed(() => {
  return parseInt(route.params.pageId) || 1;
});
const hiddenStartPage = ref(0);
const hiddenEndPage = ref(0);
const nextPage = () => {
  router.push(`/page/${pageId + 1}`)
};
const lastPage = () => {
  router.push(`/page/${pageId - 1}`)
};
const allPages = ref([]);
const updatePages = () => {
  const pages = [];
  if (pageId >= 8) {
    pages.push(1, -1);
    for (let i = pageId - 3; i <= pageId + 3 && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
    hiddenStartPage.value = pageId - 3;
    hiddenEndPage.value = pageId + 3;
    if (pageId + 3 < numberOfPage.value) pages.push(-2, numberOfPage.value);
  } else {
    for (let i = 1; i <= 8 && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
    if (pages.length === 8) pages.push(-2, numberOfPage.value);
    hiddenEndPage.value = 9;
  }
  allPages.value = pages;
};
updatePages();
const lastNumOfPage = () => {
  const pages = [];
  if (hiddenStartPage.value - 8 < 8) {
    for (let i = 1; i <= 8 && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
  }
  else {
    pages.push(1, -1);
    for (let i = hiddenStartPage.value - 7; i <= hiddenStartPage.value && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
  }
  pages.push(-2, numberOfPage.value);
  allPages.value = pages;
  hiddenEndPage.value = hiddenStartPage.value;
  hiddenStartPage.value = hiddenStartPage.value - 8;
}
const nextNumOfPage = () => {
  const pages = [];
  pages.push(1, -1);
  for (let i = hiddenEndPage.value; i <= hiddenEndPage.value + 7 && i <= numberOfPage.value; i++) {
    pages.push(i);
  }
  if (hiddenEndPage.value + 7 < numberOfPage.value) pages.push(-2, numberOfPage.value);
  allPages.value = pages;
  hiddenStartPage.value = hiddenEndPage.value;
  hiddenEndPage.value = hiddenEndPage.value + 8;
}
</script>

<template>
  <div class="pagination">
    <div v-if="pageId === 1" :style="{ width: '58px', height: '31px' }"></div>
    <button v-else class="last-page" @click="lastPage">上一页</button>
    <div class="number-of-page">
      <template v-for="i in allPages" :key="i">
        <button v-if="i === -1" class="number-router-link last-num-of-page" @click="lastNumOfPage()">...</button>
        <button v-else-if="i === -2" class="number-router-link next-num-of-page" @click="nextNumOfPage()">...</button>
        <RouterLink v-else :to="'/page/' + i" class="number-router-link" :class="{ active: i === pageId }">
          {{ i }}
        </RouterLink>
      </template>
    </div>
    <div v-if="pageId === numberOfPage" :style="{ width: '58px', height: '31px' }"></div>
    <button v-else class="next-page" @click="nextPage">下一页</button>
  </div>
</template>

<style scoped lang="scss">
.pagination {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 32px;

  .number-of-page {
    .number-router-link {
      display: inline-block;
      line-height: 32px;
      text-align: center;
      margin: 3.5px;
      border-radius: 4px;
      background: rgba(40, 44, 52, 0.6);
      color: rgb(222, 226, 231);
      width: 32px;
      height: 32px;
    }

    .active {
      background: #434A56;
    }

    .last-num-of-page {}

    .next-num-of-page {}
  }

  .last-page,
  .next-page {
    background: rgba(40, 44, 52, 0.6);
    width: 58px;
    height: 31px;
    padding: 4.5px 7px;
    color: rgb(222, 226, 231);
    font-size: 14px;
    border-radius: 4px;
  }
}
</style>
