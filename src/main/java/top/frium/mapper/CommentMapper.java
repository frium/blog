package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.Comment;

/**
 *
 * @date 2024-06-20 17:40:25
 * @description
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
