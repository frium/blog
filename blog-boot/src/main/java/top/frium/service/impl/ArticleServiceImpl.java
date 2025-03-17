package top.frium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.ArticleMapper;
import top.frium.mapper.LabelMapper;
import top.frium.pojo.dto.ArticleDTO;
import top.frium.pojo.entity.Article;
import top.frium.pojo.entity.Label;
import top.frium.pojo.vo.ArticleVO;
import top.frium.service.ArticleService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static top.frium.context.CommonConstant.DATA_TIME_PATTERN;

/**
 * @date 2025-03-17 19:52:52
 * @description
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void uploadArticle(ArticleDTO articleDTO) {
        try {
            Article article = new Article();
            BeanUtils.copyProperties(articleDTO, article);
            article.setCreateTime(LocalDateTime.now().format(DATA_TIME_PATTERN));
            save(article);
            Map<String, Object> params = new HashMap<>();
            params.put("articleId", article.getId());
            params.put("labelIds", articleDTO.getLabel());
            articleMapper.insertArticleLabels(params);
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
    }

    @Override
    public boolean save(Article entity) {
        entity.setId(null);
        return super.save(entity);
    }

    @Override
    public ArticleVO getArticle(long articleId) {
        ArticleVO articleVO;
        Article article = getById(articleId);
        if (article == null) throw new MyException(StatusCodeEnum.NOT_FOUND);
        try {
            articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            articleVO.setLabel(articleMapper.getLabelsByArticleId(articleId));
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
        return articleVO;

    }

    @Override
    public void updateArticle(ArticleDTO articleDTO) {
        try {
            Article article = new Article();
            BeanUtils.copyProperties(articleDTO, article);
            articleMapper.deleteLabelsByArticleId(articleDTO.getId());
            Map<String, Object> params = new HashMap<>();
            params.put("articleId", articleDTO.getId());
            params.put("labelIds", articleDTO.getLabel());
            articleMapper.insertArticleLabels(params);
            updateById(article);
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
    }

    @Override
    public void deleteArticle(List<Long> articleIds) {
        for (Long id : articleIds) {
            removeById(id);
            articleMapper.deleteLabelsByArticleId(id);
        }
    }

    @Override
    public List<Label> getLabels() {
        return labelMapper.selectList(null);
    }

    @Override
    public void addLabel(String label) {
        Long count = labelMapper.selectCount(new LambdaQueryWrapper<Label>()
                .eq(Label::getLabelName, label));
        if (count == 0) labelMapper.insert(new Label(0, label));
        else throw new MyException(StatusCodeEnum.LABEL_EXIST);
    }

    @Override
    public void deleteLabel(List<Long> labelIds) {
        if (labelIds != null && !labelIds.isEmpty()) labelMapper.deleteBatchIds(labelIds);
    }

    @Override
    public void updateLabel(Label label) {
        if (label == null) throw new MyException(StatusCodeEnum.VALUE_ERROR);
        Long count = labelMapper.selectCount(new LambdaQueryWrapper<Label>()
                .eq(Label::getLabelName, label.getLabelName()));
        if (count == 0) {
            labelMapper.updateById(label);
        } else throw new MyException(StatusCodeEnum.LABEL_EXIST);


    }

}
