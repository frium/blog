package top.frium.common;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum StatusCodeEnum {
    SUCCESS(200,"操作成功",200),
    ERROR(5000, "系统异常", 500),
    FAIL(-1, "操作失败",200),
    NOT_FOUND(4040, "未找到相关内容",200),
    VALUE_ERROR(4041,"参数有误",400),
    USER_EXIST(1001,"账号已被注册",200),
    LOGIN_FAIL(1002,"登录失败,请检查账号或者密码是否有误",200),
    NOT_LOGIN(1003,"用户未登陆",401),
    ERROR_VERIFY(1004,"验证码错误",200),
    USER_NOT_EXIST(1004, "用户不存在",200),
    PERSONAL_ID_ERROR(1005,"身份证格式有误",200),
    PHONE_ERROR(1006,"手机号格式有误",200),
    PASSWORD_ERROR(1007,"密码格式有误",200);

    private final Integer code;
    private final String desc;
    private final Integer httpStatusCode;
}
