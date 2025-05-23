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


export const updateUsernameAPI = (username) => {
  return request({
    url: `/user/updateUsername?username=${username}`,
    method: 'POST',
  })
}

export const updateEmailAPI = (data) => {
  return request({
    url: '/user/updateEmail',
    method: 'POST',
    data
  })
}

export const updateAvatarAPI = (url) => {
  return request({
    url: `/user/updateAvatar?avatarUrl=${url}`,
    method: 'POST',
  })
}

export const logoutAPI = () => {
  return request({
    url: '/user/logout'
  })
}
