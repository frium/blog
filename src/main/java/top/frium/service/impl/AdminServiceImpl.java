package top.frium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.AdminMapper;
import top.frium.mapper.UserMapper;
import top.frium.pojo.dto.AdminDTO;
import top.frium.pojo.dto.UserPageDTO;
import top.frium.pojo.entity.Admin;
import top.frium.pojo.entity.User;
import top.frium.service.AdminService;
import top.frium.utils.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @date 2024-06-20 20:23:26
 * @description
 */

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    HttpServletResponse response;
    @Value("${jwt.key}")
    String key;
    @Value("${jwt.ttl}")
    Long ttl;
    @Override
    public void login(AdminDTO adminDTO) {
        Admin admin = lambdaQuery().eq(Admin::getUsername, adminDTO.getUsername()).eq(Admin::getPassword, adminDTO.getPassword()).one();
        if(admin==null) throw new MyException(StatusCodeEnum.USER_NOT_EXIST);
        Map<String,Object> claims= new HashMap<>();
        claims.put("adminId",admin.getId());
        response.setHeader("Authorization", JwtUtil.createToken(key,ttl,claims));
    }

    @Override
    public List<User> findAllUsers(UserPageDTO pageDTO) {
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(User::getUsername,pageDTO.getUsername()).like(User::getIp,pageDTO.getIp())
                .like(User::getAddress,pageDTO.getAddress()).orderByDesc(User::getLoginTime);
        return userMapper.selectPage(new Page<>(pageDTO.getPage(), 5),wrapper).getRecords();
    }

}
