<script setup>
import LayoutNav from "./components/LayoutNav.vue";
import LayoutFooter from "./components/LayoutFooter.vue";
import PersonalCard from "./components/PersonalCard.vue";
import NavBall from "./components/NavBall.vue";
import BackToTop from './components/BackToTop.vue';
import MusicPlayerStrip from './components/MusicPlayerStrip.vue';
import LyricStrip from './components/LyricStrip.vue';
import { ref, onMounted, onUnmounted } from 'vue';
const isHidden = ref(false);
const observer = ref(null);



onMounted(() => {
  observer.value = new IntersectionObserver(
    ([entry]) => {
      isHidden.value = !entry.isIntersecting; //目标元素和视口相交
    },
    {
      threshold: 0.1,
      rootMargin: "-35px 0px 0px 0px"
    }
  );
  const target = document.querySelector("#trigger");
  if (target) observer.value.observe(target);
});

onUnmounted(() => {
  if (observer.value) observer.value.disconnect();
});
</script>

<template>
  <div id="app">
    <LayoutNav
      :style="{ transform: isHidden ? 'translateY(-100%)' : 'translateY(0)', transition: 'transform 0.8s ease' }"
      id="nav"></LayoutNav>
    <div class="selection">
      <div class="container">
        <div class="cloumns">
          <div class="router-view">
            <div id="trigger" style="height: 1px; width: 100%;"></div>
            <router-view></router-view>
          </div>
          <div class="right">
            <PersonalCard class="personal-card"
              :style="{ top: isHidden ? '30px' : '80px', transition: 'top 0.8s ease' }">
            </PersonalCard>
          </div>
        </div>
      </div>
    </div>
    <BackToTop class="back-to-top"></BackToTop>
    <NavBall class="nav-ball"></NavBall>
    <MusicPlayerStrip class="music-player-strip"></MusicPlayerStrip>
    <LyricStrip class="lrc-strip"></LyricStrip>
    <LayoutFooter></LayoutFooter>
  </div>
</template>

<style scoped lang="scss">
#app {
  .selection {
    position: relative;
    padding: 80px 5vw 40px 5vw;
    min-height: 100vh;
    width: 100%;

    .container {
      margin: 0 auto;

      .cloumns {
        display: flex;
        justify-content: space-between;
        width: 100%;

        .right {
          .personal-card {
            position: sticky;

            @media (max-width: 1440px) {
              & {
                transform: scale(0.8);
                transform-origin: top;
              }
            }

            @media (max-width: 1180px) {
              & {
                transform: scale(0.7);
                transform-origin: top;
              }
            }

            @media (max-width: 500px) {
              & {
                display: none;
              }
            }
          }
        }

        .router-view {
          margin-left: 1vw;
          width: 76%;

          @media (min-width: 1920px) {
            & {
              width: 80%;
            }
          }

          @media (max-width: 1440px) {
            & {
              width: 80%
            }
          }

          @media (max-width: 500px) {
            & {
              width: 400px;
            }
          }
        }
      }

    }
  }

  .back-to-top {
    position: fixed;
    bottom: 80px;
    right: 20px;
  }

  .nav-ball {
    position: fixed;
    bottom: 30px;
    right: 20px;
  }
}
</style>
