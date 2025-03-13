package top.frium.pojo.entity;

import lombok.Data;

/**
 *
 * @date 2024-07-29 23:18:34
 * @description
 */
@Data
public class User {
    private Long id;
    private String password;
    private String email;
}
