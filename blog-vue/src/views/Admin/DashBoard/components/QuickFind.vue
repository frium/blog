<script setup>
import { computed } from "vue";

const props = defineProps({
  data: Object
});

const iconSrc = computed(() => {
  return props.data.icon ? new URL(`../../../../assets/icons/${props.data.icon}`, import.meta.url).href : "";
});
</script>

<template>
  <div class="quick-find">
    <RouterLink :to="{ name: props.data.url }">
      <div class="head">
        <div style=" display: flex; align-items: center;">
          <img class="head-img" :src="iconSrc" alt="">
        </div>
        <img class="arrow" style="" src="@/assets/icons/toRightArrow.svg" alt="">
      </div>
      <h4 style="margin-top: 20px;">{{ props.data.title }}</h4>
    </RouterLink>
  </div>
</template>

<style scoped lang="scss">
a:hover {
  color: rgb(93, 91, 91);
}

.quick-find {
  background: #f4f4f4;
  width: 190px;
  height: 130px;
  padding: 20px 15px;
  border-radius: 8px;
  transition: all 0.2s;

  &:hover {
    background: #edebeb;

    .head .arrow {
      opacity: 0.8;
      transform: translate3d(-5px, 0, 0px) scale(1.1);
    }
  }

  .head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    transform-style: preserve-3d;

    .head-img {
      width: 45px;
      height: 45px;
      background-color: rgb(248, 238, 255);
      padding: 10px;
      border-radius: 8px;
      margin: auto;
    }

    .arrow {
      opacity: 0.5;
      width: 15px;
      height: 15px;
      transition: all 0.2s;
      transform: translate3d(-10px, 0, 0px)
    }
  }
}
</style>
