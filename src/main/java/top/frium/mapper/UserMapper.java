package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.User;

/**
 *
 * @date 2024-06-19 18:58:41
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    void  addUserPower(Long userId,Long roleTypeId);
    String  getUserPower(Long userId);
}
