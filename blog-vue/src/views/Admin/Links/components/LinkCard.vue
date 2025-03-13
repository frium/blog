<script setup>
const props = defineProps({
  data: Object
})


</script>

<template>
  <div class="link-card">
    <div class="left">
      <img src="https://static.longfish.site/avatar/avatar1.jpg" alt="">
      <div class="detail">
        <span class="title">{{ props.data.title }}</span>
        <a class="link">{{ props.data.link }}</a>
      </div>
    </div>
    <div class="right">
      <span>{{ props.data.title }}</span>
      <el-dropdown trigger="click" class="el-dropdown-link">
        <p style="transform: translate(0,-7px); padding: 3px;">
          ...
        </p>
        <template #dropdown>
          <el-dropdown-menu style="padding: 10px 5px; width: 120px;">
            <el-dropdown-item>编辑</el-dropdown-item>
            <el-dropdown-item><span style="color:red;">删除</span></el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

  </div>
</template>

<style scoped lang="scss">
a:hover {
  color: rgb(0, 0, 0) !important;
}

.link-card {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .left {
    display: flex;
    gap: 20px;
    align-items: center;

    img {
      width: 40px;
      height: 40px;
      object-fit: cover;
      border-radius: 4px;
    }

    .detail {
      display: flex;
      flex-direction: column;

      .title {
        font-size: 16px;
        color: #000;
      }
    }
  }

  .right {
    display: flex;
    align-items: center;

    .el-dropdown-link {
      cursor: pointer;
      font-size: 20px;
      font-weight: 800;
      margin-left: 20px;
    }
  }
}
</style>