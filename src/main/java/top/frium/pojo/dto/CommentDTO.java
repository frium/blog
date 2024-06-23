package top.frium.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @date 2024-06-20 17:19:52
 * @description
 */
@Data
@ApiModel("评论")
public class CommentDTO {
    @NotEmpty
    @Min(value = 1, message = "文章编号有误")
    @ApiModelProperty(value = "评论的文章id",required = true)
    private Long articleId;

    @NotEmpty(message = "输入内容不能为空")
    @ApiModelProperty(value = "评论的内容",required = true)
    private String content;
}
