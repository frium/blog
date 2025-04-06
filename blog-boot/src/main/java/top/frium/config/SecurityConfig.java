package top.frium.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.frium.Filter.TokenFilter;

@Configuration // 标记此类为配置类，表示该类中包含了Spring的Bean定义
@EnableWebSecurity // 启用Spring Security的Web安全支持
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    TokenFilter tokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/user/**").permitAll() // 允许登录和注册请求
                        .anyRequest().authenticated()) // 其他所有请求都需要认证
                .formLogin(AbstractHttpConfigurer::disable)  // 禁用表单登录
                .csrf(AbstractHttpConfigurer::disable)  // 禁用 CSRF 保护
                .httpBasic(AbstractHttpConfigurer::disable)  // 禁用 HTTP Basic 认证
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);  // 添加自定义过滤器

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCryptPasswordEncoder进行密码加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 从AuthenticationConfiguration获取AuthenticationManager实例
        return authenticationConfiguration.getAuthenticationManager();
    }

}
