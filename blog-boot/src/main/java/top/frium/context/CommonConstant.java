package top.frium.context;

import java.time.format.DateTimeFormatter;

/**
 *
 * @date 2024-07-31 14:51:40
 * @description
 */
public interface CommonConstant {
    String LOGIN_USER = "loginUser";
    String USER_ID = "userId";
    DateTimeFormatter DATA_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
