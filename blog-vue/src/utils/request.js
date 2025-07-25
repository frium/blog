import { useUserStore } from "@/stores/userStore";
import axios from "axios";
import notificationToast from "./notificationToast ";
import router from "@/router";

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

request.interceptors.response.use(res => res.data, error => {
  if (error.response?.status === 401) {
    notificationToast.error('用户身份过期!');
    useUserStore().jwt = "";
    useUserStore().userInfo.avatar = "";
    useUserStore().userInfo.email = "";
    useUserStore().userInfo.username = "";
    return Promise.reject(error);
  } else if (error.response?.status === 403) {
    notificationToast.error('您暂无权限访问');
    router.push({ name: 'Home' })
    return Promise.reject(error);
  } else if (error.response?.status === 404) {
    notificationToast.error('未找到相关内容');
    router.push({ name: 'NotFound' })
    return Promise.reject(error);
  }

  const errorMsg = error.response?.data.msg || '请求异常，请稍后重试'
  notificationToast.error(errorMsg)
  return Promise.reject(error);
})

