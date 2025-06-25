package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.service.CommentService;

import java.util.List;

/**
 * @date 2025-04-16 21:08:44
 * @description
 */
@Api("评论管理")
@RestController
@RequestMapping("/adminComment")
@PreAuthorize("hasAuthority('superAdmin')")
public class AdminCommentController {

    @Autowired
    CommentService commentService;
    @ApiOperation("获取所有评论")
    @GetMapping("/getAllComment")
    public R<?> getAllComment() {
        return R.success(commentService.getAllComments());
    }

    @ApiOperation("删除评论")
    @PostMapping("/deleteComment")
    public R<?> deleteComment(@RequestBody List<Long>  commentId) {
        commentService.deleteComment(commentId);
        return  R.success();
    }

    @ApiOperation("通过评论")
    @PostMapping("/passComment")
    public R<?> pauseComment(Integer commentId) {
        commentService.pauseComment(commentId);
        return  R.success();
    }

    @ApiOperation("获取所有评论数量")
    @GetMapping("/getCommentNum")
    public R<?> getCommentNum() {
        return R.success(commentService.count());
    }

    @ApiOperation("根据内容搜索评论")
    @PostMapping("/searchCommentByContent")
    public R<?> searchCommentByContent( String searchInfo) {
        return R.success(commentService.searchCommentByContent(searchInfo));
    }
}
