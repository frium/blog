<script setup>
import { createUserAPI, updateUserInfoAPI, verifyEmailAPI, verifyUsernameAPI } from "@/api/adminUser";
import { uploadFileAPI } from "@/api/file";
import UploadImg from "@/components/UploadImg.vue";
import { ElMessage } from "element-plus";
import { onMounted, reactive, ref } from "vue";
const formRef = ref(null);

const props = defineProps({
  handelShowDialog: Function,
  data: Object
});
const form = reactive({
  "id": null,
  "username": "",
  "password": "",
  "email": "",
  "avatar": null,
  "auth": 3
});
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
    {
      validator: async (rule, value, callback) => {
        const res = await verifyUsernameAPI(value, form.id);
        if (res.code !== 200) callback(res.msg);
        else callback();
      },
      trigger: 'blur'
    }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' },
    { max: 50, message: '邮箱长度不能超过50个字符', trigger: 'blur' },
    {
      validator: async (rule, value, callback) => {
        const res = await verifyEmailAPI(value, form.id);
        if (res.code !== 200) callback(res.msg);
        else callback();
      },
      trigger: 'blur'
    }
  ]
})

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
    form.avatar = res.data;
  };

  document.body.appendChild(input);
  input.click();
  document.body.removeChild(input);
}

const emit = defineEmits(['submit-success'])
const onSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      if (props.data) {
        await updateUserInfoAPI(form);
        ElMessage.success('修改成功!');
        Object.assign(props.data, form);
        props.handelShowDialog();
      } else {
        await createUserAPI(form);
        ElMessage.success('创建成功!');
        emit('submit-success');
        props.handelShowDialog();
      }
    }
  })
}

onMounted(() => {
  Object.assign(form, props.data);
  form.password = '';
})
</script>

<template>
  <div class="create-user">
    <el-form ref="formRef" :rules="rules" :model="form" label-width="auto" style="max-width: 500px">
      <el-form-item label="头像">
        <UploadImg :border-radius="'0%'" :avatar-img="form.avatar" :change-avatar="changeAvatar"></UploadImg>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" />
      </el-form-item>
      <el-form-item label="角色" required>
        <el-radio-group v-model="form.auth">
          <el-radio :value="3" size="large">访客</el-radio>
          <el-radio :value="2" size="large">管理员</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <div style="margin-left: auto;">
          <el-button @click="onSubmit" class="release" type="primary" text> 提交</el-button>
          <el-button type="danger" text @click="props.handelShowDialog">取消</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
