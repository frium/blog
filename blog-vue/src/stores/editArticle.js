import { reactive } from 'vue'
import { defineStore } from 'pinia'

export const useEditArticleStore = defineStore('editArticleStore', () => {
  const article = reactive({
    "id": 32,
    "title": "",
    "label": [],
    "coverImg": "",
    "summary": "",
    "isShow": true,
    "isTop": false,
    "content": ""
  })
  return { article }
})
