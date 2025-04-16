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


export const pauseCommentAPI = (commentId) => {
  return request({
    url: '/adminComment/pauseComment',
    method: 'POST',
    params: {
      commentId
    }
  })
}