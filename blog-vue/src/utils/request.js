import { useUserStore } from "@/stores/userStore";
import axios from "axios";
import { ElNotification } from "element-plus";

export const request = axios.create({
  baseURL: '/api',
})

export const musicRequest = axios.create({
  baseURL: 'https://api.i-meto.com/meting/api',
})


//拦截器
request.interceptors.request.use(config => {
  const token = useUserStore().jwt;
  if (token) config.headers.Authorization = `${token}`;
  return config;
}, e => Promise.reject(e))

request.interceptors.response.use(res => res.data, e => {
  if (e.response?.data) {
    ElNotification({
      title: 'Error',
      message: e.response.data.message,
      type: 'error'
    });
  }
  return Promise.reject(e);
})

