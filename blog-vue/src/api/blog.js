import { request } from "@/utils/request";
export const getBlogInfoAPI = () => {
  return request({
    url: '/blogGlobalInfo/getGlobalInfo',
  })
}
export const updateBlogInfoAPI = (data) => {
  return request({
    method: 'POST',
    url: '/blogGlobalInfo/updateGlobalInfo',
    data
  })
}

