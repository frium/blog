<script setup>
import ZScroll from '@/components/ZScroll.vue';
import { ref } from 'vue';
import TimeLineCard from '../components/TimeLineCard.vue';
import ArticleNav from '../components/ArticleNav.vue';

const isShowArticleNav = ref(false);
const handelShowArticleNav = () => {
  isShowArticleNav.value = !isShowArticleNav.value;
}
const props = defineProps({
  articles: Object
})
const zscrollRef = ref(null);
const handelToArticle = (articleNum) => {
  zscrollRef.value.scrollToArticle(articleNum + 2);
}

</script>

<template>
  <div class="z-scroll">
    <h2 class="title">请滑动滚轮~</h2>
    <ZScroll ref="zscrollRef">
      <template v-for="(article, index) in props.articles" :key="article.id">
        <TimeLineCard :id="`timeline-card-${index}`" :data-order="index"
          :class="['list-item', `position${(index % 8 + 1)}`]" :data="article" />
      </template>
    </ZScroll>
    <!-- <div class="article-nav-out" :style="{ transform: isShowArticleNav ? 'translateX(0)' : 'translateX(100%)' }">
      <div class="triangle">
        <img @click="handelShowArticleNav" :style="{ transform: !isShowArticleNav ? 'rotate(180deg)' : 'rotate(0)' }"
          src="@/assets/icons/toRightArrow.svg" alt="">
      </div>
      <ArticleNav @to-article="handelToArticle" :articles="props.articles"></ArticleNav>
    </div> -->
  </div>
</template>

<style scoped lang="scss">
.z-scroll {
  position: relative;

  .title {
    position: absolute;
    text-align: center;
    top: 40vh;
    left: 50vw;
    transform: translate(-50%, -50%);
    letter-spacing: 10px;
  }

  .article-nav-out {
    position: sticky;
    width: 240px;
    background: var(--bg-color);
    bottom: 30%;
    left: 97%;
    border-radius: 6px;
    z-index: 999;
    transform: translateX(100%);
    transition: all 0.5s;

    .triangle {
      position: absolute;
      top: 50%;
      left: -20px;
      width: 20px;
      height: 45px;
      transform: translate(0, -50%);
      background: var(--bg-color);
      display: flex;
      align-items: center;

      img {
        transform: rotate(180deg);
        transition: all 0.5s;
      }
    }

  }
}



.list-item {
  position: absolute;
  opacity: 0;
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
