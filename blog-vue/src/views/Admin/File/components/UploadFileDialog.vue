<script setup>
import { onMounted, onUnmounted, ref } from "vue";
import { ElMessage } from "element-plus";

const fileBlodArr = ref([]);
const fileNameSet = ref(new Set());
const previewUrls = ref(new Map());

const isDragEnter = ref(false);
const drop = ref(null);

const dragBox = ref(false);
const showDragBox = () => {
  dragBox.value = true;
};
const dropEvent = (event) => {
  event.preventDefault();

  if (event.type === 'drop') {
    //上传文件

  } else if (event.type === 'dragleave') {
    isDragEnter.value = false;
  } else {
    isDragEnter.value = true;
  }
}

const validateFile = (file) => {
  if (file.size > 52428800) {
    ElMessage.error(file.name + "文件大小不能超过50MB")
    return false;
  }
  if (fileNameSet.value.has(file.name)) {
    ElMessage.error(file.name + " 已经上传过了");
    return false;
  }
  fileNameSet.value.add(file.name);
  return true;
}

const openFolder = () => {
  const input = document.createElement('input');
  input.type = 'file';
  input.style.display = 'none';
  input.setAttribute('multiple', 'multiple');
  input.onchange = async (e) => {
    const files = e.target.files;
    if (files.length === 0) return;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      if (!validateFile(file)) continue;
      fileBlodArr.value.push(file);
    }
    if (dragBox.value) dragBox.value = false;
  };

  document.body.appendChild(input);
  input.click();
  document.body.removeChild(input);

}

onMounted(() => {
  drop.value.addEventListener('drop', dropEvent);
  drop.value.addEventListener("dragenter", dropEvent);
  drop.value.addEventListener("dragover", dropEvent);
  drop.value.addEventListener("dragleave", dropEvent);

})

const getImagePreview = (file) => {
  if (!previewUrls.value.has(file)) {
    const url = URL.createObjectURL(file);
    previewUrls.value.set(file, url);
  }
  return previewUrls.value.get(file);
}

const cleanupUrls = () => {
  previewUrls.value.forEach((url, file) => {
    URL.revokeObjectURL(url);
  });
  previewUrls.value.clear();
};

const cancle = () => {
  fileBlodArr.value = [];
  fileNameSet.value.clear();
}
const offDragBox = () => {
  dragBox.value = false;
}
const resetState = () => {
  fileBlodArr.value = [];
  fileNameSet.value.clear();
  cleanupUrls();
};
defineExpose({ resetState });

onUnmounted(() => {
  if (drop) {
    const handler = dropEvent;
    drop.value.removeEventListener('drop', handler);
    drop.value.removeEventListener("dragenter", handler);
    drop.value.removeEventListener("dragover", handler);
    drop.value.removeEventListener("dragleave", handler);
  }
  cleanupUrls();
})
</script>

<template>
  <div class="upload-file-dialog">
    <div v-if="fileBlodArr.length > 0" class="upload-file">
      <div class="head">
        <button style="color:red;" v-if="fileBlodArr.length > 0 && dragBox" @click="offDragBox">
          返回
        </button>
        <button style="color:red;" v-else @click="cancle">
          取消
        </button>
        <span>{{ fileBlodArr.length + '个文件待上传' }}</span>
        <button style="color:#479dce;" @click="showDragBox">
          添加更多文件
        </button>
      </div>
      <div class="selected-file">
        <div v-for="(file, index) in fileBlodArr" :key="index">
          <img v-if="file.type.startsWith('image')" :src="getImagePreview(file)"
            style="width: 190px; height: 120px; object-fit: cover; box-shadow: 3px 3px 5px 2px rgb(164, 160, 160);" />
          <img v-else src="@/assets/icons/doc.svg" alt=""
            style="width: 190px; height: 120px; box-shadow: 3px 3px 5px 2px rgb(164, 160, 160);">
          <span class="name">
            {{ file.name }}
          </span>
        </div>

      </div>
    </div>
    <div v-show="fileBlodArr.length === 0 || dragBox" class="upload-file-box" ref="drop"
      :style="{ height: fileBlodArr.length === 0 ? '650px' : '592px', top: fileBlodArr.length === 0 ? '0px' : '58px' }">
      <span style="margin: auto; transform: translate(-10px,-30px );"> 拖拽文件到这里,或者
        <button style="color: brown;" @click="openFolder">浏览</button>
        文件
      </span>
      <div class="hint" v-show="isDragEnter">放到这</div>
    </div>
    <div v-if="fileBlodArr.length > 0" class="bottom">
      <el-button type="success">{{ '上传' + fileBlodArr.length + '个文件' }}</el-button>
    </div>

  </div>
</template>

<style scoped lang="scss">
.upload-file-dialog {
  position: relative;
  height: 650px;
  color: black;
  border-radius: 6px;
  border: 1px solid rgb(234, 234, 234);
  overflow: hidden;
  background-color: #fafafa;

  .upload-file {
    .head {
      display: flex;
      justify-content: space-between;
      font-size: 15px;
      border-bottom: 1px solid rgb(234, 234, 234);
      padding-bottom: 10px;
      padding: 15px 10px;


      button {
        padding: 3px 10px;
      }
    }
  }

  .selected-file {
    height: 530px;
    display: grid;
    grid-template-columns: repeat(auto-fill, 190px);
    justify-content: space-between;
    row-gap: 15px;
    padding: 15px 20px;
    background-color: #f4f4f4;
    overflow-y: auto;

    .name {
      margin-top: 5px;
      display: block;
      max-width: 185px;
      white-space: nowrap;
      word-break: break-all;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
      overflow: hidden;
      text-overflow: ellipsis;
      text-align: center
    }
  }

  .upload-file-box {
    position: absolute;
    top: 58px;
    left: 0;
    display: flex;
    align-items: center;
    width: 100%;
    background-color: rgba($color: #dfdfdf, $alpha: 0.8);
    font-size: 22px;
    color: black;

    .hint {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      width: 100px;
      height: 50px;
      background-color: antiquewhite;
    }
  }

  .bottom {
    padding: 5px 20px;
    margin: 10px 0;
  }
}
</style>