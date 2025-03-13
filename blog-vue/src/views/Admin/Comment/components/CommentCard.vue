<script setup>
const props = defineProps({
  data: Object
})
</script>

<template>
  <div class="comment-card">
    <div class="left">
      <img src="https://blog.frium.top/upload/hutao.jpg" alt="">
      <div style="width: 20%;">
        <h4 class="username">{{ props.data.username }}</h4>
        <p class="text">{{ props.data.email }}</p>
      </div>
      <div style="width: 70%;">
        <h4>{{ props.data.articleTitle }}</h4>
        <p class="text">{{ props.data.comment }}</p>
      </div>
    </div>
    <div class="right">
      <span>{{ props.data.createTime }}</span>
      <el-dropdown trigger="click" class="el-dropdown-link">
        <p style="transform: translate(0,-7px); padding: 3px;">
          ...
        </p>
        <template #dropdown>
          <el-dropdown-menu style="padding: 10px 5px; width: 120px;">
            <el-dropdown-item>审核通过</el-dropdown-item>
            <el-dropdown-item><span style="color:red;">删除</span></el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

  </div>
</template>

<style scoped lang="scss">
.comment-card {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 55px;

  .left {
    display: flex;
    align-items: center;
    gap: 20px;
    width: 70%;

    .text {
      font-size: 15px;
      white-space: nowrap;
      word-break: break-all;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    img {
      width: 45px;
      height: 45px;
      border-radius: 50%;
    }
  }

  .right {
    display: flex;
    align-items: center;
    gap: 20px;

    .el-dropdown-link {
      cursor: pointer;
      font-size: 20px;
      font-weight: 800;
    }
  }
}
</style>