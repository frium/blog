<template>
  <div style="border: 1px solid #ccc;min-width: 365px; max-width: 100%; ">
    <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig" />
    <Editor style="height: 400px; overflow-y: hidden;" :defaultConfig="editorConfig" @onCreated="handleCreated" />
  </div>
</template>

<script setup>
import { shallowRef, onBeforeUnmount } from 'vue'
import "@wangeditor/editor/dist/css/style.css";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
// import { useRTEditorStore } from '@/stores/rtEditorStore';
// const rtEditorStore = useRTEditorStore();

const editorRef = shallowRef()

// 工具栏配置
const toolbarConfig = {
  toolbarKeys: [
    "headerSelect",  // 标题选择
    'bold', // 加粗
    'italic', // 斜体
    'through', // 删除线
    'underline', // 下划线
    'justifyCenter', // 居中对齐
    'justifyJustify', // 两端对齐
    'justifyLeft', // 左对齐
    'justifyRight', // 右对齐
    'bulletedList', // 无序列表
    'numberedList', // 有序列表
    'color', // 文字颜色
    'insertLink', // 插入链接
    'fontSize', // 字体大小
    'lineHeight', // 行高
    'delIndent', // 缩进
    'indent', // 增进
    'divider', // 分割线
    'insertTable', // 插入表格
    'undo', // 撤销
    'redo', // 重做
    'clearStyle', // 清除格式
    'fullScreen', // 全屏
    "blockquote", // 引用
    "codeBlock", // 代码块
    "insertImage", // 插入图片
    "uploadImage", // 上传图片
  ]
}


// 编辑器配置
const editorConfig = {
  placeholder: '请输入内容...',
  MENU_CONF: {},// 初始化 MENU_CONF 对象
};

// 上传图片配置
editorConfig.MENU_CONF['uploadImage'] = {
  async customUpload(file, insertFn) {
    // const formData = new FormData();
    // formData.append("file", file);

    // try {
    //   const res = await uploadAvatarAPI(formData); // 假设 uploadAvatarAPI 是一个返回 Promise 的函数
    //   insertFn(res.data.url); // 插入图片 URL
    // } catch (error) {
    //   console.error('上传图片失败:', error); // 打印错误信息
    // }
  }
};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  // rtEditorStore.content = '';
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy();
})


const handleCreated = (editor) => {
  editorRef.value = editor
}


</script>

<style lang='scss' scoped></style>
