<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue';

const createAnimation = (scrollStart, scrollEnd, valueStart, valueEnd) => {
  return function (scroll) {
    if (scroll <= scrollStart) {
      return valueStart;
    }
    if (scroll >= scrollEnd) {
      return valueEnd;
    }
    return valueStart + (valueEnd - valueStart) * (scroll - scrollStart) / (scrollEnd - scrollStart);
  }
}
const animationMap = new Map();
const updateStyles = () => {
  const scroll = window.scrollY;
  for (let [dom, value] of animationMap) {
    for (const cssProp in value) {
      dom.style[cssProp] = value[cssProp](scroll);
    }
  }
}
const getDomAnimation = (scrollStart, scrollEnd, dom) => {
  const itemSpacing = window.innerHeight;
  const maxScrollHeight = document.body.scrollHeight - window.innerHeight;
  scrollStart = dom.dataset.order * itemSpacing;
  scrollEnd = Math.min(scrollStart + itemSpacing * 8, maxScrollHeight);
  const opacityAnimation = createAnimation(scrollStart, scrollEnd, 0, 3);
  const opacity = function (scroll) {
    return opacityAnimation(scroll);
  }
  const zAnimation = createAnimation(scrollStart, scrollEnd, -120, 120);

  const transform = function (scroll) {
    return `translate3d(0px, 0px, ${zAnimation(scroll) * 1.5}vh)`;
  }
  return {
    opacity,
    transform
  }
}
const playgroundRef = ref(null);
const listRef = ref(null);
const updateMap = () => {
  const playGroundRect = playgroundRef.value.getBoundingClientRect();
  const scrollStart = playGroundRect.top + window.scrollY;
  const scrollEnd = window.innerHeight;


  for (const item of listItems.value) {
    animationMap.set(item, getDomAnimation(scrollStart, scrollEnd, item));
  }
}

const listItems = ref([]);
let observer = null
onMounted(() => {
  if (listRef.value) {
    observer = new MutationObserver(() => {
      listItems.value = Array.from(listRef.value?.children || []);
      updateMap();
      window.addEventListener('scroll', updateStyles, {
        passive: true
      });
      playgroundRef.value.style.height = `${(listItems.value.length + 2) * 100}vh`;
      console.log(listItems.value.length + 2);
      observer.disconnect();
    })

    observer.observe(listRef.value, {
      childList: true,
      subtree: true,
      attributes: false
    })
  }
})

onBeforeUnmount(() => {
  if (observer) {
    observer.disconnect()
  }
})

const scrollToArticle = (index) => {
  const position = index * parseInt(window.innerHeight);
  window.scrollTo({
    top: position,
    behavior: 'smooth'
  });
};
defineExpose({
  scrollToArticle
})
</script>

<template>
  <div class="playground" ref="playgroundRef">
    <div class="list" ref="listRef">
      <slot></slot>
    </div>
  </div>
</template>

<style scoped lang="scss">
.playground {
  position: relative;
  background: transparent;
  height: 3300vh;

  .list {
    position: sticky;
    perspective: 100vh;
    top: 0;
    height: 100vh;
    display: flex;
    justify-content: space-around;
    align-items: center;
    overflow: hidden;
  }
}
</style>
