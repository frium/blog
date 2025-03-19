package top.frium.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.RegisterEmailDTO;
import top.frium.pojo.entity.User;
import top.frium.pojo.vo.LoginVO;

/**
 *
 * @date 2024-07-29 23:30:08
 * @description
 */
public interface UserService extends IService<User> {

    void registerByEmail(RegisterEmailDTO registerEmailDTO);

    LoginVO loginByEmail(LoginEmailDTO loginEmailDTO);
    void getEmailCode(String email);
}
