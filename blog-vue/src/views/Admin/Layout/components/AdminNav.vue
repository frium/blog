<script setup>
import { useRoute } from 'vue-router';

const route = useRoute();
</script>

<template>
  <div class="admin-nav">

    <h2 style="display: flex;justify-content: center; margin-bottom: 20px;">
      <RouterLink to="/"> frium's blog</RouterLink>
    </h2>

    <ul class="tool-bar">
      <li :class="{ active: route.path === '/admin/dashboard' }">
        <img src="@/assets/icons/dashboard.svg" alt="">
        <RouterLink to="/admin/dashboard" style="flex-grow: 1;">仪表盘</RouterLink>
      </li>
      <li :class="{ active: route.path.includes('/admin/editArticle') }">
        <img src="@/assets/icons/article.svg" alt="">
        <RouterLink to="/admin/editArticle" style="flex-grow: 1;">文章</RouterLink>
      </li>
      <li :class="{ active: route.path === '/admin/comment' }">
        <img src="@/assets/icons/comment.svg" alt="">
        <RouterLink to="/admin/comment" style="flex-grow: 1;">评论</RouterLink>
      </li>
      <li :class="{ active: route.path === '/admin/file' }">
        <img src="@/assets/icons/file.svg" alt="">
        <RouterLink to="/admin/file" style="flex-grow: 1;">附件</RouterLink>
      </li>
      <li :class="{ active: route.path === '/admin/links' }">
        <img src="@/assets/icons/links.svg" alt="">
        <RouterLink to="/admin/links" style="flex-grow: 1;">友链</RouterLink>
      </li>
      <li :class="{ active: route.path === '/admin/manageUser' }">
        <img src="@/assets/icons/manageUser.svg" alt="">
        <RouterLink to="/admin/manageUser" style="flex-grow: 1;">用户</RouterLink>
      </li>
    </ul>
    <div class="admin-info">
      用户名
    </div>
  </div>
</template>

<style scoped lang="scss">
a:hover {
  color: rgb(93, 91, 91);
}

.admin-nav {
  width: 250px;
  height: 100vh;
  padding: 20px 16px;
  background: #ffff;
  border: 1px solid rgb(229, 231, 235);
  box-shadow: 0px 3px 5px rgba(0, 0, 0, 0.2);

  .tool-bar {
    display: flex;
    flex-direction: column;

    li {
      position: relative;
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 5px 12px;
      margin: 2px 0;
      border-radius: 5px;

      &:hover {
        background: #F3F4F6;
        color: rgb(93, 91, 91);
      }

      img {
        width: 20px;
        height: 20px;
      }
    }

    .active {
      background: #F3F4F6;

      &::before {
        content: '';
        position: absolute;
        top: 6px;
        left: -9px;
        width: 3px;
        height: 70%;
        background: rgb(87, 87, 87);
        border-radius: 2px;
      }
    }
  }
}
</style>
