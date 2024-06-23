package top.frium.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 * @date 2024-06-19 18:45:02
 * @description
 */
@Data
@ApiModel("添加文章")
public class ArticleDTO {
    @NotEmpty(message = "输入内容不能为空")
    @ApiModelProperty(value = "页数",required = true)
    private String content;

    @NotEmpty(message = "封面图片地址")
    @ApiModelProperty(value = "封面图片地址",required = true)
    private String imageUrl;

    @NotEmpty(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题",required = true)
    private String title;

    @NotEmpty(message = "文章简介不能为空")
    @ApiModelProperty(value = "文章简介",required = true)
    private String introduction;

    @NotNull
    @ApiModelProperty(value = "文章标签",required = true)
    private List<Integer> label;
}
