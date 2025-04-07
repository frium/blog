<script setup>
const props = defineProps({
  data: Object
});
</script>

<template>
  <div class="time-line-card">
    <RouterLink :to="{ name: 'Article', params: { articleId: props.data.id } }">
      <img :src="props.data.coverImg" alt="">
    </RouterLink>
    <span class="time">{{ props.data.createTime.split(' ')[0] }}</span>
    <span class="title">{{ props.data.title }}</span>

    <span class="describe">{{ props.data.summary }}</span>
  </div>
</template>

<style scoped lang="scss">
.time-line-card {
  position: relative;
  width: 33vw;
  min-width: 350px;
  height: 40vh;
  padding: 0 50px;
  overflow: hidden;

  img {
    width: 100%;
    height: 66%;
    object-fit: cover;
    object-position: 0 25%;
    border-radius: 5px;

  }

  .title {
    position: absolute;
    top: 53%;
    left: 70px;
    font-size: 20px;
    font-weight: bold;
    max-width: 35%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .time {
    position: absolute;
    top: 52%;
    right: 70px;
    font-size: 26px;
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
