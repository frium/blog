<script setup>
import { useUserStore } from '@/stores/userStore';
import UploadImg from './UploadImg.vue';
import notificationToast from '@/utils/notificationToast ';
import { onMounted, reactive, ref } from 'vue';
import { getUserInfoAPI, getVerifyAPI, updateAvatarAPI, updateEmailAPI, updateUsernameAPI } from '@/api/user';
import { uploadFileAPI } from '@/api/file';

const userStore = useUserStore();
const countdownEmail = ref(5);
const isDisabledEmail = ref(false);
const emailRegex = /^[a-zA-Z0-9]+([._%+-][a-zA-Z0-9]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z]{2,}$/;
const emailData = reactive({
  email: '',
  verify: ''
})

const userInfo = reactive({});

const updateUsername = async (event) => {
  await updateUsernameAPI(userInfo.username);
  notificationToast.success('用户名修改成功');
  userStore.userInfo.username = userInfo.username;
  cancle(event);
}

const getEmailCode = () => {
  if (!emailRegex.test(userInfo.email)) {
    notificationToast.error('邮箱格式有误');
    return;
  }
  isDisabledEmail.value = true;
  countdownEmail.value = 5;
  const timer = setInterval(() => {
    countdownEmail.value--;
    if (countdownEmail.value <= 0) {
      clearInterval(timer);
      isDisabledEmail.value = false;
    }
  }, 1000);
  getVerifyAPI(userInfo.email);
}
const updateEmail = async (event) => {
  emailData.email = userInfo.email;
  if (!emailData.verify || !/^\d{6}$/.test(emailData.verify)) {
    notificationToast.error('邮箱验证码应为6位数字');
    return;
  }
  const res = await updateEmailAPI(emailData);
  emailData.data = '';
  if (res.code === 200) notificationToast.success('修改成功');
  else notificationToast.error(res.msg);
  userStore.userInfo.email = userInfo.email;
  cancle(event);

}
const editInfo = (event) => {
  Object.assign(userInfo, userStore.userInfo);
  const li = event.target.parentNode;
  if (!li) return;
  li.querySelector('.content-show-r').style.display = 'none';
  li.querySelector('.info-input').style.display = 'block';
  event.target.style.display = 'none';
};
const cancle = (event) => {
  userInfo.email = "";
  userInfo.username = "";
  emailData.verify = "";
  const li = event.target.closest(".info-input").parentNode;
  if (!li) return;
  li.querySelector('.content-show-r').style.display = 'block';
  li.querySelector('.info-input').style.display = 'none';
  li.querySelector('.edit').style.display = '';
}
onMounted(async () => {
  Object.assign(userInfo, userStore.userInfo);
});
const validateFile = (file) => {
  if (file.size > 10485760) {
    ElMessage.error("图片大小不能超过10MB")
    return false;
  }
  return true;
}

const changeAvatar = () => {
  const input = document.createElement('input');
  input.type = 'file';
  input.accept = 'image/*';
  input.style.display = 'none';

  input.onchange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    if (!validateFile(file)) return;
    const res = await uploadFileAPI(file, null, null);
    userStore.userInfo.avatar = res.data;
    notificationToast.success('修改成功!');
  };

  document.body.appendChild(input);
  input.click();
  document.body.removeChild(input);
}
const selectImg = async (url) => {
  userStore.userInfo.avatar = url;
  await updateAvatarAPI(url);
  notificationToast.success('修改成功!');
}
</script>

<template>
  <div class="personal-info">
    <h2>用户中心</h2>
    <UploadImg class="user-avatar" :avatar-img="userStore.userInfo.avatar" :change-avatar="changeAvatar"
      @select-img="selectImg"></UploadImg>
    <ul class="basic-information-ul" id="infoList">
      <li>
        <div class="content-show-l">用户名</div>
        <div class="content-show-r">{{ userStore.userInfo.username }}</div>
        <div class="info-input">
          <el-input v-model="userInfo.username" style="width: 220px; margin-right: 15px ;" placeholder="请输入内容" />
          <div style="display:flex; justify-content: flex-end; margin-top: 6px;">
            <button @click="updateUsername" style="color: #2c98e5; margin-right: 15px;">确认</button>
            <button @click="cancle" style="color: #d32f2f;">取消</button>
          </div>
        </div>
        <button class="edit" @click="editInfo">编辑</button>
      </li>
      <li>
        <div class="content-show-l">邮箱</div>
        <div class="content-show-r"> {{ userStore.userInfo.email }}</div>
        <div class="info-input">
          <el-input v-model="userInfo.email" style="width: 220px; margin-right: 15px ;" placeholder="请输入内容">
            <template #append>
              <el-button :style="{ fontSize: '14px' }" :disabled="isDisabledEmail" @click="getEmailCode">
                {{ isDisabledEmail ? `${countdownEmail}s 后重试` : '发送' }}
              </el-button>
            </template>
          </el-input>
          <div style="display: flex; margin-top: 10px; gap: 0 15px">
            <el-input v-model="emailData.verify" style="width: 130px; " placeholder="请输入验证码" />
            <button @click="updateEmail" style="color: #2c98e5; ">确认</button>
            <button @click="cancle" style="color: #d32f2f; ">取消</button>
          </div>
        </div>
        <button class="edit" @click="editInfo">编辑</button>
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
.personal-info {
  user-select: none;
  width: 100%;
  height: 100vh;
  background: rgba(59, 62, 69, 0.95);
  color: #fff;
  padding: 10px 10px 10px 20px;

  .user-avatar {
    margin: 40px auto;
  }

  .basic-information-ul {
    padding-left: 16px;

    li {
      min-height: 60.5px;
      display: flex;
      text-align: center;
      margin-bottom: 12px;
      cursor: default;
      font-size: 15px;

      &:hover {
        .edit {
          display: block;
        }
      }

      .content-show-l {
        text-align: center;
        flex-shrink: 0;
        width: 46px;
        height: 100%;
        line-height: 32px;
        text-align-last: justify;
        margin-right: 30px;
      }

      .content-show-r {
        max-width: 720px;
        margin-top: 5px;
        white-space: nowrap;
        word-break: break-all;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .info-input {
        display: none;
        width: 220px;
      }
    }

    .edit {
      height: 33px;
      display: none;
      color: rgb(113, 182, 182);
      margin-left: 20px;
    }
  }
}
</style>
