import { defineStore } from 'pinia'
import { reactive } from 'vue'
export const useGlobalInfoStore = defineStore('globalInfoStore', () => {

  const globalInfo = reactive({
    siteName: "",
    aboutMe: "",
    avatarUrl: "",
    backgroundUrl: "",
    personalSignature: "",
    blogStartTime: "",
    icp: "",
    githubUrl: "",
    wyyUrl: "",
    csdnUrl: ""
  })
  return {
    globalInfo
  }
})
