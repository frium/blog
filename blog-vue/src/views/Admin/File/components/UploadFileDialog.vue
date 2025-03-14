<script setup>
import { onMounted, ref } from "vue";
import { ElMessage } from "element-plus";

const fileBlodArr = ref([]);
const fileArr = ref([]);

const isDragEnter = ref(false);
const drop = ref(null);

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
  return true;
}

const openFolder = () => {
  const input = document.createElement('input');
  input.type = 'file';
  input.style.display = 'none';
  input.setAttribute('multiple', 'multiple');
  input.onchange = async (e) => {
    console.log(e.target.files);

    const files = e.target.files;
    if (files.length === 0) return;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      if (!validateFile(file)) continue;
      //加入数组
      fileBlodArr.value.push(file);

    }
    console.log(fileBlodArr.value);


    // const formData = new FormData();
    // formData.append('file', file);
    // const avatarRes = await uploadAvatarAPI(formData);
    // userStore.userInfo.avatar = avatarRes.data.url;
    // await changeUserInfoAPI(userStore.userInfo);
    // ElMessage.success('修改成功!');

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
  return URL.createObjectURL(file);
}
</script>

<template>
  <div class="upload-file-dialog">
    <div v-if="fileBlodArr.length > 0" class="upload-file">
      <div class="head">
        <button>
          取消
        </button>
        <span>2个文件待上传</span>
        <button>
          添加更多文件
        </button>
      </div>
      <div class="selected-file">
        <div v-for="(file, index) in fileBlodArr" :key="index">
          <img v-if="file.type.startsWith('image')" :src="getImagePreview(file)"
            style="width: 190px; height: 120px; object-fit: cover;" />
          <span class="name">
            {{ file.name }}
          </span>
        </div>

      </div>
    </div>
    <div class="upload-file-box" ref="drop">
      <span style="margin: auto;"> 拖拽文件到这里,或者
        <button style="color: brown;" @click="openFolder">浏览</button>
        文件
      </span>
      <div class="hint" v-show="isDragEnter">放到这</div>
    </div>

  </div>
</template>

<style scoped lang="scss">
.upload-file-dialog {
  height: 5000px;
  background-color: #bc4343;

  .upload-file {
    .head {
      button {
        padding: 3px 10px;
      }
    }
  }

  .selected-file {
    display: grid;
    grid-template-columns: repeat(auto-fill, 190px);
    justify-content: space-between;
    row-gap: 15px;

    .name {
      display: block;
      max-width: 185px;
      white-space: nowrap;
      word-break: break-all;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }

  .upload-file-box {
    position: relative;
    display: flex;
    align-items: center;
    width: 100%;
    height: 500px;
    background-color: rgba($color: #f4f4f4, $alpha: 0.5);
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
}
</style>