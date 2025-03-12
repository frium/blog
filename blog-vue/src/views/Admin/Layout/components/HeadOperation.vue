<script setup>
import { computed, onMounted, ref } from "vue";

const props = defineProps({
  title: {
    type: String,
    default: "2313"
  },
  icon: {
    type: String,
    default: ""
  }
});

const iconSrc = computed(() => {
  return props.icon ? new URL(`../../../../assets/icons/${props.icon}`, import.meta.url).href : "";
});
</script>

<template>
  <div class="head-operation">
    <div class="left">
      <img :src="iconSrc" alt="">
      <span>{{ props.title }}</span>
    </div>
    <div class="right">
      <slot></slot>
    </div>
  </div>
</template>

<style scoped lang="scss">
.head-operation {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 55px;
  background: #fff;
  padding: 16px;

  .left {
    display: flex;
    align-items: center;
    gap: 8px;

    img {
      width: 25px;
      height: 25px;
    }

    span {
      font-size: 22px;
      font-weight: 600;
    }
  }
}
</style>
