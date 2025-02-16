<script setup>
import { onMounted, ref, nextTick } from "vue";
import "aplayer/dist/APlayer.min.css";
import APlayer from "aplayer";
import { useMusicStore } from "@/stores/musicStore";

const musics = ref([]);
const player = ref(null);
const container = ref(null);
const musicStore = useMusicStore();
const lrcContainer = ref(null);
const currentLrc = ref('');
const currentIndex = ref(0);
const lrcData = ref([]);
const isShow = ref(false);
const getMusicList = async () => {
  await musicStore.getMusicList(13049118006);
  musics.value = musicStore.musics;
  await nextTick();
  initPlayer();
};

const initPlayer = () => {
  if (player.value) {
    player.value.destroy();
  }

  player.value = new APlayer({
    container: container.value,
    audio: musics.value,
    lrcType: 0,
    lrcContainer: lrcContainer.value,
    autoplay: false,
    loop: "all",
    order: "list",
    preload: "auto",
    listFolded: true,
    listMaxHeight: "200px",
    theme: "#352d2d"
  });
  player.value.on('loadstart', async () => {
    const audio = player.value.audio;
    if (!audio) return;
    const currentMusic = musicStore.musics[currentIndex.value];
    if (!currentMusic?.lrc) {
      currentLrc.value = '暂无歌词';
      return;
    }
    const lrcText = await fetchLrcContent(currentMusic.lrc);
    lrcData.value = parseLRC(lrcText);
    console.log(lrcData.value);
  })
};
const fetchLrcContent = async (url) => {
  const response = await fetch(url);
  if (!response.ok) throw new Error(`HTTP错误 ${response.status}`);
  return await response.text();
};
const parseLRC = (lrcText) => {
  const lines = lrcText.split('\n')
  const lrcData = [];
  lines.forEach(line => {
    const matches = line.match(/\[(\d{2}):(\d{2}\.\d{2,3})\](.*)/);
    if (matches) {
      const minutes = parseInt(matches[1]);
      const seconds = parseFloat(matches[2]);
      const time = minutes * 60 + seconds;
      const text = matches[3].trim();
      lrcData.push({ time, text });
    }
  });
  return lrcData;
}

const changeState = () => {
  isShow.value = !isShow.value;
}
onMounted(() => {
  getMusicList();
});
</script>

<template>
  <div class="player">
    <div class="aplayer-body" :style="{ transform: !isShow ? ' translateX(-378px)' : 'translateX(0px)' }">
      <div ref="container"></div>
      <div class="aplayer-miniswitcher">
        <button class="aplayer-icon" @click="changeState">
          <svg xmlns="http://www.w3.org/2000/svg" version="1.1" viewBox="0 0 32 32"
            :style="{ transform: isShow ? 'rotate(180deg)' : 'rotate(0deg)' }">
            <path d="M22 16l-10.105-10.6-1.895 1.987 8.211 8.613-8.211 8.612 1.895 1.988 8.211-8.613z"></path>
          </svg>
        </button>
      </div>
    </div>
    <div v-if="false" class="custom-lrc">
      <div v-for="(music, index) in musicStore.musics" :key="index" class="lrc-wrapper">
        <template v-if="music.lrc">
          <div v-for="line in lrcData" :key="line.time" :class="{ active: currentLrc === line.text }" class="lrc-line">
            {{ line.text }}
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.player {
  width: 400px;
  height: auto;

  .aplayer-body {
    width: 100%;
    position: relative;
    transition: 0.25s;

    .aplayer-miniswitcher {
      position: absolute;
      display: flex;
      align-items: center;
      width: 22px;
      height: 100%;
      background-color: #171717;
      right: 0;
      top: 50%;
      transform: translateY(-50%);
      z-index: 10;

      button {
        cursor: pointer;
        color: #f6cac9;
      }

      svg {
        width: 22px;
        fill: currentColor;
        transition: 0.1s;
      }
    }
  }


  .custom-lrc {
    height: 200px;
    overflow-y: auto;
    background: rgba(0, 0, 0, 0.6);
    border-radius: 8px;
    padding: 15px;
    margin-top: 20px;

    .lrc-wrapper {
      display: none;

      &:first-child {
        display: block;
      }
    }

    .lrc-line {
      color: #ffffff99;
      font-size: 16px;
      line-height: 2;
      transition: all 0.3s ease;
      transform-origin: left center;

      &.active {
        color: #ff6b6b;
        font-weight: bold;
        transform: scale(1.05);
        text-shadow: 0 0 10px rgba(255, 107, 107, 0.3);
      }
    }
  }
}

:deep(.aplayer) {
  background: rgba(40, 44, 52, 0.6) !important;
}

:deep(.aplayer .aplayer-lrc p) {
  color: rgba(1, 1, 1, 0) !important;
}

:deep(.aplayer .aplayer-lrc p.aplayer-lrc-current) {
  color: #f6cac9 !important;
}

:deep(.aplayer.aplayer-withlist .aplayer-info) {
  border-bottom: 0px !important;
  width: 300px !important;
}

:deep(.aplayer .aplayer-lrc:after, .aplayer .aplayer-lrc:before) {
  background: linear-gradient(180deg, hsla(0, 0%, 100%, 0) 0, hsla(0, 0%, 100%, .8));
}

:deep(.aplayer .aplayer-played) {
  background: #290902 !important;
}

:deep(.aplayer-list) {
  background-color: rgba(40, 44, 52, 0.6) !important;
  /* 播放列表背景（深色） */
}

:deep(.aplayer-list li) {
  color: #d7c9c9 !important;
  border-top: 0px !important;
}

:deep(.aplayer-list li.aplayer-list-light) {
  background: rgba(1, 1, 1, 0) !important;
  /* 选中的歌曲颜色 */
}
</style>
