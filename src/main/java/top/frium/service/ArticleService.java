package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.PageDTO;
import top.frium.pojo.entity.Article;
import top.frium.pojo.vo.ArticleVO;

import java.util.List;

/**
 *
 * @date 2024-06-19 22:07:36
 * @description
 */
public interface ArticleService extends IService<Article> {
    List<ArticleVO> getArticles(PageDTO pageDTO);
    ArticleVO getArticleById(String id);

    List<ArticleVO> getArticleByLabel(String label);
}
