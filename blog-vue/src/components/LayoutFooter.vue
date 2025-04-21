<script setup>
import { ref } from 'vue';
const day = ref(0);
const hours = ref(0);
const minutes = ref(0);
const seconds = ref(0);

const getTime = () => {
  const timeDifference = new Date() - new Date('2025-02-07T00:00:00');
  day.value = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
  hours.value = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  minutes.value = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
  seconds.value = Math.floor((timeDifference % (1000 * 60)) / 1000);
};
getTime;
setInterval(getTime, 1000);
</script>

<template>
  <div class="container">
    <div class="title">
      <RouterLink :to="{ name: 'Home' }">
        <span>frium's blog</span>
      </RouterLink>
    </div>
    <div class="detail">
      <p>
        © 2025 frium's blog
        <a href="http://beian.miit.gov.cn/publish/query/indexFirst.action">粤ICP备2024234516号</a>
      </p>
      <p> 建站
        <span class="time">{{ day }}</span>
        天
        <span class="time">{{ hours }}</span>
        时
        <span class="time">{{ minutes }}</span>
        分
        <span class="time">{{ seconds }}</span>
        秒
      </p>
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  align-items: center;
  background: var(--bg-color);
  width: 100%;
  height: 75px;
  padding: 10px 100px;
  min-width: 750px;
  gap: 40px;

  @media (max-width: 750px) {
    & {
      display: none;
    }

  }

  .title {
    display: flex;
    align-items: center;
    white-space: nowrap;
    font-size: 20px;
    font-weight: 600;
    color: var(--primary-color);
    letter-spacing: 2px;
  }

  .detail {
    font-size: 12px;
    color: #777;

    .time {
      color: var(--primary-color);
    }
  }
}
</style>
