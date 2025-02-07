package top.frium.blogboot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.blogboot.pojo.dto.ForgetPasswordDTO;
import top.frium.blogboot.pojo.dto.LoginEmailDTO;
import top.frium.blogboot.pojo.dto.RegisterEmailDTO;
import top.frium.blogboot.pojo.entity.User;

/**
 *
 * @date 2024-07-29 23:30:08
 * @description
 */
public interface UserService extends IService<User> {

    void registerByEmail(RegisterEmailDTO registerEmailDTO);

    String loginByEmail(LoginEmailDTO loginEmailDTO);

    void forgetPassword(ForgetPasswordDTO forgetPasswordDTO);
}
