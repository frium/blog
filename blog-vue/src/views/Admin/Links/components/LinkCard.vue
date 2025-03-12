<script setup>

</script>

<template>
  <div class="link-card">
    <div class="left">
      <img src="https://static.longfish.site/avatar/avatar1.jpg" alt="">
      <div class="detail">
        <span class="title">longfish's blog</span>
        <a class="link">https://blog.longfish.site</a>
      </div>
    </div>
    <div class="right">
      <span> 2024-06-26 21:27</span>
      <el-dropdown trigger="click" class="el-dropdown-link">
        <p style="transform: translate(0,-7px); padding: 3px;">
          ...
        </p>
        <template #dropdown>
          <el-dropdown-menu style="padding: 10px 5px; width: 120px;">
            <el-dropdown-item>编辑</el-dropdown-item>
            <el-dropdown-item>设置</el-dropdown-item>
            <el-dropdown-item><span style="color:red;">删除</span></el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

  </div>
</template>

<style scoped lang="scss">
.link-card {
  display: flex;
  justify-content: space-between;

  .left {

    .detail {
      display: flex;
      flex-direction: column;
    }
  }
}
</style>