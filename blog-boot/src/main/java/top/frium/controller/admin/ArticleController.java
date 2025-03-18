package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.pojo.dto.ArticleDTO;
import top.frium.pojo.entity.Label;
import top.frium.service.ArticleService;

import java.util.List;

/**
 * @date 2025-03-17 19:58:28
 * @description
 */

@Api("文章管理")
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

    @ApiOperation("添加文章")
    @PostMapping("/uploadArticle")
    public R<?> uploadArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.uploadArticle(articleDTO);
        return R.success();
    }

    @ApiOperation("修改文章")
    @PostMapping("/updateArticle")
    public R<?> updateArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.updateArticle(articleDTO);
        return R.success();
    }

    @ApiOperation("获取文章")
    @GetMapping("/getArticle")
    public R<?> getArticle(Long articleId) {
        return R.success(articleService.getArticle(articleId));
    }

    @ApiOperation("删除文章")
    @PostMapping("/deleteArticle")
    public R<?> deleteArticle(@RequestBody List<Long> articleIds) {
        articleService.deleteArticle(articleIds);
        return R.success();
    }

    @ApiOperation("获取所有标签")
    @GetMapping("/getLabels")
    public R<?> getLabels() {
        return R.success(articleService.getLabels());
    }

    @ApiOperation("添加标签")
    @PostMapping("/addLabel")
    public R<?> addLabel(@RequestParam String label) {
        articleService.addLabel(label);
        return R.success();
    }

    @ApiOperation("删除标签")
    @PostMapping("/deleteLabel")
    public R<?> deleteLabel(@RequestBody List<Long> labelIds) {
        articleService.deleteLabel(labelIds);
        return R.success();
    }

    @ApiOperation("修改标签")
    @PostMapping("/updateLabel")
    public R<?> updateLabel(@RequestBody Label label) {
        articleService.updateLabel(label);
        return R.success();
    }

}
