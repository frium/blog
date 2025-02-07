package top.frium.blogboot.interceptor;

import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

import static top.frium.blogboot.uitls.IpUtil.getIpAddress;
import static top.frium.blogboot.uitls.IpUtil.getIpSource;


/**
 *
 * @date 2024-06-14 17:48:43
 * @description
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class IpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String ipAddress = "-";
        String ipSource = "-";
        String desc = "-";
        String pre = "-";
        String api = "-";
        try {
            ipAddress = getIpAddress(request);
            ipSource = getIpSource(ipAddress);
            Method method = ((HandlerMethod) handler).getMethod();
            if (method != null) api = method.getName();
            else log.error("method为null");
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            if (annotation != null) desc = annotation.value();
            else log.error("methodAnnotation为null");
            RequestMapping classAnnotation = method.getDeclaringClass().getAnnotation(RequestMapping.class);
            if (classAnnotation != null) pre = classAnnotation.value()[0];
            else log.error("classAnnotation为null");
        } catch (Exception ignore) {
        } finally {
            log.info("{},访问 {}/{},接口名 {},地址 {}", ipAddress, pre, api, desc, ipSource);
        }
        return true;
    }
}
