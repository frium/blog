<script setup>
import { onMounted } from "vue"


const props = defineProps({
  data: Object
})

</script>

<template>
  <div class="article-card">
    <div class="left-div">
      <RouterLink :to="{ name: 'Article', params: { articleId: props.data.id } }">
        <span class="title">
          {{ props.data.title }}
        </span>
      </RouterLink>
      <div style="flex-basis: 70%;">
        <div class="label">
          <span v-for="label in props.data.label" :key="label">
            {{ '&nbsp;#' + label.labelName }}
          </span>

        </div>
        <div style="text-indent: 2em;
           display: -webkit-box;
           -webkit-box-orient: vertical;
           -webkit-line-clamp: 4;
           overflow: hidden;">
          <span class="describe">
            {{ props.data.summary }}
          </span>
        </div>
      </div>
      <hr style="background-color: rgba(90, 90, 90,0.7);height: 2px; border: none; ">
      <div class="footer">
        <span class="views">{{ props.data.viewNum }}</span>
        <span class="message">{{ props.data.commentNum }}</span>
        <span class="time">{{ props.data.createTime }}</span>
      </div>
    </div>
    <div class="right-div">
      <RouterLink :to="{ name: 'Article', params: { articleId: props.data.id } }">
        <img :src="props.data.coverImg" alt="">

      </RouterLink>
    </div>
  </div>
</template>

<style scoped lang="scss">
img {
  filter: brightness(0.8);
}

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
      font-size: 26px;
      flex-basis: 10%;
    }

    .label {
      margin: 5px 0 10px 0;
      color: #d57c7a;
    }

    .describe {
      margin-top: 10px;
      transition: transform 0.8s ease-out;
      background: linear-gradient(to right, #deb1b1 10%, rgba(222, 177, 177, 0.25) 100%) no-repeat left bottom;
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
