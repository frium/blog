import { request } from "@/utils/request";

export const getCommentsAPI = (articleId) => {
  return request({
    url: '/user/comment/getShowArticleComment',
    params: {
      articleId
    }
  })
}

export const addCommentAPI = (data) => {
  return request({
    url: '/user/comment/addComment',
    method: 'POST',
    data
  })
}

export const getCommentNumAPI = () => {
  return request({
    url: '/adminComment/getCommentNum'
  })
}
export const getAllCommentAPI = () => {
  return request({
    url: '/adminComment/getAllComment'
  })
}


export const deleteCommentAPI = (data) => {
  return request({
    url: '/adminComment/deleteComment',
    method: 'POST',
    data
  })
}


export const passCommentAPI = (commentId) => {
  return request({
    url: '/adminComment/passComment',
    method: 'POST',
    params: {
      commentId
    }
  })
}

export const searchCommentByContentAPI = (searchInfo) => {
  return request({
    url: '/adminComment/searchCommentByContent',
    method: 'POST',
    params: {
      searchInfo
    }
  })
}
