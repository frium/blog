<script setup>
import FriendCard from './components/FriendCard.vue';
</script>

<template>
  <div class="friend-out-box">
    <img src="https://blog.frium.top/upload/网易云背景.jpg" alt="">
    <div class="content">
      <h3>frium的小伙伴们</h3>
      <div class="friend-cards">
        <FriendCard v-for="index in 5" :key="index"></FriendCard>
      </div>
      <hr>
      <div class="friend-link">
        申请友链的方法：
        <ul>
          <li>名称: frium's blog</li>
          <li>地址: <a href="https://blog.frium.top">https://blog.frium.top</a></li>
          <li>图标: <a href="/upload/flowerboy.jpg">https://blog.frium.top/upload/flowerboy.jpg</a></li>
          <li>描述: 摸鱼，摸鱼，摸摸你的咸鱼</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.friend-out-box {
  width: 100%;
  border-radius: 10px;
  overflow: hidden;
  background: rgba(40, 44, 52, 0.6);

  img {
    width: 100%;
    height: 30vh;
    object-fit: cover;
    filter: brightness(0.75);
  }

  .content {
    padding: 15px 15px 25px 15px;

    h3 {
      position: relative;
      margin-left: 10px;
      transition: transform ease 0.3s;
      padding: 5px 0;

      &:hover {
        transform: translateX(10px);
      }

      &:hover::before {
        transform: translateX(-10px);
      }

      &::before {
        content: "";
        position: absolute;
        top: 25%;
        left: -12px;
        width: 4px;
        height: 20px;
        background: #f7c4c4;
        border-radius: 5px;
        transition: transform ease 0.3s;
      }

    }

    .friend-cards {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); // 自动适应列数
      justify-content: center; // 整体居中
      gap: 15px 30px; // 行间距 15px，列间距 30px
      width: 100%;
      margin-top: 15px;
    }

    hr {
      margin: 30px 0 20px 0;
      border: none;
      border-top: 1px solid #414243;
    }

    .friend-link {
      font-size: 16px;
      color: #999;

      ul {
        li {
          position: relative;
          line-height: 16px;
          margin-top: 10px;
          margin-left: 27px;
          letter-spacing: 1px;

          &:hover {
            &::before {
              transform: rotate(540deg);
              border: 1px solid #e87c52;
            }
          }

          &::before {
            content: "";
            position: absolute;
            display: block;
            top: calc(50% - 5px);
            left: -19px;
            width: 9px;
            height: 9px;
            border: 1px solid #f6cac9;
            transform: rotate(45deg);
            transition: 0.7s ease-out;
          }

          a {
            position: absolute;
            color: #f6cac9;
            text-indent: 0.5rem;

            &:hover {
              &::before {
                width: 100%;
              }
            }

            &::before {
              content: "";
              position: absolute;
              width: 0;
              height: 2%;
              background: #f6cac9;
              top: 100%;
              transition: 0.5s;
            }
          }
        }
      }
    }
  }
}
</style>
