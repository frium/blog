package top.frium.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @date 2024-04-23 21:40:56
 * @description
 */
@Data
public class Article implements Serializable {
    private Long id;
    private String imageUrl;
    private String title;
    private String introduction;
    private String content;
    private String createTime;
    private String updateTime;
}
