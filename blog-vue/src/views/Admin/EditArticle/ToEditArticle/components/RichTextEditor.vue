<script setup>
import { useEditArticleStore } from '@/stores/editArticle';
import Vditor from 'vditor'
import 'vditor/dist/index.css';
import { ref, onMounted, watch, onBeforeUnmount } from 'vue';

const vditor = ref()
const editArticleStore = useEditArticleStore();

// 监听 store 中内容的变化，更新编辑器
watch(() => editArticleStore.article.content, (newVal) => {
  if (vditor.value && vditor.value.getValue() !== newVal) {
    vditor.value.setValue(newVal);
  }
});

onMounted(() => {
  vditor.value = new Vditor('vditor', {
    value: editArticleStore.article.content,
    height: 'calc(100vh - 88px)',
    width: '100%',
    placeholder: '开始创作吧...',
    theme: 'classic',
    mode: 'ir',
    toolbar: [
      'emoji',
      'headings',
      'bold',
      'italic',
      'strike',
      '|',
      'line',
      'quote',
      'list',
      'ordered-list',
      'check',
      'outdent',
      'indent',
      '|',
      'code',
      'inline-code',
      'insert-after',
      'insert-before',
      '|',
      'table',
      'link',
      '|',
      'undo',
      'redo',
      '|',
      'fullscreen',
      'preview',
      'both',
      'export',
    ],
    toolbarConfig: {
      pin: true,
    },
    counter: {
      enable: true,
      type: 'markdown',
    },
    preview: {
      delay: 500,
      mode: 'editor',
      url: '/api/markdown',
      parse: (element) => {
        console.log(element);
      },
    },
    tab: '  ',
    typewriterMode: true,
    select: {
      enable: true,
    },
    outline: {
      enable: true,
      position: 'right',
    },
    cdn: '',
    icon: 'material',
    debugger: true,
    adjustToolbarPosition: true,
    resize: {
      enable: true,
      position: 'bottom',
    },
    classes: {
      preview: 'custom-preview-class',
    },
    input: (value) => {
      // 当编辑器内容变化时更新 store
      editArticleStore.article.content = value;
    },
    blur: (value) => {
      // 编辑器失去焦点时也更新 store
      editArticleStore.article.content = value;
    },
  });
})
onBeforeUnmount(() => {
  editArticleStore.article.content = '';
  if (vditor.value == null) return;
  vditor.value.destroy();
})

</script>

<template>
  <div id="vditor"></div>
</template>

<style lang="scss" scoped>
:deep(.vditor-toolbar) {
  display: flex;
  padding-right: 250px;
  padding-left: 7% !important;
  justify-content: space-around;

}



:deep(.vditor-reset) {
  padding: 8px 9% !important;
}
</style>
