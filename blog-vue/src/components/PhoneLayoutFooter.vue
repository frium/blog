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
  <div class="phone-layout-footer">
    <div class="detail">
      <p>
        © 2025 frium's blog
        <a href="http://beian.miit.gov.cn/publish/query/indexFirst.action">粤ICP备2024234516号</a>
      </p>
      <p class="time-datail"> 建站
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
.phone-layout-footer {
  display: none;
  flex-direction: column;
  align-items: center;
  height: 75px;
  background: var(--bg-color);
  padding: 12px 0 12px 0;

  .detail {
    font-size: 12px;
    color: #777;
    margin-top: 5px;

    .time-datail {
      text-align: center;

      .time {
        color: var(--primary-color);
      }
    }


  }
}

@media (max-width: 500px) {
  .phone-layout-footer {
    display: flex;
  }

}
</style>
