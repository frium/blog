package top.frium.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @date 2025-03-20 16:11:43
 * @description
 */
@Data
@AllArgsConstructor
public class File {
    Long id;
    String url;
    String fileName;
    String createTime;
    Double size;
}
