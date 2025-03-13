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
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.pojo.LoginUser;
import top.frium.uitls.JwtUtil;


import java.io.IOException;
import java.util.Objects;


@Component
@SuppressWarnings("all")
public class TokenFilter extends OncePerRequestFilter {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.name}")
    private String tokenName;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(tokenName);
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        Long id;
        LoginUser loginUser = null;
        try {
            // 解析Token，获取其中的Claims对象
            Claims claims = JwtUtil.parseToken(secretKey, token);
            id = Long.valueOf(Objects.requireNonNull(claims.get("userId")).toString());
            loginUser  = (LoginUser) redisTemplate.opsForValue().get("loginUser" + id);
        } catch (Exception ex) {
            // 解析Token失败，抛出自定义业务异常
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }
        //存入SecurityContexHolder
        UsernamePasswordAuthenticationToken passwordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        filterChain.doFilter(request, response);
        return;
    }
}
