<script setup>
import { ref, onMounted } from "vue";

const starsky = ref(null);

onMounted(() => {
  const canvas = starsky.value;
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
  const ctx = canvas.getContext("2d");

  // 创建星星数组，包含位置和速度信息
  const stars = Array.from({ length: 200 }, () => {
    // 生成暖色调的随机颜色（黄色到橙色）
    const hue = 30 + Math.random() * 30; // 30-60的色相范围（黄色到橙色）
    const saturation = 40 + Math.random() * 20; // 40-60的饱和度（降低饱和度）
    const lightness = 70 + Math.random() * 20; // 70-90的亮度（提高亮度）

    return {
      x: Math.random() * canvas.width,
      y: Math.random() * canvas.height,
      size: Math.random(),
      speedX: 0.1 + Math.random() * 0.4,
      speedY: -(0.1 + Math.random() * 0.4),
      opacity: 0,
      color: `hsl(${hue}, ${saturation}%, ${lightness}%)`  // 存储星星的颜色
    };
  });

  // 创建流星数组
  const meteors = Array.from({ length: 3 }, () => ({
    x: -100 - Math.random() * 200,
    y: canvas.height + 100 + Math.random() * 200,
    size: 0.5 + Math.random() * 0.5,
    speedX: 4 + Math.random() * 3, // 稍微降低水平速度
    speedY: -2 - Math.random() * 2, // 增加垂直速度
    trail: [],
    active: false
  }));

  // 定时器控制流星出现
  setInterval(() => {
    // 随机选择一个未激活的流星
    const inactiveMeteors = meteors.filter(meteor => !meteor.active);
    if (inactiveMeteors.length > 0) {
      const randomIndex = Math.floor(Math.random() * inactiveMeteors.length);
      const meteor = inactiveMeteors[randomIndex];
      meteor.active = true;
      meteor.x = -100 - Math.random() * 200;
      meteor.y = canvas.height + 100 + Math.random() * 200;
      meteor.trail = [];
    }
  }, 3000);

  function animate() {
    // 画布透明
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // 绘制并移动每颗星星
    stars.forEach(star => {
      // 逐渐增加透明度直到1
      if (star.opacity < 1) {
        star.opacity += 0.002;
      }

      const fadeStart = canvas.width * 0.85;
      let opacity = star.opacity;
      let currentSize = star.size;

      if (star.x > fadeStart) {
        opacity = star.opacity * (0.8 * (1 - ((star.x - fadeStart) / (canvas.width - fadeStart))) + 0.5);
        currentSize = star.size * (0.8 * opacity + 0.2);
      }

      // 使用星星的随机颜色
      ctx.fillStyle = star.color.replace(')', `, ${opacity})`).replace('hsl', 'hsla');
      ctx.beginPath();
      ctx.arc(star.x, star.y, currentSize, 0, 2 * Math.PI);
      ctx.fill();

      // 更新位置（向右上角移动）
      star.x += star.speedX;
      star.y += star.speedY;

      // 如果星星移出画布，从左侧重新进入
      if (star.x > canvas.width) star.x = 0;
      if (star.y < 0) star.y = canvas.height;
    });

    // 绘制并移动流星
    meteors.forEach(meteor => {
      if (!meteor.active) return;

      // 添加当前位置到轨迹
      meteor.trail.push({ x: meteor.x, y: meteor.y });
      // 保持轨迹长度在8个点
      if (meteor.trail.length > 8) {
        meteor.trail.shift();
      }

      // 绘制流星轨迹
      ctx.beginPath();
      ctx.moveTo(meteor.trail[0].x, meteor.trail[0].y);
      for (let i = 1; i < meteor.trail.length; i++) {
        ctx.lineTo(meteor.trail[i].x, meteor.trail[i].y);
      }
      // 使用渐变色绘制轨迹
      const gradient = ctx.createLinearGradient(
        meteor.trail[0].x, meteor.trail[0].y,
        meteor.x, meteor.y
      );
      gradient.addColorStop(0, 'rgba(255, 255, 255, 0)');
      gradient.addColorStop(1, 'rgba(255, 255, 255, 0.8)');
      ctx.strokeStyle = gradient;
      ctx.lineWidth = meteor.size;
      ctx.stroke();

      // 绘制流星头部
      ctx.fillStyle = 'rgba(255, 255, 255, 1)';
      ctx.beginPath();
      ctx.arc(meteor.x, meteor.y, meteor.size, 0, 2 * Math.PI);
      ctx.fill();

      // 更新流星位置
      meteor.x += meteor.speedX;
      meteor.y += meteor.speedY;

      // 如果流星移出画布，重置状态
      if (meteor.x > canvas.width + 100 || meteor.y < -100) {
        meteor.active = false;
      }
    });

    requestAnimationFrame(animate);
  }

  animate();


});
</script>

<template>
  <canvas ref="starsky" class="star-sky"></canvas>
</template>

<style scoped>
.star-sky {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 0;
  background: transparent;
}
</style>