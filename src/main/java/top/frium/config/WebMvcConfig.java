package top.frium.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.frium.interceptor.TokenInterceptor;

/**
 *
 * @date 2024-05-20 21:05:20
 * @description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")//添加拦截路径
                .excludePathPatterns("/*/login","/*/article")
                .order(100);
    }
}
