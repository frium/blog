package top.frium.pojo.vo;

import lombok.Data;
import top.frium.pojo.entity.Label;

import java.util.List;

/**
 * @date 2025-03-22 22:08:19
 * @description
 */
@Data
public class ArticleVO {
    private Long id;
    private String title;
    private List<Label> label;
    private String coverImg;
    private String summary;
    private String content;
    private Boolean isShow;
    private Boolean isTop;
    private String createTime;
    private Long viewNum;
    private Long commentNum;
    private String prevArticleName;
    private Long prevArticleId;
    private String nextArticleName;
    private Long nextArticleId;
}
