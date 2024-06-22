package top.frium.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 *
 * @date 2024-06-19 18:16:09
 * @description
 */
@Data
public class R<T> implements Serializable {
    @ApiModelProperty(value = "响应码",required = true)
    @NotNull("响应不为null")
    private Integer code;
    @ApiModelProperty(value = "错误信息",required = true)
    @NotNull
    private String msg;
    @ApiModelProperty(value = "数据",dataType = "String")
    private T data;

    public R() {}

    public static <T> R<T> success(T object) {
        R<T> result = new R<>();
        result.code = StatusCodeEnum.SUCCESS.getCode();
        result.msg = StatusCodeEnum.SUCCESS.getDesc();
        result.data=object;
        return result;
    }

    public static <T> R<T> success() {
        R<T> result = new R<>();
        result.code = StatusCodeEnum.SUCCESS.getCode();
        result.msg = StatusCodeEnum.SUCCESS.getDesc();
        return result;
    }

    public static <T> R<T> error(String msg) {
        R<T> result = new R<>();
        result.code = StatusCodeEnum.FAIL.getCode();
        result.msg = msg;
        return result;
    }
    public static <T> R<T> error(StatusCodeEnum statusCodeEnum) {
        R<T> result = new R<>();
        result.code = statusCodeEnum.getCode();
        result.msg = statusCodeEnum.getDesc();
        return result;
    }
}