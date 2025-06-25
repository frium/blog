package top.frium.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.EmailDTO;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.RegisterEmailDTO;
import top.frium.pojo.dto.UserDTO;
import top.frium.pojo.entity.User;
import top.frium.pojo.vo.LoginVO;
import top.frium.pojo.vo.UserInfoVO;
import top.frium.pojo.vo.UserVO;

import java.util.List;

/**
 *
 * @date 2024-07-29 23:30:08
 * @description
 */
public interface UserService extends IService<User> {
    void logout();
    void registerByEmail(RegisterEmailDTO registerEmailDTO);

    LoginVO loginByEmail(LoginEmailDTO loginEmailDTO);
    void getEmailCode(String email);

    UserInfoVO getUserInfo();

    void updateEmail(EmailDTO emailDTO );

    void updateUsername(String username);

    void createUser(UserDTO userDTO);


    void deleteUsers(List<Long> userIds);

    void updateUser(UserDTO userDTO);

    List<UserVO> getUsers();

    void updateAvatar(String url);

    List<UserVO>  searchUserByName(String searchInfo);
}
