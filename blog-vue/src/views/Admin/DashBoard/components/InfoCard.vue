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
  <div class="info-card">
    <img :src="iconSrc" alt="">
    <div class="right">
      <h4>{{ props.data.title }}</h4>
      <h4>{{ props.data.number }}</h4>
    </div>
  </div>
</template>

<style scoped lang="scss">
.info-card {
  display: flex;
  gap: 15px;
  align-items: center;
  padding: 20px;
  background: #fff;
  width: 24%;
  height: 100px;
  border-radius: 4px;

  img {
    background: blanchedalmond;
    width: 45px;
    height: 45px;
    padding: 9px;
    border-radius: 50%;
  }

  .right {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
