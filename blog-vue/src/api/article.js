import { request } from "@/utils/request";

export const getShowArticleListAPI = (pageNum) => {
  return request({
    url: '/user/article/getShowArticleList',
    params: {
      pageNum
    }
  })
}
export const getShowArticleNumAPI = () => {
  return request({
    url: '/user/article/getShowArticleNum'
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

export const getArticleByTimeAPI = () => {
  return request({
    url: '/user/article/getArticleByTime'
  })
}

export const getLabelNumAPI = () => {
  return request({
    url: '/user/article/getLabelNum'
  })
}

