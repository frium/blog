<script setup>

</script>

<template>
  <div class="not-found">
    <p class="text"> 404</p>
    <p class="msg">
      <span>找不到网页，可能已被删除，去</span>
      <RouterLink :to="{ name: 'Home' }" style="color: #f8c0bf;">首页</RouterLink>
      <span>看看吧!</span>
    </p>

  </div>
</template>

<style scoped lang="scss">
.not-found {
  background: var(--bg-color);
  height: 600px;
  border-radius: 7px;

  .text {
    font-size: 77px;
    font-style: italic;
    margin: auto;
    text-align: center;
  }

  .msg {
    font-size: 20px;
    text-align: center;
  }
}
</style>
