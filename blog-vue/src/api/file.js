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
