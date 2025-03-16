<script setup>
const props = defineProps({
  createTime: String,
  operations: Array
})
</script>

<template>
  <div class="time-operation">
    <span>{{ props.createTime }}</span>
    <el-dropdown trigger="click" class="el-dropdown-link">
      <p style="transform: translate(0,-7px); padding: 3px;">
        ...
      </p>
      <template #dropdown>
        <el-dropdow3n-menu style="padding: 8px 2px;">
          <template v-for="(operate, index) in operations" :key="index">
            <el-dropdown-item @click="operate.onClick">
              <p :style="{ color: index === props.operations.length - 1 ? 'red' : '#606266', width: '120px' }">
                {{ operate.name }}
              </p>
            </el-dropdown-item>
          </template>
        </el-dropdow3n-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<style scoped lang="scss">
.time-operation {
  display: flex;
  align-items: center;
  gap: 20px;

  .el-dropdown-link {
    cursor: pointer;
    font-size: 20px;
    font-weight: 800;
  }
}
</style>
