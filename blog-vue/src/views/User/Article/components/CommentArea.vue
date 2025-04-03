<script setup>
import { useUserStore } from '@/stores/userStore';


const userStore = useUserStore();
</script>

<template>
  <div class="comment-area">
    <h3>评论</h3>
    <div class="top">
      <img :src="userStore.userInfo.avatar" alt="">
      <div class="add-comment">

        <textarea placeholder="写点评论~" maxlength="300"></textarea>
        <el-button color="pink" style="margin-left: calc(100% - 60px); margin-top: 6px;">提交</el-button>
      </div>
    </div>
    <hr class="middle">
    <div class="bottom">
      <div class="user-comments">
        <img :src="userStore.userInfo.avatar" alt="">
        <div class="comment">
          <p style=" max-height: 4.5em; line-height: 1.5em;   overflow-y: auto;  ">
            我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌
          </p>
          <hr>
          <p style="font-size: 14px;  color:#a5a5a5 ;">我是你的超级大跌 | 2024-10-15 21:49 </p>
        </div>
      </div>
      <div class="user-comments">
        <img :src="userStore.userInfo.avatar" alt="">
        <div class="comment">
          <p style=" max-height: 4.5em; line-height: 1.5em;   overflow-y: auto;  ">
            我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌
          </p>
          <hr>
          <p style="font-size: 14px;  color:#a5a5a5 ;">我是你的超级大跌 | 2024-10-15 21:49 </p>
        </div>
      </div>
      <div class="user-comments">
        <img :src="userStore.userInfo.avatar" alt="">
        <div class="comment">
          <p style=" max-height: 4.5em; line-height: 1.5em;   overflow-y: auto;  ">
            我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌我是你的超级大跌
          </p>
          <hr>
          <p style="font-size: 14px;  color:#a5a5a5 ;">我是你的超级大跌 | 2024-10-15 21:49 </p>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped lang="scss">
img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  flex-shrink: 0;
}

.comment-area {
  user-select: text;
  padding: 8px 20px;
  margin-top: 20px;
  background: rgba(40, 44, 52, 0.6);
  border-radius: 7px;
  width: 100%;

  .top {
    display: flex;
    margin-top: 15px;
    gap: 20px;


    .add-comment {
      width: 100%;

      textarea {
        width: 100%;
        resize: none;
        background: rgba(64, 65, 70, 0.7);
        padding: 10px;
        min-height: 150px;
        border-radius: 7px;
        color: #c4c4c4;
      }
    }

  }

  .middle {
    margin: 15px 0 20px 0;
    border: none;
    border-top: 1px solid #414243;
  }

  hr {
    margin: 6px 0;
    border: none;
    border-top: 1px solid #414243;
  }

  .bottom {
    .user-comments {
      display: flex;
      gap: 20px;
      margin-bottom: 20px;

      .comment {
        position: relative;

        background: rgba(64, 65, 70, 0.7);
        width: 100%;
        border-radius: 7px;
        padding: 8px 12px;

        &::before {
          content: '';
          position: absolute;
          top: 12px;
          left: -14px;
          border-left: 6px solid transparent;
          border-top: 7px solid transparent;
          border-right: 8px solid rgba(64, 65, 70, 0.7);
          border-bottom: 7px solid transparent;
          border-left: 50%;
          border-right: 50%;
        }
      }

    }


  }
}
</style>
