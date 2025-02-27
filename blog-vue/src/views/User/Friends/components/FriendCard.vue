<script setup>

</script>

<template>
  <div class="friend-card">
    <a href="/" class="a-box">
      <div>
        <span class="name">longfish's blog</span>
        <span class="detail">常常的鱼常常的鱼常常的鱼常常的鱼常常的鱼常常的鱼常常的鱼常常的鱼常常的鱼常常的鱼常的鱼常常的鱼常常的鱼常的鱼常常的鱼常常的鱼常常的鱼常常的鱼</span>
      </div>
      <img src="https://static.longfish.site/avatar/avatar1.jpg" alt="">

    </a>
  </div>
</template>

<style scoped lang="scss">
.friend-card {
  position: relative;
  width: 310px;
  height: 100px;
  padding: 10px 30px;
  background: rgba(40, 44, 52, 0.8);
  border: 1px solid #414243;
  border-radius: 10px;
  transition: all ease 0.5s;
  overflow: hidden;

  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: -15%;
    background: #ffc8c8;
    width: 0%;
    height: 100%;
    transform: skew(45deg, 0);
    transition: all ease 0.5s;
    opacity: 0.2;
    z-index: 1;
    pointer-events: none;
  }

  &:hover {
    border: 1px solid #ffc8c8;

    &::before {
      width: 130%;
    }

    img {
      transform: rotate(360deg);
    }
  }

  a:hover {
    color: #ccc;
  }

  .a-box {
    display: flex;
    justify-content: space-between;

    .name,
    .detail {
      -webkit-box-orient: vertical;
      overflow: hidden;
      display: -webkit-box;

    }

    .name {
      color: #ffc8c8;
      padding-bottom: 6px;
      font-size: 16px;
      -webkit-line-clamp: 1;
      border-bottom: 1px dashed #8e8585;

    }

    .detail {
      text-indent: 1em;
      margin-top: 5px;
      font-size: 14px;
      -webkit-line-clamp: 2;

    }

    img {
      margin-top: 5px;
      margin-left: 3px;
      height: 65px;
      width: 65px;
      border: 3px solid #646060;
      border-radius: 50%;
      transition: all ease 0.6s;

    }
  }

}
</style>
