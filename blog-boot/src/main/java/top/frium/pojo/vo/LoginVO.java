package top.frium.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @date 2025-03-19 22:09:58
 * @description
 */
@Data
@AllArgsConstructor
public class LoginVO {
    private String jwt;
    private String userAuth;
}
