package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.Comment;
import top.frium.pojo.vo.CommentVO;

import java.util.List;

/**
 * @date 2025-04-15 17:17:18
 * @description
 */
@Mapper
public interface CommentMapper  extends BaseMapper<Comment> {
    List<CommentVO> getArticleComment(Long articleId);

}
