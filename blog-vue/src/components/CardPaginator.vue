<script setup>
import { ref, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const numberOfPage = ref(28);
const showNum = ref(10);
const route = useRoute();
const router = useRouter();
const pageId = computed(() => {
  return parseInt(route.params.pageId) || 1;
});
const hiddenStartPage = ref(0);
const hiddenEndPage = ref(0);
const nextPage = async () => {
  await router.push(`/home/page/${pageId.value + 1}`);
  updatePages();
};
const lastPage = async () => {
  await router.push(`/home/page/${pageId.value - 1}`);
  updatePages();
};

const allPages = ref([]);
const updatePages = () => {
  showNum.value = Math.max(showNum.value, 7)
  if (pageId.value > numberOfPage.value) {
    //TODO 路由到404去
    return;
  }
  const pages = [];
  if (pageId.value >= showNum.value - 1) {
    pages.push(1, -1);
    if (pageId.value >= numberOfPage.value - (showNum.value - 3)) {
      for (let i = numberOfPage.value - (showNum.value - 3); i <= numberOfPage.value; i++) {
        pages.push(i);
      }
      hiddenStartPage.value = numberOfPage.value - (showNum.value - 2);
      hiddenEndPage.value = numberOfPage.value;
      allPages.value = pages;
      return;
    }

    const num = (showNum.value - 5) / 2;
    let addNum = num;
    let reduceNum = num;
    if (num < Math.ceil(num)) {
      addNum = Math.ceil(num);
      reduceNum = Math.floor(num);
    }
    for (let i = pageId.value - reduceNum; i <= pageId.value + addNum && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
    if (pageId.value + addNum < numberOfPage.value) {
      pages.push(-2, numberOfPage.value);
    }

    hiddenStartPage.value = pageId.value - reduceNum;
    hiddenEndPage.value = pageId.value + addNum;


  } else {
    if (showNum.value >= numberOfPage.value) {
      for (let i = 1; i <= showNum.value; i++) {
        pages.push(i);
      }
    } else {
      for (let i = 1; i <= showNum.value - 2; i++) {
        pages.push(i);
      }
      pages.push(-2, numberOfPage.value);
      hiddenStartPage.value = 0;
      hiddenEndPage.value = showNum.value - 1;
    }
  }
  allPages.value = pages;
};
updatePages();
const lastNumOfPage = () => {
  const pages = [];
  if (hiddenStartPage.value <= showNum.value - 2) {
    for (let i = 1; i <= showNum.value - 2 && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
    allPages.value = pages;
    hiddenEndPage.value = showNum.value - 1;
    pages.push(-2, numberOfPage.value);
    return;
  }

  if (hiddenStartPage.value <= showNum.value - 2) {
    for (let i = 1; i <= showNum.value - 2 && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
  }
  else {
    pages.push(1, -1);
    for (let i = hiddenStartPage.value - (showNum.value - 5); i <= hiddenStartPage.value && i <= numberOfPage.value; i++) {
      pages.push(i);
    }
  }
  pages.push(-2, numberOfPage.value);
  allPages.value = pages;
  hiddenEndPage.value = hiddenStartPage.value + 1;
  hiddenStartPage.value = Math.max(showNum.value - 2, hiddenStartPage.value - (showNum.value - 3));

}
const nextNumOfPage = () => {
  const pages = [];
  pages.push(1, -1);
  if (hiddenEndPage.value >= numberOfPage.value - (showNum.value - 2)) {
    for (let i = numberOfPage.value - (showNum.value - 3); i <= numberOfPage.value; i++) {
      pages.push(i);
    }
    allPages.value = pages;
    hiddenStartPage.value = numberOfPage.value - (showNum.value - 2);
    return;
  }
  for (let i = hiddenEndPage.value; i <= hiddenEndPage.value + showNum.value - 5 && i <= numberOfPage.value; i++) {
    pages.push(i);
  }
  if (hiddenEndPage.value + showNum.value - 5 < numberOfPage.value) pages.push(-2, numberOfPage.value);
  allPages.value = pages;
  hiddenStartPage.value = Math.max(showNum.value - 2, hiddenEndPage.value - 1);
  hiddenEndPage.value = hiddenEndPage.value + showNum.value - 4;
}
</script>

<template>
  <div class="pagination">
    <div v-if="pageId === 1" :style="{ width: '58px', height: '31px' }"></div>
    <button v-else class="last-page" @click="lastPage">上一页</button>
    <div class="number-of-page">
      <template v-for="i in allPages" :key="i">
        <button v-if="i === -1" class="number-router-link " @click="lastNumOfPage()">... </button>
        <button v-else-if="i === -2" class="number-router-link " @click="nextNumOfPage()">...</button>
        <RouterLink v-else :to="'/home/page/' + i" class="number-router-link" :class="{ active: i === pageId }">
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
