package top.frium.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.ArticleMapper;
import top.frium.mapper.LabelMapper;
import top.frium.pojo.dto.ArticleDTO;
import top.frium.pojo.entity.Article;
import top.frium.pojo.entity.Label;
import top.frium.pojo.vo.ArticleListVO;
import top.frium.pojo.vo.ArticleVO;
import top.frium.service.ArticleService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void uploadArticle(ArticleDTO articleDTO) {
        try {
            Article article = new Article();
            BeanUtils.copyProperties(articleDTO, article);
            article.setCreateTime(LocalDateTime.now().format(DATA_TIME_PATTERN));
            save(article);
            if (articleDTO.getLabel() != null && !articleDTO.getLabel().isEmpty()) {
                List<Long> validLabelIds = articleDTO.getLabel().stream()
                        .filter(labelId -> articleMapper.checkLabelExists(labelId) != null)
                        .toList();

                Map<String, Object> params = new HashMap<>();
                params.put("articleId", article.getId());
                params.put("labelIds", validLabelIds);
                articleMapper.insertArticleLabels(params);
            }
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
        redisTemplate.delete("articleList:all");
    }

    @Override
    public boolean save(Article entity) {
        entity.setId(null);
        return super.save(entity);
    }

    @Override
    public ArticleVO getArticle(Long articleId) {
        Article article = getById(articleId);
        if (article == null) throw new MyException(StatusCodeEnum.NOT_FOUND);
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        articleVO.setLabel(articleMapper.getLabelsByArticleId(articleId));
        return articleVO;
    }

    @Override
    public void updateArticle(ArticleDTO articleDTO) {
        try {
            Article article = new Article();
            BeanUtils.copyProperties(articleDTO, article);
            articleMapper.deleteLabelsByArticleId(articleDTO.getId());
            if (articleDTO.getLabel() != null && !articleDTO.getLabel().isEmpty()) {
                Map<String, Object> params = new HashMap<>();
                params.put("articleId", articleDTO.getId());
                params.put("labelIds", articleDTO.getLabel());
                articleMapper.insertArticleLabels(params);
            }
            updateById(article);
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
        redisTemplate.delete("articleList:all");
    }

    @Override
    public void deleteArticle(List<Long> articleIds) {
        for (Long id : articleIds) {
            removeById(id);
            articleMapper.deleteLabelsByArticleId(id);
        }
        redisTemplate.delete("articleList:all");
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
        if (labelIds != null && !labelIds.isEmpty()) {
            articleMapper.deleteLabelRelations(labelIds);
            articleMapper.deleteLabel(labelIds);
        }
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

    @Override
    public List<ArticleListVO> getArticleList() {
        String redisKey = "articleList:all"; // 更改 Redis Key 为查询所有文章
        String cachedData = (String) redisTemplate.opsForValue().get(redisKey);
        if (cachedData != null) {
            return JSON.parseArray(cachedData, ArticleListVO.class); // 如果缓存存在，则直接返回缓存数据
        }

        // 查询所有文章
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        lambdaQuery().orderByDesc(Article::getIsTop)
                .orderByDesc(Article::getCreateTime);
        List<Article> articleList = articleMapper.selectList(queryWrapper); // 查询所有文章

        // 将文章列表转为 ArticleListVO 列表
        List<ArticleListVO> articleListVOList = articleList.stream()
                .map(article -> {
                    ArticleListVO articleListVO = new ArticleListVO();
                    BeanUtils.copyProperties(article, articleListVO);
                    return articleListVO;
                }).collect(Collectors.toList());

        // 将查询结果存入 Redis
        String jsonString = JSON.toJSONString(articleListVOList);
        redisTemplate.opsForValue().set(redisKey, jsonString, 1, TimeUnit.DAYS); // 设置过期时间为1天

        return articleListVOList;
    }

    @Override
    public List<ArticleListVO> getShowArticleList(Long pageNum) {
        if (pageNum == null) pageNum = 1L;
        String redisKey = "articleList:page:" + pageNum;
        String cachedData = (String) redisTemplate.opsForValue().get(redisKey);
        if (cachedData != null) {
            return JSON.parseArray(cachedData, ArticleListVO.class);
        }
        Page<Article> page = new Page<>(pageNum, 7);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        lambdaQuery().orderByDesc(Article::getIsTop)
                .orderByDesc(Article::getCreateTime);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<ArticleListVO> articleListVOList = articlePage.getRecords().stream()
                .map(article -> {
                    ArticleListVO articleListVO = new ArticleListVO();
                    BeanUtils.copyProperties(article, articleListVO);
                    return articleListVO;
                }).collect(Collectors.toList());

        // 3. 将查询结果存入 Redis
        String jsonString = JSON.toJSONString(articleListVOList);
        redisTemplate.opsForValue().set(redisKey, jsonString, 1, TimeUnit.DAYS); // 设置过期时间为1小时
        return articleListVOList;
    }

    @Override
    public void changeArticleShowStatus(Long articleId) {
        articleMapper.changeArticleShowStatus(articleId);
        redisTemplate.delete("articleList:all");
    }
}
