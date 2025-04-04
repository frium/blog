package top.frium.pojo.dto;

import lombok.Data;

/**
 * @date 2025-04-04 11:34:03
 * @description
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer Permission;
}
