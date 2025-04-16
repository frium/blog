import { request } from "@/utils/request";

export const getUVPVAPI = () => {
  return request({
    url: '/visit/getUVAndPV'
  })
}