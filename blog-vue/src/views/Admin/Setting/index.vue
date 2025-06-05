<script setup>
import { useGlobalInfoStore } from '@/stores/globalInfo';
import HeadOperation from '../Layout/components/HeadOperation.vue';
import UploadImg from '@/components/UploadImg.vue';
import InputCard from './components/InputCard.vue';
import { updateBlogInfoAPI } from '@/api/blog';
import { uploadFileAPI } from '@/api/file';
import { ElMessage } from 'element-plus';
const globalInfoStore = useGlobalInfoStore();


const createUpdateFunction = (propertyName) => {
  return async (info) => {
    globalInfoStore.globalInfo[propertyName] = info;
    await updateBlogInfoAPI(globalInfoStore.globalInfo);
  };
};

const updateSiteName = createUpdateFunction('siteName');
const updateSiteIcon = createUpdateFunction('siteIcon');
const updatePersonalSignature = createUpdateFunction('personalSignature');
const updateBlogStartTime = createUpdateFunction('blogStartTime');
const updateBackgroundUrl = createUpdateFunction('backgroundUrl');
const updateGithubUrl = createUpdateFunction('githubUrl');
const updateCsdnUrl = createUpdateFunction('csdnUrl');
const updateWyyUrl = createUpdateFunction('wyyUrl');

const updateAvatar = async (info) => {
  globalInfoStore.globalInfo.avatarUrl = info;
  await updateBlogInfoAPI(globalInfoStore.globalInfo);
  ElMessage.success('修改成功!');
}
const changeAvatar = () => {
  const input = document.createElement('input');
  input.type = 'file';
  input.accept = 'image/*';
  input.style.display = 'none';

  input.onchange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    if (file.size > 10485760) {
      ElMessage.error("图片大小不能超过10MB")
      return;
    }
    const res = await uploadFileAPI(file, null, null);
    await updateAvatar(res.data);
  };

  document.body.appendChild(input);
  input.click();
  document.body.removeChild(input);
}
</script>

<template>
  <HeadOperation :title="'设置'" :icon="'setting.svg'"></HeadOperation>
  <div class="admin-container">
    <div class="setting">
      <ul class="info-setting">
        <li style="margin-bottom: 10px;">
          <UploadImg :avatarImg="globalInfoStore.globalInfo.avatarUrl" @select-img="updateAvatar"
            :changeAvatar="changeAvatar"></UploadImg>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'站点名称'" :info="globalInfoStore.globalInfo.siteName"
            :updateFunction="updateSiteName"></InputCard>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'站点图标'" :info="globalInfoStore.globalInfo.siteIcon"
            :updateFunction="updateSiteIcon"></InputCard>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'个性签名'" :info="globalInfoStore.globalInfo.personalSignature"
            :updateFunction="updatePersonalSignature"></InputCard>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'博客开始时间'" :info="globalInfoStore.globalInfo.blogStartTime"
            :updateFunction="updateBlogStartTime"></InputCard>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'博客背景图片'" :info="globalInfoStore.globalInfo.backgroundUrl"
            :updateFunction="updateBackgroundUrl"></InputCard>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'github链接'" :info="globalInfoStore.globalInfo.githubUrl"
            :updateFunction="updateGithubUrl"></InputCard>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'csdn链接'" :info="globalInfoStore.globalInfo.csdnUrl"
            :updateFunction="updateCsdnUrl"></InputCard>
        </li>
        <li style="margin-left: 15px">
          <InputCard :infoDescription="'网易云链接'" :info="globalInfoStore.globalInfo.wyyUrl"
            :updateFunction="updateWyyUrl"></InputCard>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped lang="scss">
.setting {
  background: white;
  padding: 20px 30px;
  border-radius: 5px;
}
</style>
