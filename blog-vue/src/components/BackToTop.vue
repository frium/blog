<script setup>
import { useScrollStore } from '@/stores/scrollStore';
import { onMounted, ref, onUnmounted } from 'vue';

const backToTop = ref(null);
const isVisible = ref(false);
let scrollTimeout = null;

const scrollStore = useScrollStore();
//节流器
const throttle = (fn, delay) => {
  let timer = null;
  return () => {
    if (timer) return;
    timer = setTimeout(() => {
      fn();
      timer = null;
    }, delay);
  }
}
const scrollToTop = () => {
  document.body.scrollTo({
    top: 0,
    left: 0,
    behavior: "smooth"
  });
};
const checkScroll = () => {
  const height = document.body.scrollTop;
  const scrollHeight = document.body.scrollHeight;
  const innerHeight = window.innerHeight;
  isVisible.value = height > scrollHeight * 0.05;
  scrollStore.scrollProportion = +(height / (scrollHeight - innerHeight) * 100).toFixed(0);
  document.body.classList.add('scrolling');
  clearTimeout(scrollTimeout);
  scrollTimeout = setTimeout(() => {
    document.body.classList.remove('scrolling');
  }, 500);
}
const throttledCheckScroll = throttle(checkScroll, 200);
onMounted(() => {
  document.body.addEventListener('scroll', throttledCheckScroll);
  checkScroll();
})
onUnmounted(() => {
  document.body.removeEventListener('scroll', throttledCheckScroll);
});
</script>

<template>
  <div class="back-to-top" :style="{ opacity: isVisible ? 1 : 0 }" ref="backToTop" @click="scrollToTop">
    <button>
      <img src="@/assets/icons/rocket.svg">
    </button>
  </div>

</template>

<style scoped lang="scss">
.back-to-top {
  width: 40px;
  height: 40px;
  padding: 7px;
  background: rgb(77, 74, 74);
  border-radius: 50%;
  transition: ease-in 0.3s;
}
</style>
