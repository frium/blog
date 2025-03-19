<script setup>
const props = defineProps({
  // id:{
  //   type: String,
  // },
  // title: {
  //   type: String,
  // },
  // img: {
  //   type: String,
  // },
  // describe: {
  //   type: String,
  // },
  // time: {
  //   type: String,
  // }
});
</script>

<template>
  <div class="time-line-card">
    <RouterLink to="/time">
      <img src="http://static.frium.top/blog/head.png" alt="">
    </RouterLink>
    <div class="time">2024-10-15</div>
    <span class="describe">如果你停止,就是谷底 如果你还在继续果你还在继续如果你停止还在继续如果你停止还在继续如果你停止还在继续如果你停止还在继续如果你停止,就是谷底 如果你还在继续,就是上坡</span>
  </div>
</template>

<style scoped lang="scss">
.time-line-card {
  position: relative;
  width: 30vw;
  min-width: 350px;
  height: 46vh;
  padding: 0 50px;
  overflow: hidden;

  img {
    width: 100%;
    height: 65%;
    object-fit: cover;
    object-position: 0 25%;
    border-radius: 5px;

  }

  .time {
    position: absolute;
    top: 52%;
    left: 70px;
    font-size: 28px;
    font-weight: bold;
  }

  .describe {
    display: inline-block;
    margin-top: 8px;
    text-indent: 2em;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;

    @media (min-width: 1600px) {
      & {
        -webkit-line-clamp: 3;
      }
    }
  }
}
</style>
