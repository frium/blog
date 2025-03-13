<script setup>
import { reactive, ref } from "vue";
import UploadImg from "@/components/UploadImg.vue";

const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})




const onSubmit = () => {
  console.log('submit!')
}



</script>

<template>
  <div class="add-link">
    <el-form :model="form" label-width="auto" style="max-width: 500px">
      <el-form-item label="网站地址">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="网站名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="logo">
        <UploadImg></UploadImg>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.desc" type="textarea" maxlength="200" show-word-limit />
      </el-form-item>
      <el-form-item>
        <button @click="onSubmit" class="release">提交</button>
        <button>取消</button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
.add-link {
  padding: 10px 20px;


}
</style>