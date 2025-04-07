package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.Label;
import top.frium.pojo.vo.LabelVO;

import java.util.List;

/**
 * @date 2025-03-17 20:49:22
 * @description
 */
@Mapper
public interface LabelMapper extends BaseMapper<Label> {
    List<LabelVO> selectLabelsWithArticleCount();
}
