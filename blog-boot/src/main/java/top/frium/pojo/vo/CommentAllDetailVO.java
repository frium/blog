package top.frium.pojo.vo;

import lombok.Data;

/**
 * @date 2025-04-16 21:16:04
 * @description
 */
@Data
public class CommentAllDetailVO {
    private Long Id;
    private String username;
    private String email;
    private String avatar;
    private String articleTitle;
    private String commentContent;
    private String createTime;
    private Boolean status;

}
