<script setup>
import { ElMessage } from "element-plus";
import { ref, watch } from "vue"

const props = defineProps({
  updateFunction: Function,
  infoDescription: String,
  info: String,
})

const info = ref(props.info);
const isChange = ref(false);
const showEdit = ref(false);
const cancle = () => {
  isChange.value = false;
  info.value = props.info;
}
const editInfo = () => {
  isChange.value = true;
}

const updateFunctionHandle = async () => {
  try {
    await props.updateFunction(info.value);
    ElMessage.success('修改成功!');
    cancle();
  } catch {
    info.value = props.info;
  }
}
watch(() => props.info, (newVal) => {
  info.value = newVal;
});
</script>

<template>
  <div class="input-card" @mouseenter="showEdit = true" @mouseleave="showEdit = false">
    <span class="des">{{ props.infoDescription }}</span>
    <span v-if="!isChange" class="info-span">{{ props.info }}</span>
    <input v-if="isChange" class="info-input" type="text" v-model="info">
    <button v-if="isChange" @click="updateFunctionHandle" style="color: #2c98e5; margin-right: 15px;">确认</button>
    <button v-if="isChange" @click="cancle" style="color: #d32f2f;">取消</button>
    <button v-if="!isChange && showEdit" class="edit" @click="editInfo">编辑</button>
  </div>

</template>

<style scoped lang="scss">
.input-card {
  display: flex;
  align-items: center;
  padding: 3px 0;

  .des {
    width: 100px;
    margin-right: 10px;
    justify-content: space-around;
    margin-right: 20px;

  }

  .info-span {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .info-span,
  .info-input {
    width: 400px;
    height: 35px;
    margin-right: 15px;
    line-height: 35px;
  }

  .info-input {

    padding-left: 15px;
    letter-spacing: 0.15px;
    border: none;
    outline: none;
    background-color: #f0f0f0;
    transition: 0.25s ease;
    border-radius: 8px;
    box-shadow: inset 2px 2px 4px #d1d9e6, inset -2px -2px 4px #f9f9f9;

    &:focus {
      box-shadow: inset 4px 4px 4px #d1d9e6, inset -4px -4px 4px #f9f9f9;

    }

  }

  .edit {
    color: #2c98e6;
  }
}
</style>
