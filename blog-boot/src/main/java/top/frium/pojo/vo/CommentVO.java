package top.frium.pojo.vo;

import lombok.Data;

/**
 * @date 2025-04-15 17:19:20
 * @description
 */
@Data
public class CommentVO {
    private String commentContent;
    private String username;
    private String avatar;
    private String createTime;
}