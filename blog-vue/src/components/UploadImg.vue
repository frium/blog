<script setup>
import { ref } from "vue";


const changeImg = ref(false);
const showChangeImg = () => {
  changeImg.value = true;
}

const hiddenChangeImg = () => {
  changeImg.value = false;
}

const changeAvatar = () => {

}
</script>

<template>
  <div class="upload-img">
    <div @mouseenter="showChangeImg" @mouseleave="hiddenChangeImg" style="height: 100%;">
      <img v-if="thumbnailUrl" :src="thumbnailUrl" style="height: 90px; min-width: 90px;" />
      <img v-else src="@/assets/icons/add.svg"
        style=" position: absolute; top: 50%;left:50%; transform: translate(-50%,-50%);" />
      <div class="change-img" v-show="changeImg" @click="uploadCoverImg">
        <img src="@/assets/icons/camera.svg" alt="">
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.upload-img {
  height: 91px;
  min-width: 90px;
  position: relative;
  border: 1px solid #d0cece;

  .change-img {
    position: absolute;
    display: flex;
    align-items: center;
    width: 100%;
    height: 90px;
    top: 0px;
    left: 0px;
    background: #222226;
    opacity: 0.8;
    overflow: hidden;
    cursor: pointer;
    object-fit: cover;

    img {
      margin: auto;
      width: 40px;
      height: 40px;
      cursor: pointer;
    }
  }
}
</style>
