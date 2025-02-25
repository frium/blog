<script setup>
import FriendCard from './components/FriendCard.vue';
</script>

<template>
  <div class="friend-cards">
    <FriendCard v-for="index in 6" :key="index"></FriendCard>
  </div>
</template>

<style scoped lang="scss">
.friend-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 15px 30px;


}
</style>
