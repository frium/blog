import { useUserStore } from "@/stores/userStore";

const permissionDirective = {
  mounted(el, binding) {
    const { value } = binding;
    const userStore = useUserStore();
    if (value && value instanceof Array && value.length > 0) {
      const hasPermission = userStore.userPermissions.some((permission) =>
        value.includes(permission)
      );
      if (!hasPermission) {
        el.parentNode?.removeChild(el);
      }
    } else if (typeof value === "string") {
      const hasPermission = userStore.userPermissions.includes(value);
      if (!hasPermission) {
        el.parentNode?.removeChild(el);
      }
    }
  },
};
export default permissionDirective;
