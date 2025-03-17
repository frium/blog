package top.frium.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @date 2025-03-17 19:49:07
 * @description
 */
@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private List<Long> label;
    private String coverImg;
    private String summary;
    private Boolean isTop;
    private String content;
}
