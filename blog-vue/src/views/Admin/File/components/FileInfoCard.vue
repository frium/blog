<script setup>
const props = defineProps({
  data: Object
})
</script>

<template>
  <div class="file-info-card">
    <div class="left">
      <img :src="props.data.url" alt="">
      <div class="detail">
        <p class="text">{{ props.data.fileName }}</p>
        <span>{{ props.data.fileType }}</span>
        <span>{{ props.data.fileSzie }}</span>
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
.file-info-card {
  display: flex;
  justify-content: space-between;

  .left {
    display: flex;
    align-items: center;
    gap: 20px;
    width: 70%;

    .detail {
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
    }

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
  }
}
</style>