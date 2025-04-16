import { request } from "@/utils/request";

export const uploadArticleAPI = (data) => {
  return request({
    url: '/adminArticle/uploadArticle',
    method: 'POST',
    data
  })
}
export const updateArticleAPI = (data) => {
  return request({
    url: '/adminArticle/updateArticle',
    method: 'POST',
    data
  })
}

export const getArticleListAPI = () => {
  return request({
    url: '/adminArticle/getArticleList'
  })
}
export const changeArticleShowStatusAPI = (articleId) => {
  return request({
    url: '/adminArticle/changeArticleShowStatus',
    method: 'POST',
    params: {
      articleId
    }
  })
}
export const deleteArticleAPI = (list) => {
  return request({
    url: '/adminArticle/deleteArticle',
    method: 'POST',
    data: list
  })
}
export const uploadLabelAPI = (labelName) => {
  return request({
    url: '/adminArticle/addLabel',
    method: 'POST',
    params: {
      label: labelName
    }
  })
}
export const deleteLabelAPI = (labelList) => {
  return request({
    url: '/adminArticle/deleteLabel',
    method: 'POST',
    data: labelList
  })
}
export const updateLabelAPI = (label) => {
  return request({
    url: '/adminArticle/updateLabel',
    method: 'POST',
    data: label
  })
}

export const getArticleNumAPI = () => {
  return request({
    url: '/adminArticle/getArticleNum',
  })
}
