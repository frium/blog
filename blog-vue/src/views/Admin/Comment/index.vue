<script setup>
import CommentCard from './components/CommentCard.vue';
import SearchTable from '../Layout/components/SearchTable.vue';
import HeadOperation from '../Layout/components/HeadOperation.vue';

const tableData = [
  {
    username: "张三",
    email: "zhangsan@example.com",
    articleTitle: "如何使用 Vue3",
    comment: "这篇文章讲解得很清楚，学到了很多！",
    createTime: "2025-03-13 12:30:45"
  },
  {
    username: "李四",
    email: "lisi@example.com",
    articleTitle: "前端性能优化",
    comment: "有没有更详细的示例代码？",
    createTime: "2025-03-13 14:10:22"
  },
  {
    username: "王五",
    email: "wangwu@example.com",
    articleTitle: "深入理解 JavaScript 作用域",
    comment: "作用域链的部分可以再展开讲讲吗？",
    createTime: "2025-03-13 15:05:10"
  }
];

const deleteFunction = () => {

}

const searchFunction = () => {

}
</script>

<template>
  <HeadOperation :title="'评论'" :icon="'comment.svg'"></HeadOperation>
  <div class="admin-container">
    <div class="comment">
      <SearchTable :delete-function="deleteFunction" :search-function="searchFunction" :table-data="tableData">
        <template v-slot:default="{ row }">
          <CommentCard :data="row" />
        </template>
      </SearchTable>
    </div>
  </div>
</template>

<style scoped lang="scss">
.comment {
  padding: 15px 20px;
}
</style>
