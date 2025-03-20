package top.frium.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @date 2025-03-20 14:12:17
 * @description
 */
@Data
public class EmailDTO {
    @Email(message = "邮箱格式有误")
    @ApiModelProperty(value = "邮箱", required = true)
    String email;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    String verify;
}
