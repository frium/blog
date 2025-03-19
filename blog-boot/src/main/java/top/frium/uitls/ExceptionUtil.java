package top.frium.uitls;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import top.frium.common.R;
import top.frium.common.StatusCodeEnum;

import java.io.IOException;

/**
 *
 * @date 2024-08-10 19:08:41
 * @description
 */
@Component
public class ExceptionUtil {
    public void throwException(HttpServletResponse response, StatusCodeEnum statusCodeEnum) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(statusCodeEnum.getHttpStatusCode());
        response.getWriter().print(JSON.toJSON(R.error(statusCodeEnum)));
    }
}
