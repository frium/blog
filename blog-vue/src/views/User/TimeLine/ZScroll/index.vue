<script setup>
import ZScroll from '@/components/ZScroll.vue';
import { onBeforeUnmount, onMounted, ref } from 'vue';
import TimeLineCard from '../components/TimeLineCard.vue';
import { getArticleByTimeAPI } from '@/api/article';


const articles = ref([]);

let tempPaddingLeft = 0;
let tempPaddingRight = 0;
onMounted(async () => {
  const selectionDiv = document.querySelector('.selection');
  if (selectionDiv) {
    tempPaddingLeft = selectionDiv.style.paddingLeft;
    tempPaddingRight = selectionDiv.style.paddingRight;
    selectionDiv.style.paddingLeft = '0';
    selectionDiv.style.paddingRight = '0';
  }
  const res = await getArticleByTimeAPI();
  articles.value = res.data;
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
  <div class="z-scroll">
    <h2 class="title">请滑动滚轮~</h2>
    <ZScroll>
      <template v-for="(article, index) in articles" :key="article.id">
        <TimeLineCard :data-order="index" :class="['list-item', `position${(index % 8 + 1)}`]" :data="article" />
      </template>
    </ZScroll>
  </div>
</template>

<style scoped lang="scss">
.z-scroll {
  position: relative;
}

.title {
  position: absolute;
  text-align: center;
  top: 40vh;
  left: 50vw;
  transform: translate(-50%, -50%);
  letter-spacing: 10px;
}

.list-item {
  position: absolute;
}

.position1 {
  top: 10%;
  left: -5%;
}

.position2 {
  bottom: 5%;
  right: -5%;

}

.position3 {
  top: 0%;
  right: 10%;
}

.position4 {
  bottom: 0%;
  left: 10%;
}

.position5 {
  top: 0%;
  left: 0%;
}

.position6 {
  bottom: 0%;
  right: 2%;
}

.position7 {
  top: 10%;
  right: 5%;
}

.position8 {
  left: 10%;
  bottom: 2%;
}
</style>
