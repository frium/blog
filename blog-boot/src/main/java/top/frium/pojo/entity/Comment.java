package top.frium.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2025-04-15 17:16:04
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    Long Id;
    Long userId;
    Long articleId;
    String commentContent;
    String createTime;
    Boolean status;

}
