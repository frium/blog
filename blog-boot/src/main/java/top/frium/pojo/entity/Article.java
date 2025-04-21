package top.frium.pojo.entity;

import lombok.Data;

@Data
public class Article {
    private Long id;
    private String title;
    private String coverImg;
    private String summary;
    private Boolean isTop;
    private Boolean isShow;
    private String createTime;
    private String content;
    private Long viewNum;
}