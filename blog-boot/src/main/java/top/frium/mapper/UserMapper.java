package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.User;

import java.util.List;

/**
 * @date 2024-07-29 23:29:39
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    void addUserPermission(Integer Permission, Long id);

    List<String> getUserPermission(Long id);

    void deleteUserPermissionByUserId(Long userId);
    void updateUserPermissionByUserId(Integer Permission,Long userId );
}
