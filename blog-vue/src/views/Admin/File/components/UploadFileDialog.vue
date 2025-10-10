<script setup>
import { onMounted, onUnmounted, ref, watch, watchEffect } from "vue";
import { ElMessage } from "element-plus";
import { uploadFileAPI } from "@/api/file";
import ProgressCircle from "./ProgressCircle.vue";
import { SuccessFilled, CircleCloseFilled } from "@element-plus/icons-vue";
import imageCompression from 'browser-image-compression';
const fileBlodArr = ref([]);
const fileNameSet = ref(new Set());
const previewUrls = ref(new Map());
const uploading = ref(false);
const promiseNum = ref(-1);

const isDragEnter = ref(false);
const drop = ref(null);

const dragBox = ref(false);
const showDragBox = () => {
  if (fileNameSet.value.size === 0) fileBlodArr.value = [];
  dragBox.value = true;
};
const dropEvent = (event) => {
  event.preventDefault();
  if (event.type === 'drop') {
    dragBox.value = false;
    const files = event.dataTransfer.files;
    if (files.length === 0) return;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      if (!validateFile(file)) continue;
      const fileInfo = {
        file: file,
        uploadProgress: 0,
      }
      if (!fileBlodArr.value[i]) {
        fileBlodArr.value[i] = fileInfo;
      } else {
        fileBlodArr.value.push(fileInfo);
      }
      promiseNum.value = Math.max(promiseNum.value, 0) + 1;
      const controller = new AbortController();
      abortControllers.value[i] = controller;
      queue.push({ file, index: i, signal: controller.signal });
    }
    isDragEnter.value = false;
    return;
  } else if (event.type === 'dragleave') {
    isDragEnter.value = false;
    dragBox.value = false;
    return;
  } else if (event.type === 'dragenter') {
    dragBox.value = true;
  }
  isDragEnter.value = true;

}

