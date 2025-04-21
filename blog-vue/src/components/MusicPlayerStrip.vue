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
    lrcType: 1,
    fixed: true,
    lrcContainer: lrcContainer.value,
    autoplay: false,
    loop: "all",
    order: "list",
    preload: "auto",
    listFolded: true,
    listMaxHeight: "200px",
    theme: '#54565c'

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
    musicStore.playMusicLrc = parseLRC(lrcText);    //将歌词传递给LyricStrip.vue
  });
  player.value.on('listswitch', (index) => {
    currentIndex.value = parseInt(index.index);
  });
  player.value.on('lrcshow', () => {
    musicStore.showLrc = true;
  });

  player.value.on('lrchide', () => {
    musicStore.showLrc = false;
  });
  player.value.on('timeupdate', () => {
    musicStore.lrcIndex = findLrcIndex();
  });
  const aplayerElement = document.querySelector('.aplayer');
  if (aplayerElement) {
    aplayerElement.style.transition = 'transform 0.3s ease';
    const observer = new MutationObserver(() => {
      const isNarrow = aplayerElement.classList.contains('aplayer-narrow');
      if (!isNarrow) {
        aplayerElement.style.transform = 'translateX(0px)';
      } else {
        aplayerElement.style.transform = 'translateX(-67px)';
      }
    });
    observer.observe(aplayerElement, { attributes: true, attributeFilter: ['class'] });
  }
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
      let text = matches[3] ? matches[3].trim() : "~";
      lrcData.push({ time, text });
    }
  });
  return lrcData;
};

const findLrcIndex = () => {

  const currentTime = player.value.audio.currentTime;
  const playMusicLrc = musicStore.playMusicLrc;
  for (let i = 0; i < playMusicLrc.length; i++) {
    if (currentTime < playMusicLrc[i].time) {
      return i - 1;
    }
  }
  return playMusicLrc.length - 1;
}

const toggleMusic = () => {
  player.value.toggle();
};
const skipBack = () => {
  player.value.skipBack();
};
const skipForward = () => {
  player.value.skipForward();
};
const trunOffLrc = () => {
  player.value.lrc.hide();
  const lrcBtn = container.value.querySelector('.aplayer-icon-lrc');
  lrcBtn.classList.toggle('aplayer-icon-lrc-inactivity');

}
defineExpose({ toggleMusic, skipBack, skipForward, trunOffLrc });

onMounted(() => {
  getMusicList();
});
</script>

<template>
  <div class="player">
    <div ref="container"></div>
  </div>
</template>

<style scoped lang="scss">
.player {
  width: 300px;
  height: auto;
}

:deep(.aplayer-body) {
  width: 366px !important;
  background-color: rgb(32, 33, 35, 0.6) !important;
}

:deep(.aplayer) {
  background-color: transparent !important;
  max-width: 366px !important;
  transform: translateX(-67px);
}

:deep(.aplayer.aplayer-withlist .aplayer-info) {
  border: 0px !important;
  width: 300px !important;
  background-color: rgba(39, 42, 48, 0.8) !important;
  color: #d7d7d7 !important;
}

:deep(.aplayer-list) {
  background-color: rgba(40, 44, 52, 0.8) !important;
  border: 0 !important;
}

:deep(.aplayer-list li) {
  color: #d7c9c9 !important;
  border-top: 0px !important;
}

:deep(.aplayer-list li.aplayer-list-light) {
  background: rgba(40, 44, 52, 0.4) !important;
}

:deep(.aplayer-list li:hover) {
  background: rgba(70, 75, 86, 0.7) !important;
}

:deep(.aplayer .aplayer-miniswitcher) {
  background: rgba(69, 69, 73, 0.8) !important;
}

@media (max-width: 500px) {
  .player {
    display: none;
  }
}
</style>
