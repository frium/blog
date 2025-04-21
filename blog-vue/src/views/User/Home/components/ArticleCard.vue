<script setup>
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
      <div style="flex-basis: 66%;">
        <div class="label">
          <span v-for="label in props.data.label" :key="label">
            {{ '&nbsp;#' + label.labelName }}
          </span>

        </div>
        <div class="describe-out-box">
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
  background: var(--bg-color);
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
    flex-basis: 65%;
    width: 65%;
    display: flex;
    justify-content: flex-end;
    flex-direction: column;
    height: 100%;
    padding: 10px 17px 5px 17px;

    .title {
      display: block;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      font-size: 26px;
    }

    @media (max-width:500px) {
      .title {
        font-size: 20px;
      }
    }

    .label {
      margin: 5px 0 10px 0;
      color: #d57c7a;
    }

    @media (max-width:500px) {
      .label {
        margin: 3px 0 4px 0;
      }
    }

    .describe-out-box {
      text-indent: 2em;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 4;
      overflow: hidden;

      .describe {
        margin-top: 10px;
        transition: transform 0.8s ease-out;
        background: linear-gradient(to right, #deb1b1 10%, rgba(222, 177, 177, 0.25) 100%) no-repeat left bottom;
        background-size: 0px 2px;
        transition: 1s;

        &:hover {
          background-size: 100% 2px;
        }
      }
    }

    @media (max-width: 500px) {
      .describe-out-box {
        -webkit-line-clamp: 2;
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

    @media (max-width: 500px) {
      .footer {
        margin-top: 2px;
        font-size: 14px;
      }
    }
  }

  .right-div {
    flex-shrink: 0;
    flex-basis: 35%;
    height: 100%;
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

@media (max-width: 500px) {
  .article-card {
    height: 165px;
  }
}
</style>
