<script setup>
import XScroll from '@/components/XScroll.vue';
import TimeLineCard from './components/TimeLineCard.vue';
</script>

<template>
  <XScroll class="x-scroll">
    <div class="time-line-cards">
      <TimeLineCard v-for="index in 60" :key="index" :class="['card', { even: index % 2 === 0 }]" />
    </div>
  </XScroll>
</template>

<style scoped lang="scss">
.x-scroll {
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: 41vh;
    width: 100%;
    height: 2px;
    background: #525151;
  }

  .time-line-cards {
    --card-width: 460px;
    --cards-count: 60;

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

          @media (max-width: 1160px) {
            left: calc(#{$index} * 255px);
          }

          @if ($i % 2==0) {
            top: 46vh;
          }
        }
      }
    }
  }
}
</style>
