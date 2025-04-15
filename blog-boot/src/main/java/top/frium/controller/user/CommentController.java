package top.frium.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.pojo.dto.CommentDTO;
import top.frium.pojo.entity.Article;
import top.frium.service.CommentService;

/**
 * @date 2025-04-15 17:06:16
 * @description
 */

@Api("评论")
@RestController
@RequestMapping("/user/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @ApiOperation("获取可见文章评论")
    @GetMapping("/getShowArticleComment")
    public R<?> getShowArticleComment(Long articleId) {
        return R.success(commentService.getShowArticleComment(articleId));
    }

    @ApiOperation("给可见文章评论")
    @PostMapping("/addComment")
    public R<?> addComment(@RequestBody CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return  R.success();
    }
}
