<script setup>
const props = defineProps({
  data: Object
})
</script>

<template>
  <div class="user-info-card">
    <div class="left">
      <img src="https://blog.frium.top/upload/7b914c84-b6ec-4406-8d8b-976eee71e502.png" alt="">
      <span>{{ props.data.username }}</span>
    </div>
    <div class="right">
      <div class="identity">
        <span v-if="props.data.auth === 1">超级管理员</span>
        <span v-else-if="props.data.auth === 2">管理员</span>
        <span v-else>访客</span>
      </div>
      <span>{{ props.data.createTime }}</span>
      <el-dropdown trigger="click" class="el-dropdown-link">
        <p style="transform: translate(0,-7px); padding: 3px;">
          ...
        </p>
        <template #dropdown>
          <el-dropdown-menu style="padding: 10px 5px; width: 120px;">
            <el-dropdown-item>修改资料</el-dropdown-item>
            <el-dropdown-item><span style="color:red;">删除</span></el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<style scoped lang="scss">
.user-info-card {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .left {
    display: flex;
    align-items: center;
    gap: 10px;

    img {
      width: 40px;
      height: 40px;
      border-radius: 4px;
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

    .identity {
      color: black;
      padding: 1px 4px;
      border: 1px solid rgb(184, 184, 184);
      border-radius: 4px;
    }
  }
}
</style>