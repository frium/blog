package top.frium.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.context.BaseContext;
import top.frium.utils.JwtUtil;

import java.util.Objects;

/**
 *
 * @date 2024-06-20 19:34:25
 * @description
 */
@Component
@SuppressWarnings("all")
public class TokenInterceptor implements HandlerInterceptor {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.name}")
    private String tokenName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        try {
            String token = request.getHeader(tokenName);
            // 解析Token，获取其中的Claims对象
            Claims claims = JwtUtil.parseToken(secretKey, token);
            Object id = claims.get("userId");
            BaseContext.setCurrentId(Long.valueOf(Objects.requireNonNull(id).toString()));
            return true;
        } catch (Exception ex) {
            // 解析Token失败，抛出自定义业务异常
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }

    }
}
