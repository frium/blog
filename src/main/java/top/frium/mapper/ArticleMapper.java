package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.Article;

import java.util.List;

/**
 *
 * @date 2024-06-19 22:07:03
 * @description
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article>getArticleByLabel(String labelName);
}
