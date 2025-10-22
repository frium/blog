<script setup>
const props = defineProps({
  data: Object
})

</script>

<template>
  <div class="article-card">
    <div class="left-div">
      <RouterLink :to="{ name: 'Article', params: { articleId: props.data.id } }" class="title">
        {{ props.data.title }}
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
        <img class="eye" src="@/assets/icons/eye.svg" alt="">
        <span class="views">{{ props.data.viewNum }}</span>
        <img class="chat" src="@/assets/icons/chat.svg" alt="">
        <span class="message">{{ props.data.commentNum || 0 }}</span>
        <span class="time">{{ props.data.createTime }}</span>
      </div>
    </div>
    <div class="right-div">
      <RouterLink :to="{ name: 'Article', params: { articleId: props.data.id } }">
        <img v-lazyLoad="props.data.coverImg" alt="">
      </RouterLink>
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
  background: var(--bg-color);
  border-radius: 10px;
  transition: 0.5s;
  margin-bottom: 24px;

  .right-div {
    img {
      filter: brightness(0.8);
    }
  }

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

    .label {
      margin: 5px 0 10px 0;
      color: #d57c7a;
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

    .footer {
      flex-basis: 20%;
      display: flex;
      align-items: center;
      padding: 0 10px;
      gap: 5px;

      .eye {
        width: 24px;
      }

      .chat {
        margin-top: 2px;
        margin-left: 12px;
        width: 24px;
      }

      .time {
        margin-left: auto;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
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

@media (max-width: 750px) {
  .article-card {
    height: 165px;

    .left-div {
      .title {
        font-size: 20px;
      }

      .label {
        margin: 3px 0 4px 0;
      }

      .describe-out-box {
        -webkit-line-clamp: 2;
      }

      .footer {
        margin-top: 2px;
        font-size: 14px;


      }
    }
  }
}

@media (max-width:510px) {
  .message {
    display: none;
  }

  .chat {
    display: none;
  }
}

@media (max-width:415px) {
  .views {
    display: none;
  }

  .eye {
    display: none;
  }

  .article-card {
    .left-div {
      .footer {
        .time {
          margin-left: 0;

        }
      }
    }
  }


}
</style>
