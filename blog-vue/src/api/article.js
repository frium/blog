import { request } from "@/utils/request";

export const getShowArticleListAPI = (pageNum) => {
  return request({
    url: '/article/getArticleList',
    params: {
      pageNum
    }
  })
}
export const getArticleNumAPI = () => {
  return request({
    url: '/article/getArticleNum'
  })
}
export const getArticleAPI = (articleId) => {
  return request({
    url: '/article/getArticle',
    params: {
      articleId
    }
  })
}
export const getLabelsAPI = () => {
  return request({
    url: '/article/getLabels'
  })
}

