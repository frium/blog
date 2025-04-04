package top.frium.pojo.vo;

import lombok.Data;

/**
 * @date 2025-04-04 14:01:31
 * @description
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String createTime;
    private String avatar;
    private Integer auth;
}
