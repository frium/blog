package top.frium.common;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private HttpServletResponse response;

    @ExceptionHandler(MyException.class)
    public R<?> myExceptionHandler(MyException e) {
        log.info("业务异常信息：{}", e.getMessage());
        if (e.getStatusCodeEnum() != null) {
            response.setStatus(e.getHttpStatusCode());
            return R.error(e.getStatusCodeEnum());
        }
        response.setStatus(e.getHttpStatusCode());
        return R.error(e.getMessage());
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class  )
    public R<?> formatExceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.error("参数格式错误, {}", e.getMessage());
        response.setStatus(StatusCodeEnum.VALUE_ERROR.getHttpStatusCode());
        return R.error(StatusCodeEnum.VALUE_ERROR);
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public R<String> exceptionHandler(NullPointerException e) {
        log.error("发生空指针异常！ msg: -> ", e);
        return R.error("发生空指针异常!");
    }

    /**
     * 服务器异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> exception(Exception e) {
        log.error("未知异常！ msg: -> ", e);
        return R.error("服务器异常!"+e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> runtimeExceptionHandler(RuntimeException e) {
        log.info("运行时异常：" + e.getMessage());
        e.printStackTrace();//后台输出具体异常
        return R.error("运行时异常"+e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        // 将错误信息拼接成字符串
        StringBuilder errorMessage = new StringBuilder("参数错误: ");
        errors.forEach((field, message) ->
                errorMessage.append(message));
        return R.error(errorMessage.toString());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("业务异常信息：{}", e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return R.error(StatusCodeEnum.VALUE_ERROR);
    }
}
