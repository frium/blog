import { request } from "@/utils/request";

export function uploadFileAPI(file, onUploadProgress) {
  const formData = new FormData();
  formData.append('file', file);

  return request({
    url: '/file/uploadFile',
    method: 'POST',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress
  })
}
