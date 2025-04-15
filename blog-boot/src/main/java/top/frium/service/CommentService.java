package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.CommentDTO;
import top.frium.pojo.entity.Comment;
import top.frium.pojo.entity.File;
import top.frium.pojo.vo.CommentVO;

import java.util.List;

/**
 * @date 2025-04-15 17:17:54
 * @description
 */
public interface CommentService extends IService<Comment> {
    List<CommentVO> getShowArticleComment(Long articleId);
    void addComment(CommentDTO commentDTO);
}
