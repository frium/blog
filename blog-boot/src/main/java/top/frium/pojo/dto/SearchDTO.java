package top.frium.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @date 2025-06-25 15:48:57
 * @description
 */
@Data
@ApiModel("分页查询")
public class SearchDTO {
    private  String searchInfo;
    private  Long pageNum;
}
