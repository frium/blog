<script setup>
import { onMounted, reactive, ref } from "vue";
import UploadImg from "@/components/UploadImg.vue";
import { addLinkAPI, updateLinkAPI } from "@/api/links";
import { ElMessage } from "element-plus";
import { uploadFileAPI } from "@/api/file";
const props = defineProps({
  handelShowLink: Function,
  data: Object
})
const form = reactive({
  id: null,
  logo: "",
  urlName: "",
  url: "",
  description: ""
})

const resetState = () => {
  form.id = null;
  form.logo = "";
  form.urlName = "";
  form.url = "";
  form.description = "";
}

const rules = reactive({
  url: [
    { required: true, message: '请输入网站地址', trigger: 'blur' },
    {
      type: 'url',
      message: '请输入正确的网址格式 (如 https://example.com)',
      trigger: ['blur', 'change']
    }
  ],
  urlName: [
    { required: true, message: '请输入网站名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ]
})
const formRef = ref(null)


defineExpose({ resetState });
const emit = defineEmits(['submit-success'])
const onSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      if (props.data) {
        await updateLinkAPI(form);
        ElMessage.success('修改成功!');
        Object.assign(props.data, form);
        props.handelShowLink();
      } else {
        await addLinkAPI(form);
        ElMessage.success('添加成功!');
        emit('submit-success');
        props.handelShowLink();
      }
    }
  })
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
onMounted(() => {
  Object.assign(form, props.data);
})

const selectImg = async (url) => {
  form.logo = url;
}
</script>

<template>
  <div class="add-link">
    <el-form ref="formRef" :rules="rules" :model="form" label-width="auto" style="max-width: 500px">
      <el-form-item label="网站地址" prop="url">
        <el-input placeholder="https://...." v-model="form.url" />
      </el-form-item>
      <el-form-item label="网站名称" prop="urlName">
        <el-input v-model="form.urlName" />
      </el-form-item>
      <el-form-item label="logo">
        <UploadImg :border-radius="'0%'" :avatar-img="form.logo" :change-avatar="changeAvatar" @select-img="selectImg">
        </UploadImg>
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
