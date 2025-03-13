<script setup>
import { ref } from "vue"


const visible = ref(true);
const toUser = () => {

}

const editArticle = () => {

}

const deleteArticle = () => {

}

const changeVisible = () => {
  visible.value = !visible.value;
}
</script>

<template>
  <div class="edit-article-card">
    <div class="edit-left">
      <span class="title">几微米</span>
      <span>分类: #mysql</span>
      <div class="bottom">
        <span>浏览量: 100</span>
        <span>评论: 100</span>
      </div>
    </div>
    <div class="edit-right">
      <img @click="toUser" style="width: 35px; height: 35px; border-radius: 50%; "
        src="https://blog.frium.top/upload/7b914c84-b6ec-4406-8d8b-976eee71e502.png" alt="">
      <img v-if="visible" @click="changeVisible" style="width: 24px; height: 24px;" src="@/assets/icons/visible.svg"
        alt="">
      <img v-else @click="changeVisible" style="width: 24px; height: 24px;" src="@/assets/icons/invisible.svg" alt="">
      <span> 2024-10-15 21:49 </span>
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
.edit-article-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 2px 0;
  font-size: 15px;

  .edit-left {
    display: flex;
    flex-direction: column;

    .title {
      margin-bottom: 2px;
      font-size: 17px;
      color: black;
    }

    .bottom {
      display: flex;
      gap: 10px;
      margin-top: 2px;
    }
  }

  .edit-right {
    display: flex;
    align-items: center;
    gap: 30px;

    .el-dropdown-link {
      cursor: pointer;
      font-size: 20px;
      font-weight: 800;
    }

    img {
      cursor: pointer;
    }
  }
}
</style>
