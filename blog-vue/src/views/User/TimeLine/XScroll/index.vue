<script setup>
import XScroll from '@/components/XScroll.vue';
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

</script>

<template>
  <div class="x-scrroll-out-box">
    <XScroll class="x-scroll">
      <div class="time-line-cards">
        <TimeLineCard v-for="(article, index) in props.articles" :key="article.id"
          :class="['card', { even: index % 2 === 0 }]" :data="article" />
      </div>
    </XScroll>
    <div class="article-nav-out" :style="{ transform: !isShowArticleNav ? 'translateX(100%)' : 'translateX(0)' }">
      <div class="triangle">
        <img @click="handelShowArticleNav" :style="{ transform: !isShowArticleNav ? 'rotate(180deg)' : 'rotate(0)' }"
          src="@/assets/icons/toRightArrow.svg" alt="">
      </div>
      <ArticleNav :articles="props.articles"></ArticleNav>
    </div>

  </div>
</template>

<style scoped lang="scss">
.x-scrroll-out-box {
  position: relative;

  .article-nav-out {
    position: sticky;
    width: 240px;
    background: var(--bg-color);
    bottom: 50%;
    left: 97%;
    border-radius: 6px;
    z-index: 999;
    transform: translateX(0);
    transition: all 0.5s;
  }

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

  .nav-ball {
    position: sticky;
    width: 35px;
    height: 35px;
    background: var(--primary-color);
    bottom: 8%;
    left: 97%;
    border-radius: 6px;
    z-index: 999;
  }
}

.x-scroll {
  position: relative;
  height: 80vh;



  &::before {
    content: '';
    position: absolute;
    top: 39vh;
    width: 50%;
    height: 2px;
    background: #525151;
    left: 50%;
    transform: translateX(-50%);
  }

  .time-line-cards {
    --card-width: 460px;
    --cards-count: v-bind('articles.length');

    position: relative;
    min-height: 400px;
    margin: 0 auto;

    .card {
      position: absolute;
      transition: transform 0.3s;

      @for $i from 1 through 60 {
        $index: $i - 1;

        &:nth-child(#{$i}) {
          left:#{$index * 22vw};

          @if ($i % 2==0) {
            top: 46vh;
          }

          @media (max-width: 1160px) {
            left: calc(#{$index} * 255px);
          }

        }
      }
    }
  }
}
</style>
