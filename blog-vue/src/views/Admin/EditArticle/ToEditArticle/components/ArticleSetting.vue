<script setup>
import { reactive, ref } from 'vue'
import UploadImg from '@/components/UploadImg.vue'

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
  <div class="article-setting">
    <el-form :model="form" label-width="auto" style="max-width: 500px">
      <el-form-item label="标题">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.region" placeholder="选择文章分类">
          <el-option label="Zone one" value="shanghai" />
          <el-option label="Zone two" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="封面">
        <UploadImg></UploadImg>
      </el-form-item>
      <el-form-item label="摘要">
        <el-input v-model="form.desc" type="textarea" maxlength="200" show-word-limit />
      </el-form-item>
      <el-form-item label="置顶">
        <el-switch v-model="form.delivery" />
      </el-form-item>
      <el-form-item>
        <button @click="onSubmit" class="release">发布</button>
        <button>取消</button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
.article-setting {
  min-height: 380px;
  max-height: 500px;
  padding: 0 25px;
  color: black;

  button {
    padding: 0 18px;
    border: 1px solid rgb(217, 219, 224);
    border-radius: 6px;
    transition: all 0.3s;
  }

  .release {
    background-color: black;
    color: #fff;
    margin-right: 20px;

    &:hover {
      background: rgb(64, 64, 66);
    }
  }

}
</style>