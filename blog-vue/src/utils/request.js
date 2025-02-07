import axios from "axios";


const request = axios.create({
  baseURL: 'http://',
  timeout: 5000
})


//拦截器
request.interceptors.request.use(config => {
  return config;
}, e => Promise.reject(e))

request.interceptors.response.use(res => res.data, e => {
  ElNotification({
    title: 'Error',
    message: e.response.data.message,
    type: 'error'
  });
  return Promise.reject(e);
})
export default httpInstance;
