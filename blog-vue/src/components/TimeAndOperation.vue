<script setup>
import { ref } from 'vue';
const props = defineProps({
  createTime: String,
  operations: Array
});

const deleteDialogVisible = ref(false);

const handleOperateClick = (operate) => {
  if (operate.name === '删除') {
    deleteDialogVisible.value = true;
  } else {
    operate.onClick();
  }
};

const confirmDelete = () => {
  props.operations[props.operations.length - 1].onClick();
  deleteDialogVisible.value = false;
};

const handleClose = (done) => {
  done();
};
</script>
<template>
  <div class="time-operation">
    <span>{{ props.createTime }}</span>
    <el-dropdown trigger="click" class="el-dropdown-link">
      <p style="transform: translate(0,-7px); padding: 3px;">
        ...
      </p>
      <template #dropdown>
        <el-dropdown-menu style="padding: 10px 0px; width: 120px;">
          <template v-for="(operate, index) in operations" :key="index">
            <el-dropdown-item @click="handleOperateClick(operate)">
              <p :style="{ color: index === props.operations.length - 1 ? 'red' : '#606266', width: '120px' }">
                {{ operate.name }}
              </p>
            </el-dropdown-item>
          </template>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <el-dialog v-model="deleteDialogVisible" title="确认删除" width="30%" :modal="true" :append-to-body="true"
      :before-close="handleClose" style="z-index: 99;">
      <span>确定要删除吗？此操作不可恢复。</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="deleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="confirmDelete">删除</el-button>
        </span>
      </template>
    </el-dialog>
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
