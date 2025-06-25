import { defineStore } from 'pinia'
import { reactive } from 'vue'
export const useGlobalInfoStore = defineStore('globalInfoStore', () => {

  const globalInfo = reactive({
    siteName: "",
    siteIcon: "",
    aboutMe: "",
    avatarUrl: "",
    backgroundUrl: "",
    personalSignature: "",
    blogStartTime: "",
    icp: "",
    githubUrl: "",
    wyyUrl: "",
    csdnUrl: "",
    friendLinkName: "",
    friendLinkUrl: "",
    friendLinkImg: "",
    friendLinkDescribe: ""
  })
  return {
    globalInfo
  }
})
