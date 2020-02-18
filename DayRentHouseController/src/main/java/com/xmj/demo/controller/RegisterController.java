package com.xmj.demo.controller;


import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.redis.HouseRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterController {

    @Resource
    private UserMapper userMapper;

    @Autowired
    HouseRedis houseRedis;

    @PostMapping("/register")
    public Map register(@RequestBody Map userInfo){
        System.out.println("接收注册到请求");
        String phonenum=(String) userInfo.get("phonenum");
        String password2=(String) userInfo.get("password");
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password=bCryptPasswordEncoder.encode(password2);
        Map result =new HashMap();
        if (phonenum==null||password==null||phonenum.length()==0||password.length()==0){
            result.put("msg","deficient");
            return result;
        }
        String phonenumExist=userMapper.selectUserPhonenum(phonenum);

        if (phonenumExist!=null){
            result.put("msg","phonenumExist");
            return result;
        }

        User user=new User();
        user.setPhonenum(phonenum);
        user.setPassword(password);

        int row=userMapper.insertNewUser(user);

        if (row>0){
            result.put("msg","success");
        }else {
            result.put("msg","failed");
        }
        return result;
    }


}
