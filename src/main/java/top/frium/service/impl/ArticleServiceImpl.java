package top.frium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.ArticleMapper;
import top.frium.mapper.CommentMapper;
import top.frium.mapper.LabelMapper;
import top.frium.pojo.dto.PageDTO;
import top.frium.pojo.entity.Article;
import top.frium.pojo.entity.Comment;
import top.frium.pojo.vo.ArticleVO;
import top.frium.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @date 2024-06-19 22:08:13
 * @description
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<ArticleVO> getArticles(PageDTO pageDTO) {
        List<Article> articles = lambdaQuery().like(Article::getTitle, pageDTO.getTitle()).page(new Page<>(pageDTO.getPage(), 5)).getRecords();
        if (articles == null) throw new MyException(StatusCodeEnum.NOT_FOUND);
        List<ArticleVO> articleVOS = new ArrayList<>();
        //查询article对应的label
        for (Article article : articles) {
            articleVOS.add(getArticleVO(article));
        }
        return articleVOS;
    }

    @Override
    public ArticleVO getArticleById(String id) {
        Article article = lambdaQuery().eq(Article::getId, id).one();
        if (article == null) throw new MyException(StatusCodeEnum.NOT_FOUND);
        return getArticleVO(article);
    }

    @Override
    public List<ArticleVO> getArticleByLabel(String labelName) {
        List<Article> articles = articleMapper.getArticleByLabel(labelName);
        if (articles == null) throw new MyException(StatusCodeEnum.NOT_FOUND);
        List<ArticleVO> articleVOS = new ArrayList<>();
        for (Article article : articles) {
            articleVOS.add(getArticleVO(article));
        }
        return articleVOS;
    }
    public ArticleVO getArticleVO(Article article){
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        articleVO.setWordage(articleVO.getContent().length());
        articleVO.setLabel(labelMapper.getArticleLabels(article.getId()));
        articleVO.setComment(commentMapper.selectList(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getArticleId, article.getId())));
        return articleVO;
    }

}
