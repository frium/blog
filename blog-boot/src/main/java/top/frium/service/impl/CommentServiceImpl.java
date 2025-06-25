package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.CommentMapper;
import top.frium.pojo.LoginUser;
import top.frium.pojo.dto.CommentDTO;
import top.frium.pojo.entity.Article;
import top.frium.pojo.entity.Comment;
import top.frium.pojo.vo.CommentAllDetailVO;
import top.frium.pojo.vo.CommentVO;
import top.frium.service.ArticleService;
import top.frium.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static top.frium.context.CommonConstant.DATA_TIME_PATTERN;

/**
 * @date 2025-04-15 17:18:18
 * @description
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;


    @Override
    public List<CommentVO> getShowArticleComment(Long articleId) {
        boolean exists = articleService.lambdaQuery()
                .eq(Article::getId, articleId)
                .eq(Article::getIsShow, true)
                .exists();
        if (!exists) throw new MyException(StatusCodeEnum.NOT_FOUND);
        return commentMapper.getArticleComment(articleId);
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        Long articleId = commentDTO.getArticleId();
        boolean exists = articleService.lambdaQuery()
                .eq(Article::getId, articleId)
                .eq(Article::getIsShow, true)
                .exists();
        if (!exists) throw new MyException(StatusCodeEnum.NOT_FOUND);
        Long userId;
        try {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = loginUser.getUser().getId();
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }
        Comment comment = new Comment(null, userId, articleId, commentDTO.getCommentContent(), LocalDateTime.now().format(DATA_TIME_PATTERN),false);
        save(comment);
    }

    @Override
    public List<CommentAllDetailVO> getAllComments() {
        return commentMapper.getAllComments();
    }

    @Override
    public void deleteComment( List<Long>  commentIds) {
        for (Long commentId : commentIds) {
            removeById(commentId);
        }
        Set<Object> keys = redisTemplate.keys("articleList*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void pauseComment(Integer commentId) {
        lambdaUpdate().eq(Comment::getId,commentId).set(Comment::getStatus,true).update();
        Set<Object> keys = redisTemplate.keys("articleList*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public List<CommentAllDetailVO> searchCommentByContent(String searchInfo) {
        return commentMapper.searchComments(searchInfo);
    }

}
