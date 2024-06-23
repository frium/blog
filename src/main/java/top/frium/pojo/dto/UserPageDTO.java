package top.frium.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @date 2024-06-20 22:44:20
 * @description
 */
@Data
@ApiModel("用户分页查询")
public class UserPageDTO {
    @Min(value = 1,message = "最小从第一页展示")
    @ApiModelProperty(value = "页数",required = true)
    Integer page;

    @NotNull
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotNull
    @ApiModelProperty(value = "ip",required = true)
    private String ip;

    @NotNull
    @ApiModelProperty(value = "ip地址",required = true)
    private String address;
}
