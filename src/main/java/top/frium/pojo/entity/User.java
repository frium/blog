package top.frium.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @date 2024-04-23 19:06:13
 * @description
 */
@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String ip;
    private String password;
    private String address;
    private String phone;
    private String email;
    private String loginTime;
}
