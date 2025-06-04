<script setup>
import { getLabelNumAPI, getShowArticleNumAPI } from "@/api/article";
import { getUVPVAPI } from "@/api/uvpv";
import { useGlobalInfoStore } from "@/stores/globalInfo";
import { onMounted, ref } from "vue";


const articleNum = ref(0);
const labelNum = ref(0);
const viewNum = ref(0);
const globalInfoStore = useGlobalInfoStore();

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

</script>

<template>
  <div class="personal-info">
    <div class="bg-img"></div>
    <img :src="globalInfoStore.globalInfo.avatarUrl" class="head" />
    <div class="detail">
      <p class="name">{{ globalInfoStore.globalInfo.siteName }}</p>
      <span class="signature">{{ globalInfoStore.globalInfo.personalSignature }}</span>
      <div style="display: flex; justify-content: space-between;padding: 15px 10px 10px 10px; width: 240px;">
        <div>
          <p class="title">文章</p>
          <p class="number">{{ articleNum }}</p>
        </div>
        <div>
          <p class="title">分类</p>
          <p class="number">{{ labelNum }}</p>
        </div>
        <div>
          <p class="title">访问</p>
          <p class="number">{{ viewNum }}</p>
        </div>
      </div>
      <a class="about-me" :href="globalInfoStore.globalInfo.aboutMe">About Me</a>
      <div class="relate">
        <a :href="globalInfoStore.globalInfo.githubUrl">
          <img src="@/assets/icons/GitHub.svg" alt="" style="height: 18px;">
        </a>
        <a :href="globalInfoStore.globalInfo.csdnUrl">
          <img src="@/assets/icons/csdn.svg" alt="" style="height: 18px;">
        </a>
        <a :href="globalInfoStore.globalInfo.wyyUrl">
          <img src="@/assets/icons/wangyiyunyinle.svg" alt="" style="height: 18px;">
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.personal-info {
  position: relative;
  width: 280px;
  height: 380px;
  border-radius: 10px;
  overflow: hidden;

  .bg-img {
    width: 100%;
    height: 100%;
    background: url('https://static.frium.top/blog/start.jpg');
    opacity: 0.7;
    border-radius: 10px;
    background-position: center;
    background-size: cover;
  }


  .head {
    position: absolute;
    top: 20px;
    left: 90px;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    background-position: center;
    background-size: cover;
    border: 4px solid rgba(184, 181, 181, 0.5);
    transition: transform 0.8s ease-out;

    &:hover {
      transform: rotate(720deg)
    }

  }

  .detail {
    position: absolute;
    top: 130px;
    left: 20px;
    width: 240px;
    display: flex;
    flex-direction: column;
    align-items: center;

    .name {
      font-size: 30px;
      line-height: 30px;
    }

    .about-me {
      width: 100%;
      background: #f8c0bf;
      padding: 4.25px;
      color: white;
      border-radius: 20px;
      text-align: center;

      &:hover {
        color: white;
      }
    }

    .signature {
      display: block;
      margin-top: 10px;
      word-wrap: break-word;
      white-space: normal;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      color: #e2ba86;

    }



    .title,
    .number {
      text-align: center;
    }

    .title {
      font-size: 18px;
    }

    .number {
      font-size: 25px;
      color: var(--primary-color);

    }

    .relate {
      display: flex;
      justify-content: space-between;
      padding: 16px 5px 0 5px;
      width: 220px;
      margin-top: 10px;

      img {
        transition: transform 0.36s ease-in-out;

        &:hover {
          transform: rotate(720deg) scale(1.2);
        }
      }

    }
  }


  .detail2 {
    position: absolute;
    display: flex;
    top: 70px;
    width: 100%;
    height: 200px;
    padding: 0 15px;



    .head-relate {
      display: flex;
      flex-direction: column;
      margin-right: 20px;
      width: 140px;
      height: 100%;


    }





  }

}
</style>
