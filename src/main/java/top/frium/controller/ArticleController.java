package top.frium.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.pojo.dto.ArticleDTO;
import top.frium.pojo.dto.PageDTO;
import top.frium.pojo.vo.ArticleVO;
import top.frium.service.ArticleService;

import java.util.List;

/**
 *
 * @date 2024-06-19 21:59:21
 * @description
 */
@Validated
@RestController("ArticleController")
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @ApiOperation("上传文章")
    @PostMapping("/admin/loadArticle")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String loadArticle(@RequestBody ArticleDTO articleDTO) {
        return null;
    }


    @ApiOperation("分页查询")
    @PostMapping("/getArticles")
    public R<List<ArticleVO>> getArticles(@RequestBody PageDTO pageDTO) {
        return R.success(articleService.getArticles(pageDTO));
    }

    @ApiOperation("查看单篇具体文章")
    @GetMapping("/getArticle")
    public R<ArticleVO> getArticle(String id) {
        return R.success(articleService.getArticleById(id));
    }

    @ApiOperation("根据标签查询文章")
    @PostMapping("/getArticleByLabel")
    public R<?> getArticleByLabel(String label){
        return R.success(articleService.getArticleByLabel(label));
    }
}
