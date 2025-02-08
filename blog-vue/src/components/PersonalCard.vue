<script setup>


</script>

<template>
  <div class="personal-info">
    <div class="bg-img"></div>
    <div class="detail">
      <div class="head-relate">
        <div class="head"></div>
        <div class="relate">
          <a href="https://github.com/frium">
            <img src="@/assets/icons/GitHub.svg" alt="" style="height: 18px;">
          </a>
          <a href="https://blog.csdn.net/guabghaibaoan?spm=1000.2115.3001.5343">
            <img src="@/assets/icons/csdn.svg" alt="" style="height: 18px;">
          </a>
          <a href="https://music.163.com/#/user/home?id=3967869594">
            <img src="@/assets/icons/wangyiyunyinle.svg" alt="" style="height: 18px;">
          </a>
        </div>
      </div>
      <div style="width: 236px;">
        <p class="name">frium's blog</p>
        <p style="font-size: 14px;">前端开发</p>
        <span class="signature">欲买桂花同载酒 终不似 少年游</span>
        <div style="display: flex; justify-content: space-between;padding: 15px 10px 0 10px;">
          <div>
            <p class="title">文章</p>
            <p class="number">2</p>
          </div>
          <div>
            <p class="title">分类</p>
            <p class="number">222</p>
          </div>
          <div>
            <p class="title">访问</p>
            <p class="number">222</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.personal-info {
  position: relative;
  width: 400px;
  height: 240px;
  background: rgba(40, 44, 52, 0.6);
  border-radius: 10px;

  .bg-img {
    width: 100%;
    height: 60%;
    background-image: url('https://blog.frium.top/upload/star.jpg');
    filter: brightness(80%);
    border-radius: 10px;
    background-position: center;
    background-size: cover;
    clip-path: polygon(0% 0%, 100% 0%, 100% 75%, 0% 95%);
  }

  .detail {
    position: absolute;
    display: flex;
    top: 70px;
    width: 100%;
    height: 200px;
    padding: 0 15px;

    .head-relate {
      display: flex;
      flex-direction: column;
      margin-right: 20px;
      width: 140px;
      height: 100%;

      .head {
        background-image: url('https://blog.frium.top/upload/7b914c84-b6ec-4406-8d8b-976eee71e502.png');
        width: 120px;
        height: 120px;
        border-radius: 50%;
        background-position: center;
        background-size: cover;
        border: 4px solid rgba(40, 44, 52, 0.6);
        transition: transform 0.8s ease-out;

        &:hover {
          transform: rotate(720deg)
        }

      }

      .relate {
        display: flex;
        justify-content: space-between;
        padding: 16px 5px 0 5px;


        img {
          transition: transform 0.36s ease-in-out;

          &:hover {
            transform: rotate(720deg) scale(1.2);
          }
        }

      }
    }

    .name {
      font-size: 25px;
      line-height: 30px;
    }

    .signature {
      display: block;
      margin-top: 10px;
      word-wrap: break-word;
      white-space: normal;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      color: #e2ba86;

    }


    .title,
    .number {
      text-align: center;
    }

    .title {
      font-size: 18px;
    }

    .number {
      font-size: 26px;
      color: #f6cac9;

    }
  }

}
</style>
