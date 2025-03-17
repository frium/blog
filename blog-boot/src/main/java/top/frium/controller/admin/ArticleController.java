package top.frium.controller.admin;

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

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @PostMapping("/uploadArticle")
    public R<?> uploadArticle(@RequestBody  ArticleDTO articleDTO) {
        articleService.uploadArticle(articleDTO);
        return R.success();
    }

    @PostMapping("/updateArticle")
    public R<?> updateArticle(@RequestBody  ArticleDTO articleDTO) {
        articleService.updateArticle(articleDTO);
        return R.success();
    }
    @GetMapping("/getArticle")
    public R<?> getArticle(Long articleId) {
        return R.success(articleService.getArticle(articleId));
    }

    @PostMapping("/deleteArticle")
    public R<?> deleteArticle(@RequestBody List<Long> articleIds) {
        articleService.deleteArticle(articleIds);
        return R.success();
    }

    @GetMapping("/getLabels")
    public R<?> getLabels() {
        return R.success(articleService.getLabels());
    }

    @PostMapping("/addLabel")
    public R<?> addLabel(@RequestParam String label) {
        articleService.addLabel(label);
        return R.success();
    }

    @PostMapping("/deleteLabel")
    public R<?> deleteLabel(@RequestBody List<Long> labelIds) {
        articleService.deleteLabel(labelIds);
        return R.success();
    }

    @PostMapping("/updateLabel")
    public R<?> updateLabel(@RequestBody Label label) {
        articleService.updateLabel(label);
        return R.success();
    }
}
