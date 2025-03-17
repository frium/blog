package top.frium.pojo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Article {
    private Long id;
    private String title;
    private String coverImg;
    private String summary;
    private Boolean isTop;
    private String createTime;
    private String content;
}