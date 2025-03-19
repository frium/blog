import { ref, reactive } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('userStore', () => {
  const userInfo = reactive(
    {
      "avatar": "",
      "username": "",
      "email": "",
    }
  )
  const jwt = ref("");
  return {
    userInfo
    , jwt
  }
})
