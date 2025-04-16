import { request } from "@/utils/request";

export const getComments = (articleId) => {
  return request({
    url: '/user/comment/getShowArticleComment',
    params: {
      articleId
    }
  })
}

export const addComment = (data) => {
  return request({
    url: '/user/comment/addComment',
    method: 'POST',
    data
  })
}
