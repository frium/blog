package top.frium.context;


import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;

/**
 *
 * @date 2024-05-28 16:48:02
 * @description
 */
public class BaseContext {
    // 使用 ThreadLocal 存储当前线程的用户 ID
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的用户 ID
     * @param id 用户 ID
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取当前线程的用户 ID
     * 如果用户 ID 为空，抛出未登录异常
     * @return 用户 ID
     * @throws MyException 未登录异常
     */
    public static Long getCurrentId() throws MyException {
        if (threadLocal.get() == null) {
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }
        return threadLocal.get();
    }

    /**
     * 清除当前线程的用户 ID
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }
}
