<script setup>
import { useScrollStore } from '@/stores/scrollStore';
import { ref, watch } from 'vue';
import VueCountTo from './VueCountTo/vue-countTo.vue';
const scrollStore = useScrollStore();

const newScrollProportion = ref(0);
const oldScrollProportion = ref(0);

watch(() => scrollStore.scrollProportion, (newValue, oldValue) => {
  newScrollProportion.value = newValue;
  oldScrollProportion.value = oldValue;
});

</script>

<template>
  <div class="nav-ball">
    <VueCountTo :startVal="oldScrollProportion" :endVal="newScrollProportion" :duration="500"></VueCountTo>
  </div>
</template>

<style scoped lang="scss">
.nav-ball {
  height: 40px;
  width: 40px;
  margin-top: 10px;
  border-radius: 50%;
  background: rgb(187, 141, 141);
  font-size: 14px;
  text-align: center;
  line-height: 40px;
}
</style>
