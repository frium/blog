<script setup>
import { computed, onMounted } from "vue";

const props = defineProps({
  data: Object
})
computed(() => {
  props.data.label.length = 3;
})

</script>

<template>
  <div class="top-aticle-card">
    <RouterLink :to="{ name: 'Article', params: { articleId: props.data.id } }">
      <img class="bg-img" :src="props.data.coverImg" alt="">
    </RouterLink>
    <div class="detail">
      <span class="top" v-if="props.data.isTop">置顶</span>
      <span class="title"> {{ props.data.title }}</span>
    </div>
    <span class="time">{{ props.data.createTime }} </span>
    <div class="labels">
      <RouterLink to="/" class="label" v-for="label in props.data.label" :key="label"> {{ label.labelName }}
      </RouterLink>
    </div>
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
    object-fit: cover;
    object-position: center;
    border-radius: 10px;
    transition: 0.5s;
    filter: brightness(0.75);
  }

  &:hover {
    .bg-img {
      transform: scale(1.03);
    }
  }

  .detail {
    position: absolute;
    top: 186px;
    left: 20px;



    .top {
      display: inline-block;
      background-image: -webkit-linear-gradient(0deg, rgba(57, 169, 255, .8) 0, rgba(155, 79, 255, .8) 100%);
      border-radius: 2px 6px;
      font-size: 16px;
      padding: 1px 6px;
      color: #fff;
      line-height: 20px;
      margin-right: 10px;
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
        width: 100%;
      }
    }
  }

  .time {
    position: absolute;
    top: 230px;
    left: 20px;
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
