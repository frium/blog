<script setup>
import XScroll from '@/components/XScroll.vue';
import { onMounted, ref } from 'vue';
import TimeLineCard from '../components/TimeLineCard.vue';
import { getArticleByTimeAPI } from '@/api/article';

const articles = ref([]);
onMounted(async () => {
  const res = await getArticleByTimeAPI();
  articles.value = res.data;
})
</script>

<template>
  <XScroll class="x-scroll">
    <div class="time-line-cards">
      <TimeLineCard v-for="(article, index) in articles" :key="article.id" :class="['card', { even: index % 2 === 0 }]"
        :data="article" />
    </div>
  </XScroll>
</template>

<style scoped lang="scss">
.x-scroll {
  position: relative;
  height: 80vh;

  &::before {
    content: '';
    position: absolute;
    top: 39vh;
    width: 100%;
    height: 2px;
    background: #525151;
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
