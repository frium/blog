package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.ArticleDTO;
import top.frium.pojo.entity.Article;
import top.frium.pojo.entity.Label;
import top.frium.pojo.vo.ArticleVO;

import java.util.List;

/**
 * @date 2025-03-17 19:52:23
 * @description
 */
public interface ArticleService extends IService<Article> {
    void uploadArticle(ArticleDTO articleDTO);

    String getArticleContent(Long articleId);

    void updateArticle(ArticleDTO articleDTO);

    void deleteArticle(List<Long> articleIds);

    List<Label> getLabels();

    void addLabel(String label);

    void deleteLabel(List<Long> labelIds);

    void updateLabel(Label label);

    List<ArticleVO> getArticleList(Long pageNum);

    void changeArticleShowStatus(Long articleId);
}
