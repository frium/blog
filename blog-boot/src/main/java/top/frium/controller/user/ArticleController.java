package top.frium.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.frium.common.R;
import top.frium.service.ArticleService;

/**
 * @date 2025-03-20 20:46:10
 * @description
 */
@Api("获取文章")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @ApiOperation("获取文章数量")
    @GetMapping("/getArticleNum")
    public R<?> getArticleNum() {
        return R.success(articleService.count());
    }

    @ApiOperation("获取文章列表")
    @GetMapping("/getArticleList")
    public R<?> getArticleList(Long pageNum) {
        return R.success(articleService.getArticleList(pageNum));
    }

    @ApiOperation("获取文章内容")
    @GetMapping("/getArticleContent")
    public R<?> getArticleContent(Long articleId) {
        return R.success(articleService.getArticleContent(articleId));
    }
    @ApiOperation("获取所有标签")
    @GetMapping("/getLabels")
    public R<?> getLabels() {
        return R.success(articleService.getLabels());
    }
}
