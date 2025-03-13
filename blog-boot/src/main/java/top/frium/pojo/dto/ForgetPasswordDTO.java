package top.frium.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @date 2024-07-31 17:01:06
 * @description
 */
@Data
public class ForgetPasswordDTO {
    @Email(message = "邮箱格式有误")
    @ApiModelProperty(value = "邮箱",required = true)
    String email;

    @NotEmpty
    @Length(min = 6, max = 18, message = "密码长度应在6~18位之间")
    @ApiModelProperty(value = "密码", required = true)
    String newPassword;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    String verify;
}
