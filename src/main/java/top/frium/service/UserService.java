package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.*;
import top.frium.pojo.entity.User;

/**
 *
 * @date 2024-06-19 18:58:10
 * @description
 */
public interface UserService extends IService<User> {

    void loginByEmail(LoginEmailDTO loginEmailDTO) ;

     void registerByEmail(RegisterEmailDTO registerEmailDTO);


     void getEmailSMS(String email);

    void comment(CommentDTO commentDTO);
}
