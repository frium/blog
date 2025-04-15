package top.frium.Filter;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.frium.pojo.LoginUser;
import top.frium.uitls.ExceptionUtil;
import top.frium.uitls.JwtUtil;

import java.io.IOException;
import java.util.Objects;

import static top.frium.common.StatusCodeEnum.NOT_LOGIN;
import static top.frium.common.StatusCodeEnum.NO_PERMISSION;
import static top.frium.context.CommonConstant.LOGIN_USER;
import static top.frium.context.CommonConstant.USER_ID;


@Component
@SuppressWarnings("all")
public class TokenFilter extends OncePerRequestFilter {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.name}")
    private String tokenName;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    ExceptionUtil exceptionUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if ((requestURI.contains("/user/")||requestURI.contains("/visit/") && !requestURI.contains("/user/getUserInfo")) && !requestURI.contains("/addComment")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(tokenName);

        if (token == null || token.isEmpty()) {
            exceptionUtil.throwException(response, NOT_LOGIN);
            return;
        }
        Long id;
        LoginUser loginUser = null;
        try {
            // 解析Token，获取其中的Claims对象
            Claims claims = JwtUtil.parseToken(secretKey, token);
            id = Long.valueOf(Objects.requireNonNull(claims.get(USER_ID)).toString());
            loginUser = (LoginUser) Objects.requireNonNull(redisTemplate.opsForValue().get(LOGIN_USER + id));
        } catch (Exception ex) {
            // 解析Token失败，抛出自定义业务异常
            exceptionUtil.throwException(response, NOT_LOGIN);
            return;
        }
        try {
            //存入SecurityContexHolder
            UsernamePasswordAuthenticationToken passwordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        } catch (Exception e) {
            exceptionUtil.throwException(response, NO_PERMISSION);
            return;
        }
        filterChain.doFilter(request, response);
        return;
    }
}
