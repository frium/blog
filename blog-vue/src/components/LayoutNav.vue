<script setup>
import { Search } from '@element-plus/icons-vue'
</script>

<template>
  <div class="container">
    <div>
      <span class="title">frium's blog</span>
    </div>
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
      <el-button :icon="Search" round color="#f6cac9" style="color: white; width: 50px; font-size: 18px;" />
    </div>
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
  }
}
</style>
