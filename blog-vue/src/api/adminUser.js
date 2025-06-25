import { request } from "@/utils/request";

export const getUsersAPI = () => {
  return request({
    url: '/manageUser/getUsers'
  })
}
export const verifyUsernameAPI = (username, userId) => {
  return request({
    url: `/manageUser/verifyUsername`,
    params: {
      username,
      userId: userId ?? null
    },
  })
}
export const verifyEmailAPI = (email, userId) => {
  return request({
    url: '/manageUser/verifyEmail',
    params: {
      email,
      userId: userId ?? null
    },
  })
}
export const createUserAPI = (data) => {
  return request({
    url: '/manageUser/createUser',
    method: 'POST',
    data
  })
}

export const deleteUsersAPI = (data) => {
  return request({
    url: '/manageUser/deleteUsers',
    method: 'POST',
    data
  })
}

export const updateUserInfoAPI = (data) => {
  return request({
    url: '/manageUser/updateUserInfo',
    method: 'POST',
    data
  })
}

export const getUserNumAPI = () => {
  return request({
    url: '/manageUser/getUserNum',
  })
}

export const searchUserByNameAPI = (searchInfo) => {
  return request({
    url: '/manageUser/searchUserByName',
    method: 'POST',
    params: {
      searchInfo
    }
  })
}
