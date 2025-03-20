import { ElNotification } from 'element-plus';

const activeNotifications = new Map();
const getNotificationKey = (options) => `${options.message}-${options.type}`;

// 触发通知的函数
function notificationToast(options, closeTime = 3000) {
  const key = getNotificationKey(options);
  // 如果当前消息已存在，直接返回，保留旧通知
  if (activeNotifications.has(key)) {
    return;
  }

  // 创建新通知
  const notification = ElNotification(options);

  // 将新通知存入 Map
  activeNotifications.set(key, notification);

  // 设置自动关闭并清理引用
  setTimeout(() => {
    notification.close();
    activeNotifications.delete(key);
  }, closeTime); // 使用传入的关闭时间或默认值
}

// 为 notificationToast 添加四个类型的方法：'success'、'error'、'info'、'warning'
['success', 'error', 'info', 'warning'].forEach(item => {
  notificationToast[item] = (options) => {
    // 如果传入的 options 是字符串，则将其转化为包含 message 的对象
    if (typeof options === 'string') {
      options = {
        message: options
      };
    }

    options.type = item;
    return notificationToast(options);
  };
});

export default notificationToast;
