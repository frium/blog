<script setup>
import { ref } from 'vue';
const props = defineProps({
  isTop: {
    type: Boolean,
    default: true
  }
})
const labels = ref(['算法', '算法', '算法'])
</script>

<template>
  <div class="top-aticle-card">
    <RouterLink to="/">
      <div class="bg-img"></div>
      <div class="detail">
        <h2><span class="top" v-if="isTop">置顶</span>
          <span class="title"> 你说的对对你说的</span>
        </h2>
        <p class="time">2024-10-15 21:49 </p>
      </div>
      <div class="labels">
        <RouterLink to="/" class="label" v-for="label in labels" :key="label"> {{ label }}</RouterLink>
      </div>
    </RouterLink>
  </div>
</template>

<style scoped lang="scss">
span {
  user-select: text;
}

.top-aticle-card {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 270px;
  margin-bottom: 24px;
  border-radius: 10px;

  .bg-img {
    width: 100%;
    height: 100%;
    background-image: url('https://static.frium.top/blog/sea of flowers.jpg');
    background-size: cover;
    background-position: center;
    border-radius: 10px;
    transition: 0.5s;
    filter: brightness(0.8);
  }

  &:hover {
    .bg-img {
      transform: scale(1.05);
    }
  }

  .detail {
    position: absolute;
    top: 186px;
    left: 20px;

    h2 {
      margin-bottom: 10px;

      .top {
        display: inline-block;
        background-image: -webkit-linear-gradient(0deg, rgba(57, 169, 255, .8) 0, rgba(155, 79, 255, .8) 100%);
        border-radius: 2px 6px;
        font-size: 16px;
        padding: 1px 6px;
        color: #fff;
        line-height: 20px;
      }

      .title {
        font-size: 22px;
        color: #ede7e7;
      }

      &::after {
        content: '';
        position: absolute;
        top: 35px;
        left: 0;
        width: 42px;
        height: 3.6px;
        background: #f6cac9;
        border-radius: 2px;
        transition: width 0.3s;
      }

      &:hover {
        &::after {
          width: 102%;
        }
      }
    }
  }

  .labels {
    display: flex;
    position: absolute;
    top: 4px;
    right: 10px;
    justify-content: right;
    gap: 5px;

    .label {
      display: inline-block;
      font-size: 12px;
      padding: 4px 10px;
      background: rgba(45, 45, 45, 0.7);
      border-radius: 4px;
    }
  }
}
</style>
