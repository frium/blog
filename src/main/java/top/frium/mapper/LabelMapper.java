package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.Label;

import java.util.List;

/**
 *
 * @date 2024-06-20 15:17:37
 * @description
 */
@Mapper
public interface LabelMapper extends BaseMapper<Label> {
    List<String> getArticleLabels(long articleId);
    List<String> getAllLabel();
    List<String> getLabelsNumber();
}
