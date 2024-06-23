package top.frium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.UserMapper;
import top.frium.pojo.entity.LoginUser;
import top.frium.pojo.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @date 2024-06-23 16:36:31
 * @description
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        //TODO 查询权限
        List<String> list = new ArrayList<>(List.of(userMapper.getUserPower(user.getId())));
        //把数据封装成UserDetails
        return new LoginUser(user, list);
    }
}
