package top.frium.pojo.vo;

import lombok.Data;
import top.frium.pojo.entity.Label;

import java.util.List;

/**
 * @date 2025-04-07 22:47:21
 * @description
 */
@Data
public class ArticleByTimeVO {
    private Long id;
    private String coverImg;
    private String summary;
    private String createTime;
    private String title;
}
