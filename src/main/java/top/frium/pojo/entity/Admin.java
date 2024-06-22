package top.frium.pojo.entity;

import lombok.Data;

/**
 *
 * @date 2024-06-20 20:20:04
 * @description
 */
@Data
public class Admin {
    private Long id;
    private String username;
    private String password;
    private boolean power;
}
