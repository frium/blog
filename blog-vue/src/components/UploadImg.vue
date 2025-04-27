<script setup>
import { getAllFilesAPI } from "@/api/file";
import { ref } from "vue";
const props = defineProps({
  avatarImg: String,
  changeAvatar: Function,
  borderRadius: {
    type: String,
    default: '50%'
  }
})
const selectImgBox = ref(false);
const changeImg = ref(false);
const fileData = ref([]);
const showChangeImg = () => {
  changeImg.value = true;
}

const hiddenChangeImg = () => {
  changeImg.value = false;
}

const handelSelectImgBox = async () => {
  selectImgBox.value = true;
  const res = await getAllFilesAPI();
  fileData.value = res.data;
}

const emit = defineEmits(['select-img']);
const handelSelectImg = (url) => {
  emit('select-img', url);
  selectImgBox.value = false;
}


</script>

<template>
  <div class="upload-img" :style="{ borderRadius: props.borderRadius }">
    <div @mouseenter="showChangeImg" @mouseleave="hiddenChangeImg" style="height: 100%;">
      <img v-if="props.avatarImg" :src="props.avatarImg" style="height: 120px; width: 120px;" />
      <img v-else src="@/assets/icons/add.svg"
        style=" position: absolute; top:50%;left: 50%; transform: translate(-50%,-50%); width: 120px; height: 120px; " />
      <div class="change-img" v-show="changeImg" @click="handelSelectImgBox">
        <img src="@/assets/icons/camera.svg" alt="">
      </div>
      <el-dialog title="选择附件" v-model="selectImgBox" append-to-body width="1000px">
        <button @click="props.changeAvatar" style="margin-bottom: 10px;">上传文件</button>
        <div class="files">
          <div class="file-out-box" v-for="file in fileData" :key="file.id" @click="handelSelectImg(file.url)">
            <img class="file-img" :src="file.url" alt="">
            <p class="file-name">{{ file.fileName }}</p>
          </div>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped lang="scss">
img {
  object-fit: cover;
  object-position: center;
  cursor: url('/src/assets/cursor/Hand.cur'), pointer;
}

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
    cursor: url('/src/assets/cursor/Hand.cur'), pointer;

    object-fit: cover;

    img {
      margin: auto;
      width: 40px;
      height: 40px;
    }
  }
}

.files {
  display: grid;
  grid-template-columns: repeat(auto-fill, 115px);
  justify-content: space-between;
  gap: 10px;

  .file-out-box {
    width: 115px;
    height: 117px;
    border-radius: 4px;
    overflow: hidden;
    border: rgb(228, 229, 231) 1px solid;
    cursor: url('/src/assets/cursor/Hand.cur'), pointer;

    .file-img {
      width: 100%;
      height: 90px;
      object-fit: cover;
      object-position: center;
    }

    .file-name {
      width: 100%;
      padding: 2px 8px;
      text-align: center;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      border-radius: 0 0 4px 4px;
      border-top: 0px;
    }
  }

}
</style>
