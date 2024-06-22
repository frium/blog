package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.CommentDTO;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.LoginPhoneDTO;
import top.frium.pojo.entity.User;

/**
 *
 * @date 2024-06-19 18:58:10
 * @description
 */
public interface UserService extends IService<User> {

     void loginByPhone(LoginPhoneDTO loginPhoneDTO) ;

     void getPhoneSMS();


     void loginByEmail(LoginEmailDTO loginEmailDTO) ;

     void getEmailSMS(String email);

    void comment(CommentDTO commentDTO);
}
