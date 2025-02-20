<script setup>
import { ref } from 'vue'
import { useMusicStore } from "@/stores/musicStore";

const musicStore = useMusicStore();
const draggableElement = ref(null);
const showBox = ref(false);

const startDrag = (event) => {
  draggableElement.value.addEventListener('mousedown', startDrag);
  showBox.value = true;
  let timeoutId;
  draggableElement.value.addEventListener('mouseleave', function () {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => {
      if (draggableElement.value) {
        draggableElement.value.removeEventListener('mousedown', startDrag);
        showBox.value = false;
      }
    }, 2333);
  });
  draggableElement.value.addEventListener('mouseenter', function () {
    clearTimeout(timeoutId);
  });
  const rect = draggableElement.value.getBoundingClientRect();
  // 计算初始偏移时保留像素值（用于计算相对移动）
  const offsetX = event.clientX - rect.left;
  const offsetY = event.clientY - rect.top;

  const onMouseMove = (moveEvent) => {
    // 计算新的像素位置
    const newX = moveEvent.clientX - offsetX;
    const newY = moveEvent.clientY - offsetY;
    // 转换为视口百分比
    const percentX = (newX / window.innerWidth) * 100;
    const percentY = (newY / window.innerHeight) * 100;
    // 更新元素位置（使用百分比单位）
    draggableElement.value.style.left = `${percentX}%`;
    draggableElement.value.style.top = `${percentY}%`;
  };

  const onMouseUp = () => {
    window.removeEventListener('mousemove', onMouseMove);
    window.removeEventListener('mouseup', onMouseUp);
    const rect = draggableElement.value.getBoundingClientRect();
    const screenHeight = window.innerHeight;
    // 顶部边界检查（转换为百分比）
    if (rect.top < 0) {
      draggableElement.value.style.top = '0%';
    }
    // 底部边界检查（保持元素完整可见）
    if (rect.bottom > screenHeight) {
      const validY = (screenHeight - rect.height) / screenHeight * 100;
      draggableElement.value.style.top = `${validY}%`;
    }
  };
  window.addEventListener('mousemove', onMouseMove);
  window.addEventListener('mouseup', onMouseUp);
};

</script>

<template>

  <div class="lyc-box" v-if="musicStore.showLrc" ref="draggableElement" :class="{ 'lyc-box-avtive': showBox }"
    :style="{ 'pointer-events': showBox ? 'auto' : 'none' }">
    <div class="lrc-wrapper" :style="{ transform: `translateY(-${22.5 * musicStore.lrcIndex}px)` }">
      <span @mousedown="startDrag" :class="{ active: musicStore.lrcIndex == index }"
        v-for="(music, index) in musicStore.playMusicLrc" :key="index">{{
          music.text }}</span>
    </div>
  </div>
</template>

<style scoped lang="scss">
.lyc-box-avtive {
  background: rgba(21, 23, 28, 0.4);
  border-radius: 6px;
  cursor: grab;
}

.lyc-box {
  position: fixed;
  bottom: 2px;
  right: -6vw;
  overflow: hidden;
  width: 666px;
  height: 50px;
  user-select: none;
  padding: 2px 0;
  z-index: 1000;

  .lrc-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    transition: all 0.5s ease-out;

    span {
      position: relative;
      width: fit-content;
      transition: all 0.4s ease-out;
      font-size: 15px;
      cursor: grab;
      z-index: 1000;
      pointer-events: auto;

      &.active {
        color: #ec9897;
        font-size: 16px;
      }
    }
  }

}
</style>
