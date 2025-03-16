<script setup>
//获取文章接口
</script>

<template>
  <div class="recent-article">
    <RouterLink to="/">
      <h4>jvm基础</h4>
    </RouterLink>
    <div style="margin-top: 3px;">
      <span>访问量 12</span>
      <span>评论 2132</span>
      <span>发布日期 2024-09-23 21:53 </span>
    </div>
  </div>
</template>

<style scoped lang="scss">
.recent-article {
  padding: 12px 0 7px 0;
  border-bottom: 1px solid #e2e6ed;

  h4 {
    display: inline-block;
  }

  span {
    margin: 0 4px;
  }

  a:hover {
    color: rgb(93, 91, 91);
  }
}
</style>
