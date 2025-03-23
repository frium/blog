<script setup>
import { ref } from "vue";
const props = defineProps({
  avatarImg: String,
  changeAvatar: Function,
  borderRadius: {
    type: String,
    default: '50%'
  }
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
  <div class="upload-img" :style="{ borderRadius: props.borderRadius }">
    <div @mouseenter="showChangeImg" @mouseleave="hiddenChangeImg" style="height: 100%;">
      <img v-if="props.avatarImg" :src="props.avatarImg" style="height: 120px; width: 120px;" />
      <img v-else src="@/assets/icons/add.svg"
        style=" position: absolute; top:50%;left: 50%; transform: translate(-50%,-50%); width: 120px; height: 120px; " />
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
  overflow: hidden;
  user-select: none;
  border: 1px solid rgb(216, 218, 225);

  .change-img {
    position: absolute;
    display: flex;
    align-items: center;
    width: 120px;
    height: 120px;
    top: 0px;
    left: 0px;
    background: #656363;
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
