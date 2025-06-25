import { request } from "@/utils/request";
export const uploadFileAPI = (file, onUploadProgress, signal) => {
  const formData = new FormData();
  formData.append('file', file);

  return request({
    url: '/file/uploadFile',
    method: 'POST',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress,
    signal: signal
  })
}

export const getAllFilesAPI = () => {
  return request({
    url: '/file/getAllFiles',
  })
}
export const deleteFileAPI = (list) => {
  return request({
    url: '/file/deleteFile',
    method: 'POST',
    data: list
  })
}

export const searchFileByNameAPI = (searchInfo) => {
  return request({
    url: '/file/searchFileByName',
    method: 'POST',
    params: {
      searchInfo
    }
  })
}
