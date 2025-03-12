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
  const offsetX = event.clientX - rect.left;
  const offsetY = event.clientY - rect.top;
  const startX = event.clientX;
  const startY = event.clientY;
  let isDragging = false;
  const onMouseMove = (moveEvent) => {
    if (!isDragging) { //阈值判断
      const deltaX = moveEvent.clientX - startX;
      const deltaY = moveEvent.clientY - startY;
      const distance = Math.sqrt(deltaX ** 2 + deltaY ** 2);
      if (distance <= 5) return;
      isDragging = true;
    }
    // 计算新的像素位置
    const newX = moveEvent.clientX - offsetX;
    const newY = moveEvent.clientY - offsetY;
    // 转换为视口百分比
    const percentX = (newX / window.innerWidth) * 100;
    const percentY = (newY / window.innerHeight) * 100;
    // 更新元素位置
    draggableElement.value.style.left = `${percentX}%`;
    draggableElement.value.style.top = `${percentY}%`;
  };

  const onMouseUp = () => {
    window.removeEventListener('mousemove', onMouseMove);
    window.removeEventListener('mouseup', onMouseUp);
    const rect = draggableElement.value.getBoundingClientRect();
    const screenHeight = window.innerHeight;
    // 顶部边界检查
    if (rect.top < 0) {
      draggableElement.value.style.top = '0%';
    }
    // 底部边界检查
    if (rect.bottom > screenHeight) {
      const validY = (screenHeight - rect.height) / screenHeight * 100;
      draggableElement.value.style.top = `${validY}%`;
    }
  };
  window.addEventListener('mousemove', onMouseMove);
  window.addEventListener('mouseup', onMouseUp);
};
const emit = defineEmits(["toggle-music", "skip-back", "skip-forward", "turn-off-lrc"]);

const isPlaying = ref(false);
const handleMusicToggle = () => {
  emit('toggle-music');
  isPlaying.value = !isPlaying.value;
}
</script>

<template>

  <div class="lyc-box" v-if="musicStore.showLrc" ref="draggableElement" :class="{ 'lyc-box-avtive': showBox }"
    :style="{ 'pointer-events': showBox ? 'auto' : 'none' }">
    <div style="min-height: 20px;
    margin: 9px;">
      <div v-if="showBox" class="action-bar">
        <button>
          <img src="/src/assets/icons/music.svg" alt="">
        </button>
        <button @click="emit('skip-back')">
          <img src="/src/assets/icons/next.svg" alt="" :style="{ transform: ' rotate(180deg)' }">
        </button>
        <button @click="handleMusicToggle">
          <img v-if="!isPlaying" src='/src/assets/icons/play.svg' alt="">
          <img v-else src='/src/assets/icons/pause.svg' alt="">

        </button>
        <button @click="emit('skip-forward')">
          <img src="/src/assets/icons/next.svg" alt="">
        </button>
        <button @click="emit('turn-off-lrc')">
          <img src="/src/assets/icons/off.svg" alt="">
        </button>
      </div>
    </div>
    <div class="lrc-wrapper-outer">
      <div class="lrc-wrapper" :style="{ transform: `translateY(-${22.5 * musicStore.lrcIndex}px)` }">
        <span @mousedown="startDrag" :class="{ active: musicStore.lrcIndex == index }"
          v-for="(music, index) in musicStore.playMusicLrc" :key="index">
          {{ music.text }}
        </span>
      </div>
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
  right: -6vw;
  bottom: 8px;
  overflow: hidden;
  width: 666px;
  height: 90px;
  user-select: none;
  padding: 2px 0;
  z-index: 1000;

  .action-bar {
    display: flex;
    padding: 0 180px;
    justify-content: space-between;

    button {
      transition: transform 0.3s ease;
      padding: 1px 5px;

      &:hover {
        img {
          filter:
            brightness(1.2) saturate(2.5) hue-rotate(-15deg) drop-shadow(0 2px 4px rgba(236, 152, 151, 0.3));
        }
      }
    }

    img {
      width: 18px;
      height: 18px;
      transition: all 0.3s ease;
      filter:
        brightness(0.8) saturate(0.6) grayscale(0.3); // 默认状态滤镜
    }
  }

  .lrc-wrapper-outer {
    height: 50px;
    overflow: hidden;

    .lrc-wrapper {
      display: flex;
      flex-direction: column;
      align-items: center;
      transition: all 0.5s ease-out;
      overflow: hidden;

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
}
</style>
