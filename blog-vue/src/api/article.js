import { request } from "@/utils/request";

export const getShowArticleListAPI = (pageNum) => {
  return request({
    url: '/user/article/getShowArticleList',
    params: {
      pageNum
    }
  })
}
export const getArticleNumAPI = () => {
  return request({
    url: '/user/article/getArticleNum'
  })
}
export const getArticleAPI = (articleId) => {
  return request({
    url: '/user/article/getArticle',
    params: {
      articleId
    }
  })
}
export const getLabelsAPI = () => {
  return request({
    url: '/user/article/getLabels'
  })
}

