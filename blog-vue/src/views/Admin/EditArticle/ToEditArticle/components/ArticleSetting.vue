<script setup>
import { onMounted, reactive, ref } from 'vue'
import UploadImg from '@/components/UploadImg.vue'
import { useEditArticleStore } from '@/stores/editArticle'
import { getLabelsAPI } from '@/api/article';
import { uploadArticleAPI } from '@/api/adminArticle';
import { ElMessage } from 'element-plus';
import { uploadFileAPI } from '@/api/file';
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';

const route = useRoute();
const router = useRouter();
const article = reactive({});
const props = defineProps({
  closeArticleSetting: Function
})
const editArticleStore = useEditArticleStore();

const labels = ref([]);
const creatArticle = async () => {
  await uploadArticleAPI(article);
  ElMessage.success('发布成功!')
  editArticleStore.article = article;
  router.push({ name: 'ArticleList' })
}

const updateArticle = async () => {
  editArticleStore.article = article;
  props.closeArticleSetting();
}

const cancle = () => {
  Object.assign(article, editArticleStore.article);
  props.closeArticleSetting();
}

onMounted(async () => {
  const res = await getLabelsAPI();
  labels.value = res.data;
  Object.assign(article, editArticleStore.article);
  article.label.forEach((item, index, array) => {
    array[index] = item.id;
  });
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
    article.coverImg = res.data;
  };

  document.body.appendChild(input);
  input.click();
  document.body.removeChild(input);
}
const selectImg = (url) => {
  article.coverImg = url;
}

</script>

<template>
  <div class="article-setting">
    <el-form @submit.prevent :model="article" label-width="auto" style="max-width: 500px">
      <el-form-item label="标题">
        <el-input v-model="article.title" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="article.label" placeholder="选择文章分类" multiple>
          <template v-for="label in labels" :key="label.id">
            <el-option :label=label.labelName :value=label.id />
          </template>
        </el-select>
      </el-form-item>
      <el-form-item label="封面">
        <UploadImg :border-radius="'0'" :avatar-img="article.coverImg" :change-avatar="changeAvatar"
          @select-img="selectImg">
        </UploadImg>
      </el-form-item>
      <el-form-item label="摘要">
        <el-input v-model="article.summary" type="textarea" maxlength="200" show-word-limit />
      </el-form-item>
      <el-form-item label="置顶">
        <el-switch v-model="article.isTop" />
      </el-form-item>
      <el-form-item>
        <button @click="updateArticle" class="release" v-if="route.params.editArticleId">修改</button>
        <button @click="creatArticle" class="release" v-else>发布</button>
        <button @click="cancle">取消</button>
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
