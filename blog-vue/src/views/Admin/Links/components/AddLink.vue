<script setup>
import { reactive } from "vue";
import UploadImg from "@/components/UploadImg.vue";
import { addLinkAPI } from "@/api/links";
import { ElMessage } from "element-plus";
import { uploadFileAPI } from "@/api/file";

const props = defineProps({
  handelShowLink: Function
})
const form = reactive({
  id: null,
  logo: "",
  urlName: "",
  url: "",
  description: ""
})


const resetState = () => {
  console.log(3231);

  form.id = null;
  form.logo = "";
  form.urlName = "";
  form.url = "";
  form.description = "";
}
defineExpose({ resetState });
const onSubmit = async () => {
  await addLinkAPI(form);
  ElMessage.success('添加成功!');
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
    form.logo = res.data;
  };

  document.body.appendChild(input);
  input.click();
  document.body.removeChild(input);
}

</script>

<template>
  <div class="add-link">
    <el-form :model="form" label-width="auto" style="max-width: 500px">
      <el-form-item label="网站地址">
        <el-input placeholder="https://...." v-model="form.url" />
      </el-form-item>
      <el-form-item label="网站名称">
        <el-input v-model="form.urlName" />
      </el-form-item>
      <el-form-item label="logo">
        <UploadImg :border-radius="'0%'" :avatar-img="form.logo" :change-avatar="changeAvatar"></UploadImg>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" type="textarea" maxlength="20" show-word-limit />
      </el-form-item>
      <el-form-item>
        <div style="margin-left: auto;">
          <el-button @click="onSubmit" class="release" type="primary" text> 提交</el-button>
          <el-button type="danger" text @click="props.handelShowLink">取消</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
.add-link {
  padding: 10px 20px 0 20px;
}
</style>
