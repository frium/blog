package top.frium.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 *
 * @date 2024-06-20 22:44:20
 * @description
 */
@Data
public class UserPageDTO {
    @Min(value = 1,message = "最小从第一页展示")
    @ApiModelProperty(value = "页数",required = true)
    Integer page;

    private String username;
    private String ip;
    private String address;
}
