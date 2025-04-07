<script setup>
import { Search } from '@element-plus/icons-vue'
import { onMounted, ref } from 'vue'
import Login from './Login.vue';
import { useUserStore } from '@/stores/userStore';
import PersoanalInfo from './PersoanalInfo.vue';
import { getUserInfoAPI, logoutAPI } from '@/api/user';
import notificationToast from '@/utils/notificationToast ';
const userStore = useUserStore();
const showLogin = ref(false);
const handleShowLogin = () => {
  showLogin.value = true;
}

const handleClose = () => {
  showLogin.value = false;
};
const showPersonalInfo = ref(false);
const handelShowPersonalInfo = () => {
  showPersonalInfo.value = true;
}
onMounted(async () => {
  if (userStore.jwt) {
    const res = await getUserInfoAPI();
    Object.assign(userStore.userInfo, res.data);
  }
})

const handelLogout = async () => {
  await logoutAPI();
  userStore.userInfo.avatar = "";
  userStore.userInfo.email = "";
  userStore.userInfo.username = "";
  userStore.jwt = "";
  notificationToast.success('登出成功!');
}
</script>

<template>
  <div class="container">
    <RouterLink :to="{ name: 'Home' }">
      <span class="title">frium's blog</span>
    </RouterLink>
    <div class="menu">
      <div>
        <ul class="sub-menu">
          <li class="hover-show">
            <RouterLink to="/home" active-class="router-link-active">首页</RouterLink>
          </li>
          <li class="hover-show">
            <RouterLink to="/categories" active-class="router-link-active">分类</RouterLink>
          </li>
          <li class="hover-show">
            <RouterLink to="/time" active-class="router-link-active">归档</RouterLink>
          </li>
          <li class="hover-show">
            <RouterLink to="/friends" active-class="router-link-active">友人帐</RouterLink>
          </li>
          <li class="hover-show">
            <RouterLink to="/other" active-class="router-link-active"> 其他</RouterLink>
          </li>

          <li class="hover-show">
            <a href="">网盘</a>
          </li>
        </ul>
      </div>
      <el-button :icon="Search" round color="#f6cac9"
        style="color: white; width: 50px; font-size: 18px;margin-left: auto; margin-right: 25px;" />
      <el-dropdown v-if=(userStore.userInfo.avatar)>
        <img class="user-head" :src="userStore.userInfo.avatar" alt="">
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="handelShowPersonalInfo">个人中心</el-dropdown-item>
            <el-dropdown-item @click="handelLogout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <button v-else class="login" @click="handleShowLogin">登录</button>
      <el-dialog style="width:1000px; padding : 0px; margin-top: 7%; background-color: transparent;  user-select: none;"
        v-model="showLogin" @before-close="handleClose" :append-to-body="true" :show-close="false">
        <div style="margin-top: -30px;">
          <Login :handleClose="handleClose"></Login>
        </div>
      </el-dialog>
    </div>
    <el-dialog
      style="width:23.5vw; padding : 0px; margin: 0px 0px 0px 76.5vw;   background-color: transparent;   height: 100vh;"
      v-model="showPersonalInfo" @before-close="handleClose" :append-to-body="true">
      <div style="margin-top: -16px;">
        <PersoanalInfo></PersoanalInfo>
      </div>
    </el-dialog>

  </div>
</template>

<style scoped lang="scss">
.container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  padding: 10px 5vw 10px 6vw;
  display: flex;
  justify-content: center;
  background: rgba(40, 44, 52, 0.6);
  min-width: 900px;
  align-items: center;
  text-align: center;
  z-index: 1;

  .title {
    white-space: nowrap;
    font-size: 21px;
    font-weight: 600;
    color: #f6cac9;
    letter-spacing: 2px;
    margin-right: 10px;
  }

  .menu {
    display: flex;
    justify-content: space-between;
    width: 100%;
    align-items: center;

    .sub-menu {
      display: flex;

      li {
        position: relative;
        margin-left: 20px;
        padding: 0 4px;

        &::after {
          position: absolute;
          content: "";
          background-color: rgba(165, 127, 127, 0.4);
          left: 50%;
          width: 0;
          bottom: -12px;
          height: 3px;
          border-radius: 4px 4px 0 0;
          transform: translateX(-50%);
          transition: all 0.6s ease;
        }

        &:hover::after {
          width: 100%;
        }

        .router-link-active::after {
          position: absolute;
          content: "";
          background-color: #f6cac9;
          left: 0;
          right: 0;
          z-index: 1;
          bottom: -12px;
          height: 3px;
          border-radius: 4px 4px 0 0;
        }
      }
    }

    .login {
      width: 36px;
      height: 36px;
      color: #c4c4c4;
      transition: all 0.6s ease;

      &:hover {
        color: #f6cac9;
      }
    }

    .user-head {
      height: 36px;
      width: 36px;
      transition: transform 0.5s;
      border-radius: 50%;

      &:hover {
        transform: rotate(560deg)
      }
    }
  }



}
</style>