const validateFile = (file) => {
  if (file.size > 52428800) {
    ElMessage.error(file.name + "文件大小已超过50MB")
    return false;
  }
  if (fileNameSet.value.has(file.name)) {
    ElMessage.error(file.name + " 已经添加过了");
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
      const fileInfo = {
        file: file,
        uploadProgress: 0,
      }
      if (!fileBlodArr.value[i]) {
        fileBlodArr.value[i] = fileInfo;
      } else {
        fileBlodArr.value.push(fileInfo);
      }
      promiseNum.value = Math.max(promiseNum.value, 0) + 1;
      const controller = new AbortController();
      abortControllers.value[i] = controller;
      queue.push({ file, index: i, signal: controller.signal });
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
  previewUrls.value.forEach((url) => {
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
  cancle();
  cleanupUrls();
};
defineExpose({ resetState });

onUnmounted(() => {
  if (drop) {
    const handler = dropEvent;
    drop.value?.removeEventListener('drop', handler);
    drop.value?.removeEventListener("dragenter", handler);
    drop.value?.removeEventListener("dragover", handler);
    drop.value?.removeEventListener("dragleave", handler);
  }
  cleanupUrls();
});
const onUploadProgress = (index) => (e) => {
  fileBlodArr.value[index].uploadProgress = Math.round(e.progress * 100);
}
const resolve = (index) => {
  promiseNum.value = promiseNum.value - 1;
  fileNameSet.value.delete(fileBlodArr.value[index].file.name);
  ElMessage.success(fileBlodArr.value[index].file.name + '上传成功');
  fileBlodArr.value[index].uploadProgress = 100;
}

let queue = [];
let concurrentUploads = ref(0);
const maxConcurrentUploads = 4;
const abortControllers = ref([]);

const processQueue = async () => {
  while (concurrentUploads.value < maxConcurrentUploads && queue.length > 0) {
    const { file, index, signal } = queue.shift();
    const isImage = file.type.startsWith('image/');
    let fileToUpload = file;
    if (isImage) {
      const options = {
        maxSizeMB: 0.05,
        maxWidthOrHeight: 800,
        useWebWorker: true,
        fileType: 'image/jpeg',
      };
      const compressedFile = await imageCompression(file, options);
      fileToUpload = new File([compressedFile], file.name, {
        type: compressedFile.type,
        lastModified: Date.now(),
      });
    }
    if (fileToUpload.size > 60000) {
      ElMessage.warning(`${file.name} 压缩后大小仍超过50kb,请重新选择该文件`);
      return;
    }
    if (!fileBlodArr.value[index]) continue;
    fileBlodArr.value[index].uploadProgress = 0;
    concurrentUploads.value++;
    uploadFileAPI(fileToUpload, onUploadProgress(index), signal)
      .then(() => {
        resolve(index);
      })
      .catch((error) => {
        if (error.message === 'canceled') {
          ElMessage.warning(`文件 ${file.name} 撤销上传`);
        } else {
          ElMessage.error(`文件 ${file.name} 上传失败`);

        }
      })
      .finally(() => {
        concurrentUploads.value--;
      });
  }
}

const handlerUploadFile = () => {
  uploading.value = true;
  processQueue();
};

watch(concurrentUploads, (newVal, oldVal) => {
  processQueue();

});

watch(promiseNum, (newVal, oldVal) => {
  if (newVal <= 0) {
    uploading.value = false;
  }
});

const cancleUpload = (index) => {
  if (abortControllers.value[index]) {
    abortControllers.value[index].abort();
    abortControllers.value[index] = null;
  }
  promiseNum.value = promiseNum.value - 1;
  fileNameSet.value.delete(fileBlodArr.value[index].file.name);
  fileBlodArr.value[index] = null;
}
</script>

<template>
  <div class="upload-file-dialog">
    <div v-if="fileBlodArr.length > 0" class="upload-file">
      <div class="head">
        <button style="color:red;" v-if="fileNameSet.size > 0 && dragBox" @click="offDragBox">
          返回
        </button>
        <button style="color:red;" v-else @click="cancle">
          取消
        </button>
        <span v-if="fileNameSet.size !== 0">{{ fileNameSet.size + '个文件待上传' }}</span>
        <button :disabled="uploading" style="color:#479dce;" @click="showDragBox">
          添加文件
        </button>
      </div>
      <div class="selected-file">
        <template v-for="(fileInfo, index) in fileBlodArr" :key="index">
          <div v-if="fileInfo" class="img-out-box">
            <img v-if="fileInfo.file.type.startsWith('image')" :src="getImagePreview(fileInfo.file)"
              style="object-fit: cover;" />
            <img v-else src="@/assets/icons/doc.svg" alt="">

            <el-icon class="upload-status" v-if="fileBlodArr[index].uploadProgress === 100" color="#54af4f" size="18px">
              <SuccessFilled />
            </el-icon>
            <el-icon style="cursor: pointer;" class="upload-status" v-else @click="cancleUpload(index)" size="18px">
              <CircleCloseFilled />
            </el-icon>
            <div v-if="uploading && fileBlodArr[index].uploadProgress < 100" class="progress">
              <ProgressCircle class="cancle-upload" :percent="fileBlodArr[index].uploadProgress" size="60px"
                border-width="5px" color="#fff" inactive-color="rgba(200,200,200,0.3)">
              </ProgressCircle>
            </div>
            <span class="name">
              {{ fileInfo.file.name }}
            </span>
          </div>
        </template>
      </div>
    </div>
    <div class="upload-file-box" ref="drop" :style="{
      height: fileBlodArr.length === 0 ? '650px' : '532px', top: fileBlodArr.length === 0 ? '0px' : '58px',
      opacity: fileBlodArr.length === 0 || dragBox ? 1 : 0,
      zIndex: fileBlodArr.length === 0 || dragBox ? 9 : -9,
    }">
      <span v-show="!isDragEnter" style="margin: auto; transform: translate(-10px,-30px );"> 拖拽文件到这里,或者
        <button style="color: brown;" @click="openFolder">浏览</button>
        文件
      </span>
      <div class="hint" v-show="isDragEnter">
        <img src="@/assets/icons/uploadFile.svg" alt="">
        <p>拖拽文件到这里</p>
      </div>
    </div>
    <div v-if="fileNameSet.size > 0 && !uploading" class="bottom">
      <el-button type="success" @click="handlerUploadFile">{{ '上传' + fileNameSet.size + '个文件' }}</el-button>
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
    grid-auto-flow: dense;
    grid-template-columns: repeat(auto-fill, 190px);
    grid-template-rows: repeat(auto-fill, 150px);
    row-gap: 15px;
    justify-content: space-between;
    padding: 15px 20px;
    background-color: #f4f4f4;
    overflow-y: auto;
    pointer-events: auto;

    .img-out-box {
      position: relative;
      border-radius: 10px;

      .upload-status {
        position: absolute;
        width: 18px;
        height: 18px;
        top: -9px;
        right: -9px;
        box-shadow: 0px 0px;
        background: #f4f4f4;
        border-radius: 50%;
        z-index: 2;

      }

      img {
        border-radius: 3px;
        width: 190px;
        height: 120px;
        box-shadow: 3px 3px 5px 2px rgb(164, 160, 160);
        transform: translateZ(0);
      }

      .progress {
        position: absolute;
        top: 0;
        left: 0;
        width: 190px;
        height: 120px;
        background: rgba(0, 0, 0, 0.6);
        border-radius: 3px;

        .cancle-upload {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
        }
      }

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

  }

  .upload-file-box {
    position: absolute;
    z-index: 9;
    top: 58px;
    left: 0;
    display: flex;
    align-items: center;
    width: 100%;
    background-color: rgba($color: rgb(231, 231, 231), $alpha: 0.8);
    font-size: 22px;
    color: black;

    .hint {
      position: absolute;
      display: flex;
      align-items: center;
      flex-direction: column;
      top: 42%;
      left: 50%;
      transform: translate(-50%, -50%);
      height: 130px;
      pointer-events: none;

      img {
        width: 130px;
      }
    }
  }

  .bottom {
    padding: 5px 20px;
    margin: 10px 0;
  }
}
</style>
