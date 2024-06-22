package top.frium.pojo.vo;

import lombok.Data;
import top.frium.pojo.entity.Comment;

import java.util.List;

/**
 *
 * @date 2024-06-19 22:11:13
 * @description
 */
@Data
public class ArticleVO {
    private String imageUrl;
    private String title;
    private String introduction;
    private String content;
    private String createTime;
    private Integer wordage;
    private List<String> label;
    private List<Comment> comment;
}
