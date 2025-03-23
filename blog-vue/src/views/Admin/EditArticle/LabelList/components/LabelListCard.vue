<script setup>
import { deleteLabelAPI, updateLabelAPI } from '@/api/adminArticle';
import TimeAndOperation from '@/components/TimeAndOperation.vue';
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

const props = defineProps({
  data: Object
})
const showLabelInput = ref(false);
const labelInput = reactive({});
Object.assign(labelInput, props.data);
const editLabel = () => {
  showLabelInput.value = true;
}

const emit = defineEmits(['delete-success']);
const deleteLabel = async () => {
  const arr = [];
  arr.push(props.data.id);
  await deleteLabelAPI(arr);
  ElMessage.success('删除成功!')
  emit('delete-success', props.data.id);
}

const operations = [
  { name: '编辑', onClick: editLabel },
  { name: '删除', onClick: deleteLabel },
];

const submit = async () => {
  await updateLabelAPI(labelInput);
  ElMessage.success('修改成功!')
  Object.assign(props.data, labelInput);
  showLabelInput.value = false;
}

const cancel = () => {
  Object.assign(labelInput, props.data);
  showLabelInput.value = false;
}
</script>

<template>
  <div class="label-list-card">
    <div v-if="showLabelInput" style="display: flex;">
      <el-input v-model="labelInput.labelName"></el-input>
      <el-button text style="margin-left: 10px;" type="primary" @click="submit">确认</el-button>
      <el-button text style="margin:0" type="danger" @click="cancel">取消</el-button>
    </div>
    <span v-else style="padding: 4.5px 0;">{{ labelInput.labelName }}</span>
    <TimeAndOperation :create-time="labelInput.createTime" :operations="operations" />
  </div>
</template>

<style scoped lang="scss">
.label-list-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
