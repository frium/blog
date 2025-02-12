import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useScrollStore = defineStore('scrollStore', () => {
  const scrollProportion = ref(0)
  return { scrollProportion }
})
