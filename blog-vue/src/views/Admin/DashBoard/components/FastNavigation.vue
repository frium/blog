<script setup>
const props = defineProps({
  url: {
    type: String,
    default: ''
  },
  icon: {
    type: String,
    default: '',
  }
})
</script>

<template>
  <div class="quick-find">
    <div class="head">
      <div style=" display: flex; align-items: center;">
        <img class="head-img" src="@/assets/icons/article.svg" alt="">
      </div>
      <img class="arrow" style="" src="@/assets/icons/toRightArrow.svg" alt="">
    </div>
    <h4 style="margin-top: 20px;">个人中心</h4>
  </div>
</template>

<style scoped lang="scss">
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
