package top.frium.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 *
 * @date 2024-06-19 18:45:02
 * @description
 */
@Data
public class ArticleDTO {
    private String content;
    private String imageUrl;
    private String title;
    private String introduction;
    private List<Integer> label;
}
