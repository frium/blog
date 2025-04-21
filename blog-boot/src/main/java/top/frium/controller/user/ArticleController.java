package top.frium.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.frium.common.R;
import top.frium.mapper.LabelMapper;
import top.frium.pojo.entity.Article;
import top.frium.service.ArticleService;

/**
 * @date 2025-03-20 20:46:10
 * @description
 */
@Api("获取文章")
@RestController
@RequestMapping("/user/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    LabelMapper labelMapper;


    @ApiOperation("获取可见文章数量")
    @GetMapping("/getShowArticleNum")
    public R<?> getShowArticleNum() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        return R.success(articleService.count(queryWrapper.eq(Article::getIsShow, true)));
    }

    @ApiOperation("获取可见文章列表")
    @GetMapping("/getShowArticleList")
    public R<?> getArticleList(Long pageNum) {
        return R.success(articleService.getShowArticleList(pageNum));
    }

    @ApiOperation("获取文章内容")
    @GetMapping("/getArticle")
    public R<?> getArticle(Long articleId) {
        return R.success(articleService.getArticle(articleId));
    }

    @ApiOperation("获取所有标签")
    @GetMapping("/getLabels")
    public R<?> getLabels() {
        return R.success(labelMapper.selectLabelsWithArticleCount());
    }
    @ApiOperation("获取标签数量")
    @GetMapping("/getLabelNum")
    public R<?> getLabelNum() {
        return R.success(labelMapper.selectCount(null));
    }

    @ApiOperation("获取归档")
    @GetMapping("/getArticleByTime")
    public R<?> getArticleByTime() {
        return R.success(articleService.getArticleByTime());
    }
}
