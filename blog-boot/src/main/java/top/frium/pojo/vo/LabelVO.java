package top.frium.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @date 2025-04-07 13:58:02
 * @description
 */
@Data
@AllArgsConstructor
public class LabelVO {
    Long id;
    String labelName;
    String createTime;
    Long articleCount;
}