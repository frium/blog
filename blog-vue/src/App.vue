<script setup>
import { getBlogInfoAPI } from "./api/blog";
import { useGlobalInfoStore } from "./stores/globalInfo";
import { onMounted, nextTick } from "vue";
import router from "./router";

const globalInfoStore = useGlobalInfoStore();

const setFavicon = (href) => {
  let link = document.querySelector("link[rel~='icon']");
  if (!link) {
    link = document.createElement('link');
    link.rel = 'icon';
    document.head.appendChild(link);
  }
  link.href = href;
};

const dismissLoading = () => {
  const el = document.getElementById("loading-screen");
  if (!el || el.classList.contains("fade-out")) return;
  el.classList.add("fade-out");
  el.addEventListener("transitionend", () => el.remove(), { once: true });
};

const fetchBlogInfo = async () => {
  const res = await getBlogInfoAPI();
  globalInfoStore.globalInfo = res.data;
  setFavicon(globalInfoStore.globalInfo.siteIcon);
};

const preloadImage = (src) =>
  new Promise((resolve) => {
    if (!src) return resolve();
    const img = new Image();
    img.onload = resolve;
    img.onerror = resolve;
    img.src = src;
  });

const waitWindowLoad = () =>
  new Promise((resolve) => {
    if (document.readyState === "complete") return resolve();
    window.addEventListener("load", resolve, { once: true });
  });

onMounted(async () => {
  await fetchBlogInfo();
  await router.isReady();
  await nextTick();
  await waitWindowLoad();
  await nextTick();
  await preloadImage(globalInfoStore.globalInfo.backgroundUrl);
  await document.fonts.ready;
  await nextTick();
  await new Promise((r) => setTimeout(r, 300));
  await nextTick();
  dismissLoading();
});
</script>

<template>
  <router-view></router-view>
</template>

<style></style>
