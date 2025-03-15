<script setup>
import { computed, ref, onMounted, watch } from 'vue';

const props = defineProps({
  percent: {
    type: Number,
    default: 0,
    validator: (value) => value >= 0 && value <= 100
  },
  size: {
    type: String,
    default: '60px'
  },
  borderWidth: {
    type: String,
    default: '5px'
  },
  color: {
    type: String,
    default: '#7856d7'
  },
  inactiveColor: {
    type: String,
    default: '#ccc'
  }
});

const sizeNumber = computed(() => parseInt(props.size, 10));
const currentPercent = ref(0); // 动态变化的百分比

// 动画函数
const animateProgress = (start, end, duration) => {
  const startTime = performance.now();

  const step = () => {
    const currentTime = performance.now();
    const elapsed = currentTime - startTime;
    const progress = Math.min(elapsed / duration, 3);
    currentPercent.value = start + (end - start) * progress;

    if (progress < 1) {
      requestAnimationFrame(step);
    } else {
      currentPercent.value = end;
    }
  };

  requestAnimationFrame(step);
};

watch(() => props.percent, (newPercent) => {
  animateProgress(currentPercent.value, Math.max(newPercent, currentPercent.value), 1000); // 动画持续 1 秒
});

onMounted(() => {
  animateProgress(0, props.percent, 1000); // 页面加载时从 0 动画到初始百分比
});
</script>

<template>
  <div class="progress-circle" :style="{
    '--percent': currentPercent,
    '--size': props.size,
    '--border-width': props.borderWidth,
    '--color': props.color,
    '--inactive-color': props.inactiveColor
  }">
    <div class="progress-text">{{ Math.round(currentPercent) }}%</div>
    <svg :viewBox="`0 0 ${sizeNumber} ${sizeNumber}`">
      <circle :cx="sizeNumber / 2" :cy="sizeNumber / 2" />
      <circle :cx="sizeNumber / 2" :cy="sizeNumber / 2" class="progress-value" />
    </svg>
  </div>
</template>

<style scoped>
.progress-circle {
  --percent: 0;
  --size: 180px;
  --border-width: 15px;
  --color: #7856d7;
  --inactive-color: #ccc;

  position: relative;
  width: var(--size);
  height: var(--size);
}

.progress-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 16px;
  z-index: 1;
  color: #fff;
}

.progress-circle>svg {
  width: 100%;
  height: 100%;
  transform: rotate(-90deg);
}

.progress-circle circle {
  --r: calc((var(--size) - var(--border-width)) / 2);
  --circumference: calc(2 * 3.1415926536 * var(--r));

  r: var(--r);
  fill: none;
  stroke-width: var(--border-width);
  stroke-linecap: round;
  transition: stroke-dasharray 1s ease-out, stroke 1s;
}

.progress-circle circle:first-child {
  stroke: var(--inactive-color);
}

.progress-value {
  stroke: var(--color);
  stroke-dasharray: calc(var(--circumference) * var(--percent) / 100), 1000;
  opacity: calc(var(--percent) * 0.01);
}
</style>
