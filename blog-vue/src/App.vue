<script setup>
import { onMounted } from "vue";
import { getBlogInfoAPI } from "./api/blog";
import { useGlobalInfoStore } from "./stores/globalInfo";

const globalInfoStore = useGlobalInfoStore();
const setFavicon = (href) => {
  let link = document.querySelector("link[rel~='icon']")
  if (!link) {
    link = document.createElement('link')
    link.rel = 'icon'
    document.head.appendChild(link)
  }
  link.href = href
}

const setTitle = (title) => {
  document.title = title + '\'s blog';

}
onMounted(async () => {
  const res = await getBlogInfoAPI();
  globalInfoStore.globalInfo = res.data;
  setFavicon(globalInfoStore.globalInfo.siteIcon);
  setTitle(globalInfoStore.globalInfo.siteName);
})
</script>

<template>
  <router-view></router-view>
</template>

<style scoped lang="scss"></style>
