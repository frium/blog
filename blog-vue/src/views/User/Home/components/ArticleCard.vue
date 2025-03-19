<script setup>
import { ref } from 'vue';


const text = ref('单看外形似乎只是个古灵精怪的快乐少女，实际上是往生堂第七十七代堂主，掌管堂中事务的少女。身居堂主之位，却没有半分架子。既是“往生堂”堂主，也是璃月“著名”诗人，胡桃的每一重身份都堪称奇妙。她总是飞快地出现又消失，犹如闪电与火花并行，甫一现身便点燃一切')


</script>

<template>
  <div class="article-card">
    <div class="left-div">
      <RouterLink to="/article/1">
        <span class="title">
          胡桃万岁
        </span>
      </RouterLink>
      <div style="flex-basis: 70%;">
        <div class="label">#胡桃 #原神 #好看</div>
        <div style="text-indent: 2em;
           display: -webkit-box;
           -webkit-box-orient: vertical;
           -webkit-line-clamp: 4;
           overflow: hidden;">
          <span class="describe">
            {{ text }}
          </span>
        </div>
      </div>
      <hr style="background-color: rgba(90, 90, 90,0.7);height: 2px; border: none; ">
      <div class="footer">
        <span class="views">❤100</span>
        <span class="message">❤17</span>
        <span class="time"> 2024-10-15 21:49</span>
      </div>
    </div>
    <div class="right-div">
      <img src="http://static.frium.top/blog/hutao.jpg" alt="">
    </div>
  </div>
</template>

<style scoped lang="scss">
span {
  user-select: text;
}

.article-card {
  display: flex;
  width: 100%;
  height: 250px;
  background: rgba(40, 44, 52, 0.6);
  border-radius: 10px;
  transition: 0.5s;
  margin-bottom: 24px;

  &:hover {
    background: rgba(44, 47, 54, 0.7);

    .right-div img {
      transform: scale(1.1);
    }
  }

  .left-div {
    flex-basis: 66%;
    display: flex;
    justify-content: flex-end;
    flex-direction: column;
    height: 100%;
    padding: 10px 17px 5px 17px;

    .title {
      display: block;
      word-wrap: none;
      font-size: 30px;
      flex-basis: 10%;
    }

    .label {
      margin: 5px 0 10px 0;
      color: #d57c7a;
    }

    .describe {
      margin-top: 10px;
      transition: transform 0.8s ease-out;
      background: linear-gradient(to right, #deb1b1 60%, transparent 100%) no-repeat left bottom;
      background-size: 0px 2px;
      transition: 1s;
      width: 480px;

      &:hover {
        background-size: 100% 2px;
      }
    }

    .footer {
      flex-basis: 20%;
      display: flex;
      align-items: center;
      gap: 20px;
      padding: 0 10px;

      .time {
        margin-left: auto;
      }
    }
  }

  .right-div {
    flex-basis: 35%;
    height: 100%;
    width: 100%;
    overflow: hidden;
    border-radius: 10px;

    img {
      height: 100%;
      width: 100%;
      object-fit: cover;
      transition: 0.5s;
    }
  }
}
</style>
