
import { request } from "@/utils/request";

export const addLinkAPI = (data) => {
  return request({
    url: '/links/addLink',
    method: 'POST',
    data
  })
}


export const deleteLinkAPI = (list) => {
  return request({
    url: '/links/deleteLink',
    method: 'POST',
    data: list
  })
}


export const updateLinkAPI = (data) => {
  return request({
    url: '/links/updateLink',
    method: 'POST',
    data
  })
}


export const getLinksAPI = () => {
  return request({
    url: '/links/getLinks',
  })
}
