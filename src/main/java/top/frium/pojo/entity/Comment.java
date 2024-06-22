package top.frium.pojo.entity;

import lombok.Data;

/**
 *
 * @date 2024-06-20 17:21:50
 * @description
 */
@Data
public class Comment {
    private Long id;
    private Long articleId;
    private String username;
    private String content;
    private String ip;
    private String address;
    private String time;
}
