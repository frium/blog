<script setup>
import { ref } from "vue";
const props = defineProps({
  avatarImg: String,
  changeAvatar: Function
})
const changeImg = ref(false);
const showChangeImg = () => {
  changeImg.value = true;
}

const hiddenChangeImg = () => {
  changeImg.value = false;
}



</script>

<template>
  <div class="upload-img">
    <div @mouseenter="showChangeImg" @mouseleave="hiddenChangeImg" style="height: 100%;">
      <img v-if="props.avatarImg" :src="props.avatarImg" style="height: 120px; width: 120px;" />
      <img v-else src="@/assets/icons/add.svg"
        style=" position: absolute; top: 50%;left:50%; transform: translate(-50%,-50%); width: 120px; height: 120px;" />
      <div class="change-img" v-show="changeImg" @click="props.changeAvatar">
        <img src="@/assets/icons/camera.svg" alt="">
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.upload-img {
  height: 120px;
  width: 120px;
  position: relative;
  border-radius: 50%;
  overflow: hidden;
  user-select: none;

  .change-img {
    position: absolute;
    display: flex;
    align-items: center;
    width: 120px;
    height: 120px;
    top: 0px;
    left: 0px;
    background: #000000;
    opacity: 0.9;
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
