package top.frium.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @date 2024-06-19 18:42:49
 * @description
 */
@Data
@ApiModel("分页查询")
public class PageDTO {
    @NotEmpty
    @Min(value = 1,message = "最小从第一页展示")
    @ApiModelProperty(value = "页数",required = true)
    Integer page;

    @NotNull
    @ApiModelProperty("标题")
    String title;
}
