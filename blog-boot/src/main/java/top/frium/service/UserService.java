package top.frium.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.RegisterEmailDTO;
import top.frium.pojo.entity.User;

/**
 *
 * @date 2024-07-29 23:30:08
 * @description
 */
public interface UserService extends IService<User> {

    void registerByEmail(RegisterEmailDTO registerEmailDTO);

    String loginByEmail(LoginEmailDTO loginEmailDTO);
    void getEmailCode(String email);
}
