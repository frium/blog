import { ref } from "vue";
import { getMusicAPI } from "@/api/music";
import { defineStore } from 'pinia'

export const useMusicStore = defineStore('music', () => {
  const musics = ref([]);
  const getMusicList = async (id) => {
    const res = await getMusicAPI("netease", "playlist", id)
    musics.value = res.data.map((song) => ({
      name: song.title,
      artist: song.author,
      url: song.url,
      cover: song.pic,
      lrc: song.lrc,
      theme: '#ff5733'
    }));
  }

  return {
    musics,
    getMusicList
  }
}, {
  persist: true
})
