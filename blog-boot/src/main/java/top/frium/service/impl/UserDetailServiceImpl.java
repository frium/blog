package top.frium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.UserMapper;
import top.frium.pojo.LoginUser;
import top.frium.pojo.entity.User;


import java.util.List;

/**
 *
 * @date 2024-07-30 15:08:23
 * @description
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, username);
        User user = userMapper.selectOne(wrapper);
        if (user == null) throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        List<String> list = userMapper.getUserPermission(user.getId());
        //把数据封装成UserDetails
        return new LoginUser(user, list);

    }
}
