<script setup>
import { getLabelNumAPI, getShowArticleNumAPI } from "@/api/article";
import { getUVPVAPI } from "@/api/uvpv";
import { useGlobalInfoStore } from "@/stores/globalInfo";
import { onMounted, ref } from "vue";


const articleNum = ref(0);
const labelNum = ref(0);
const viewNum = ref(0);

onMounted(async () => {
  const [articleNumRes, labelNumRes, viewNumRes] = await Promise.all([
    getShowArticleNumAPI(),
    getLabelNumAPI(),
    getUVPVAPI()
  ])
  articleNum.value = articleNumRes.data;
  labelNum.value = labelNumRes.data;
  viewNum.value = viewNumRes.data.uv;
})

const emit = defineEmits('offPersoalCard')
const handelOffPersonalCard = () => {
  emit('off-persoal-card');
}
</script>

<template>
  <div class="phone-personal-card">
    <img class="avatar" src="https://static.frium.top/blog/flowerboy.jpg" alt="">
    <p>frium</p>
    <p>愿你历经千帆,归来仍是少年</p>
    <div class="details">
      <p class="detail">累计撰写 <span class="num">{{ articleNum }}</span> 篇文章</p>
      <p class="detail">累计创建 <span class="num">{{ labelNum }}</span> 个标签</p>
      <p class="detail">累计收获 <span class="num">{{ viewNum }}</span> 次访问</p>
    </div>
    <div class="nav">
      <ul class="sub-menu">
        <li>
          <RouterLink to="/home" active-class="router-link-active" @click="handelOffPersonalCard">首页</RouterLink>
        </li>
        <li>
          <RouterLink to="/categories" active-class="router-link-active" @click="handelOffPersonalCard">分类</RouterLink>
        </li>
        <li>
          <RouterLink to="/friends" active-class="router-link-active" @click="handelOffPersonalCard">友人帐</RouterLink>
        </li>
        <li>
          <RouterLink to="/other" active-class="router-link-active" @click="handelOffPersonalCard"> 其他</RouterLink>
        </li>
        <li>
          <a href="https://alist.frium.top">网盘</a>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped lang="scss">
.phone-personal-card {
  padding: 40px 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  width: 80vw;
  height: 100vh;
  background: #303030;
  color: #999;
  font-size: 15px;

  .avatar {
    width: 60px;
    height: 60px;
    border-radius: 10%;
  }

  .details,
  .nav {
    width: 100%;
    padding: 10px 15px;
    background: #232323;
    border-radius: 8px;
  }

  .details {
    .detail {
      margin-bottom: 5px;
      letter-spacing: 1px;

      &:last-child {
        margin-bottom: 0px;
      }

      .num {
        color: var(--primary-color);
      }
    }
  }

  .nav {
    .sub-menu {
      li {
        padding: 10px 0;

        .router-link-active {
          color: var(--primary-color);
        }
      }
    }
  }
}
</style>
