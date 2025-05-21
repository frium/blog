<script setup>
const props = defineProps({
  articles: Object
});
const emit = defineEmits(['to-article'])
const handelToArticle = (articleNum) => {
  emit('to-article', articleNum);
}
</script>

<template>
  <div class="article-nav">
    <div class="articles">
      <h3>文章导航</h3>
      <ul>
        <template v-for="(article, index) in articles" :key="index">
          <li @click="handelToArticle(index)"> {{ article.title }}</li>
        </template>
      </ul>
    </div>
    <!-- <div class="switch">
      <h3>切换模式</h3>
      <RouterLink style="text-indent: 1rem; " :to="{ name: 'XScroll' }">
        <span>横向滑动</span>
      </RouterLink>
      <RouterLink style="text-indent: 1rem; " :to="{ name: 'ZScroll' }">
        <span> ZScroll</span>
      </RouterLink>
    </div> -->
  </div>
</template>

<style scoped lang="scss">
.article-nav {
  padding: 10px;

  &::-webkit-scrollbar {
    display: none;
  }

  max-height: 300px;
  overflow-y: auto;

  .articles {
    display: flex;
    flex-direction: column;

    li {
      overflow: hidden;
      padding: 2px 12px;
      border-radius: 3px;
      cursor: url('/src/assets/cursor/Hand.cur'), pointer;

      &:hover {
        background: #444a55;
        color: var(--primary-color);
      }

    }

    .switch {
      display: flex;
      flex-direction: column;
    }
  }
}
</style>
