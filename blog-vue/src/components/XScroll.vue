<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'

const containerRef = ref(null);
const fHeight = ref(0);
const fWidth = ref(0);
function updateSize() {
  if (containerRef.value) {
    fHeight.value = containerRef.value.clientHeight;
    fWidth.value = containerRef.value.clientWidth;
  }
};

onMounted(() => {
  updateSize();
  window.addEventListener('resize', updateSize)
});
nextTick(() => {
  updateSize();
})

onUnmounted(() => {
  window.removeEventListener('resize', updateSize)
})
</script>

<template>
  <div class="scroll-container" ref="containerRef">
    <div class="v-scroll">
      <div class="content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.scroll-container {
  width: 100%;
  height: calc(100vh - 80px);
  overflow: hidden;

  .v-scroll {

    width: calc(v-bind('fHeight')*1px);
    height: 90vw;
    position: relative;
    overflow: auto;
    transform-origin: 0 0;
    transform: rotate(-90deg) translateX(-100%);

    &::-webkit-scrollbar {
      display: none;
    }

    .content {
      width: 100%;
      position: absolute;
      left: 100%;
      transform-origin: 0 0;
      transform: rotate(90deg);
    }
  }
}
</style>
