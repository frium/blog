<script setup>
const props = defineProps({
  icon: {
    type: String,
    default: '',
  }
})
</script>

<template>
  <div class="info-card">
    <img src="@/assets/icons/article.svg" alt="">
    <div class="right">
      <h4>文章</h4>
      <h4>22</h4>
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
