import { reactive } from 'vue'
import { defineStore } from 'pinia'

export const useArticleStore = defineStore('articleStore', () => {
  const searchData = reactive({
    searchInfo: "",
    pageNum: 1
  })
  const articleArr = [];
  const articleNum = 0;
  return {
    searchData,
    articleArr,
    articleNum
  }
})
