package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.Article;
import top.frium.pojo.entity.Label;
import top.frium.pojo.vo.ArticleListVO;

import java.util.List;
import java.util.Map;

/**
 * @date 2025-03-17 19:51:33
 * @description
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    void insertArticleLabels(Map<String, Object> params);
    void changeArticleShowStatus(Long articleId);

    void deleteLabelsByArticleId(Long articleId);

    void deleteLabelRelations(List<Long> labelIds);

    void deleteLabel(List<Long> labelIds);

    Integer checkLabelExists(Long labelId);
    List<Label> getLabelsByArticleId(Long articleId);
    List<ArticleListVO> searchArticles(String searchInfo, long offset);
    Long countSearchArticles(String searchInfo);
}
