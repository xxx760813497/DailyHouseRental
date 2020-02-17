package com.xmj.demo.security;

import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService  implements UserDetailsService {

    @Resource
    UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("s值为："+s);
        User user=userMapper.getUserByPhoneNum(s);
        System.out.println("获取用户处理器触发："+user);
        if (user==null){
            throw new UsernameNotFoundException("用户名错误");
        }
        return user;
    }
}
