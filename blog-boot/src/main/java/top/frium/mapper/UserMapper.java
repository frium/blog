package top.frium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.frium.pojo.entity.User;
import top.frium.pojo.vo.UserVO;

import java.util.List;

/**
 * @date 2024-07-29 23:29:39
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    void addUserPermission(Integer permission, Long userId);

    List<String> getUserPermission(Long id);

    void deleteUserPermissionByUserId(Long userId);
    void updateUserPermissionByUserId(Integer permission,Long userId );

    List<UserVO> getUserList();
}
