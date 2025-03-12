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
  },
  buttonArr: {
    type: Array,
    default: []
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
      <button v-for="(button, index) in buttonArr" :key="button.name"
        :class="{ active: index === buttonArr.length - 1 }" @click="button.onClick">
        {{ button.name }}
      </button>
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

  .right {
    display: flex;
    align-items: center;
    gap: 10px;

    button {
      padding: 3px 18px;
      border: 1px solid rgb(217, 219, 224);
      border-radius: 6px;
      transition: all 0.3s;

      &:hover {
        background: rgb(217, 219, 224);
      }
    }

    .active {
      background-color: black;
      color: #fff;

      &:hover {
        background: rgb(64, 64, 66);
      }
    }
  }
}
</style>
