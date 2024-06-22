package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.AdminDTO;
import top.frium.pojo.dto.UserPageDTO;
import top.frium.pojo.entity.Admin;
import top.frium.pojo.entity.User;

import java.util.List;

/**
 *
 * @date 2024-06-20 20:22:59
 * @description
 */
public interface AdminService extends IService<Admin> {
    void login(AdminDTO adminDTO);

    List<User> findAllUsers(UserPageDTO pageDTO);
}
