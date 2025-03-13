package top.frium.uitls;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    /**
     * 创建JWT Token
     *
     * @param secretKey 密钥
     * @param ttlMillis Token的有效期（毫秒）
     * @param claims 包含在JWT中的声明（Claim）信息
     * @return 生成的JWT Token
     */
    public static String createToken(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 计算Token的过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 创建JWT Builder
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims) // 设置JWT的声明
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8)) // 设置签名算法和密钥
                .setExpiration(exp); // 设置过期时间
        // 生成JWT Token并返回
        return builder.compact();
    }

    /**
     * 解析JWT Token
     *
     * @param secretKey 密钥
     * @param token 要解析的JWT Token
     * @return 解析后的Claims对象，包含了JWT中的声明信息
     */
    public static Claims parseToken(String secretKey, String token) {
        // 解析JWT Token，获取其中的Claims对象
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)) // 设置签名算法和密钥
                .parseClaimsJws(token) // 解析JWT Token
                .getBody(); // 获取JWT中的payload部分（Claims对象）
    }

}
