package top.frium.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @date 2024-06-19 19:11:15
 * @description
 */
@Data
@ApiModel("邮箱登录")
public class LoginEmailDTO {
    @Email(message = "邮箱格式有误")
    @ApiModelProperty(value = "邮箱",required = true)
    String email;

    @NotEmpty
    @Length(min = 6, max = 18, message = "密码长度应在6~18位之间")
    @ApiModelProperty(value = "密码", required = true)
    String password;
}
