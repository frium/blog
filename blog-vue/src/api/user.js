import { request } from "@/utils/request";

export const getVerifyAPI = (email) => {
  return request({
    url: '/user/getEmailSMS',
    params: {
      email
    }
  })
}

export const loginAPI = (data) => {
  return request({
    url: '/user/loginByEmail',
    method: 'POST',
    data
  })
}

export const registerAPI = (data) => {
  return request({
    url: '/user/registerByEmail',
    method: 'POST',
    data
  })
}

export const getUserInfoAPI = () => {
  return request({
    url: '/user/getUserInfo',
  })
}
