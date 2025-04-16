<script setup>
const props = defineProps({
  data: Object
})
</script>

<template>
  <div class="recent-article">
    <RouterLink :to="{ name: 'Article', params: { 'articleId': data.id } }">
      <h4>{{ props.data.title }}</h4>
    </RouterLink>
    <div style="margin-top: 3px;">
      <span>{{ '访问量 ' + props.data.viewNum }}</span>
      <span>{{ '评论 ' + props.data.commentNum }}</span>
      <span>{{ '发布日期 ' + props.data.createTime }} </span>
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
