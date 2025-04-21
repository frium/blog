<script setup>
import { ref, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const props = defineProps({
  numberOfPage: Number, //总数
  showNum: Number//最多展示多少个
})
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
  props.showNum = Math.max(props.showNum, 5)
  if (pageId.value > props.numberOfPage) {
    //TODO 路由到404去
    return;
  }
  const pages = [];
  if (props.numberOfPage <= props.showNum) {
    for (let i = 0; i < props.numberOfPage; i++) {
      pages.push(i + 1);
    }
    allPages.value = pages;
    return;
  }
  if (pageId.value >= props.showNum - 1) {
    pages.push(1, -1);
    if (pageId.value >= props.numberOfPage - (props.showNum - 3)) {
      for (let i = props.numberOfPage - (props.showNum - 3); i <= props.numberOfPage; i++) {
        pages.push(i);
      }
      hiddenStartPage.value = props.numberOfPage - (props.showNum - 2);
      hiddenEndPage.value = props.numberOfPage;
      allPages.value = pages;
      return;
    }

    const num = (props.showNum - 5) / 2;
    let addNum = num;
    let reduceNum = num;
    if (num < Math.ceil(num)) {
      addNum = Math.ceil(num);
      reduceNum = Math.floor(num);
    }
    for (let i = pageId.value - reduceNum; i <= pageId.value + addNum && i <= props.numberOfPage; i++) {
      pages.push(i);
    }
    if (pageId.value + addNum < props.numberOfPage) {
      pages.push(-2, props.numberOfPage);
    }

    hiddenStartPage.value = pageId.value - reduceNum;
    hiddenEndPage.value = pageId.value + addNum;


  } else {
    if (props.showNum >= props.numberOfPage) {
      for (let i = 1; i <= props.showNum; i++) {
        pages.push(i);
      }
    } else {
      for (let i = 1; i <= props.showNum - 2; i++) {
        pages.push(i);
      }
      pages.push(-2, props.numberOfPage);
      hiddenStartPage.value = 0;
      hiddenEndPage.value = props.showNum - 1;
    }
  }
  allPages.value = pages;
};
updatePages();
const lastNumOfPage = () => {
  const pages = [];
  if (hiddenStartPage.value <= props.showNum - 2) {
    for (let i = 1; i <= props.showNum - 2 && i <= props.numberOfPage; i++) {
      pages.push(i);
    }
    allPages.value = pages;
    hiddenEndPage.value = props.showNum - 1;
    pages.push(-2, props.numberOfPage);
    return;
  }

  if (hiddenStartPage.value <= props.showNum - 2) {
    for (let i = 1; i <= props.showNum - 2 && i <= props.numberOfPage; i++) {
      pages.push(i);
    }
  }
  else {
    pages.push(1, -1);
    for (let i = hiddenStartPage.value - (props.showNum - 5); i <= hiddenStartPage.value && i <= props.numberOfPage; i++) {
      pages.push(i);
    }
  }
  pages.push(-2, props.numberOfPage);
  allPages.value = pages;
  hiddenEndPage.value = hiddenStartPage.value + 1;
  hiddenStartPage.value = Math.max(props.showNum - 2, hiddenStartPage.value - (props.showNum - 3));

}
const nextNumOfPage = () => {
  const pages = [];
  pages.push(1, -1);
  if (hiddenEndPage.value >= props.numberOfPage - (props.showNum - 2)) {
    for (let i = props.numberOfPage - (props.showNum - 3); i <= props.numberOfPage; i++) {
      pages.push(i);
    }
    allPages.value = pages;
    hiddenStartPage.value = props.numberOfPage - (props.showNum - 2);
    return;
  }
  for (let i = hiddenEndPage.value; i <= hiddenEndPage.value + props.showNum - 5 && i <= props.numberOfPage; i++) {
    pages.push(i);
  }
  if (hiddenEndPage.value + props.showNum - 5 < props.numberOfPage) pages.push(-2, props.numberOfPage);
  allPages.value = pages;
  hiddenStartPage.value = Math.max(props.showNum - 2, hiddenEndPage.value - 1);
  hiddenEndPage.value = hiddenEndPage.value + props.showNum - 4;
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
    <div v-if="pageId === props.numberOfPage" :style="{ width: '58px', height: '31px' }"></div>
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
      background: var(--bg-color);
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
    background: var(--bg-color);
    width: 58px;
    height: 31px;
    padding: 4.5px 7px;
    color: rgb(222, 226, 231);
    font-size: 14px;
    border-radius: 4px;
  }
}
</style>
