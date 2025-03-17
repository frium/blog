package top.frium.pojo.vo;

import lombok.Data;
import top.frium.pojo.entity.Label;

import java.util.List;

@Data
public class ArticleVO {
    private Long id;
    private String title;
    private List<Label> label;
    private String coverImg;
    private String summary;
    private Boolean isTop;
    private String createTime;
    private String content;
}