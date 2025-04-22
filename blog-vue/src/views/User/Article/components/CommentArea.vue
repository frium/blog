<script setup>
import { addCommentAPI, getCommentsAPI } from '@/api/comment';
import { useUserStore } from '@/stores/userStore';
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const userStore = useUserStore();
const commentArr = ref([]);

const comment = ref('');
const addCommentHandel = async () => {
  const data = {};
  data.commentContent = comment.value;
  data.articleId = route.params.articleId;
  await addCommentAPI(data);
  const res = await getCommentsAPI(route.params.articleId);
  commentArr.value = res.data;
  ElMessage.success('评论成功,请等待管理员审核!');
  comment.value = '';
}
onMounted(async () => {
  const res = await getCommentsAPI(route.params.articleId);
  commentArr.value = res.data;
  console.log(commentArr.value);

})
</script>

<template>
  <div class="comment-area">
    <h3>评论</h3>
    <div class="top">
      <img :src="userStore.userInfo.avatar" alt="">
      <div class="add-comment">
        <textarea class="input-area" placeholder="写点评论~" maxlength="300" v-model="comment"></textarea>
        <el-button color="#35363c" style="margin-left: calc(100% - 60px); margin-top: 6px;" @click="addCommentHandel">
          <span style="color: #c4c4c4;">提交</span>
        </el-button>
      </div>
    </div>
    <template v-if="commentArr.length > 0">
      <hr class="middle">
      <div class="bottom">
        <div class="user-comments" v-for="(commentInfo, index) in commentArr" :key="index">
          <img :src="commentInfo.avatar" alt="">
          <div class="comment">
            <p style=" max-height: 4.5em; line-height: 1.5em;   overflow-y: auto; padding: 6px 0;">
              {{ commentInfo.commentContent }}
            </p>
            <hr>
            <p style="font-size: 14px;  color:#a5a5a5 ;">{{ commentInfo.username + ' | ' + commentInfo.createTime }}
            </p>
          </div>
        </div>
      </div>
    </template>
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
  background: var(--bg-color);
  border-radius: 7px;
  width: 100%;

  .top {
    display: flex;
    margin-top: 15px;
    gap: 20px;

    .add-comment {
      width: 100%;

      .input-area {
        width: 100%;
        resize: none;
        background: rgba(64, 65, 70, 0.7);
        padding: 10px;
        min-height: 150px;
        border-radius: 7px;
        color: var(--text-color);
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

@media (max-width: 750px) {
  .comment-area {
    .top .add-comment .input-area {
      min-height: 70px;
    }
  }
}
</style>
