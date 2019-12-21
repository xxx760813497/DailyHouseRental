package com.xmj.demo.controller;


import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Resource
    UserMapper userMapper;

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Map masterLogin(@RequestBody Map userInfo,HttpSession session){
        System.out.println("接收到login请求");
        String phonenum=(String) userInfo.get("phonenum");
        String password=(String) userInfo.get("password");
        Map result =new HashMap();
        if (phonenum==null||password==null||phonenum.length()==0||password.length()==0){
            result.put("msg","deficient");
            return result;
        }
        User user=userMapper.getUserByPhonenumAndPassword(phonenum,password);
        if (user!=null){
            result.put("msg","success");
            result.put("user",user.getId());
            result.put("status",user.getStatus());
            session.setAttribute("user",user);
        }else {
            result.put("msg","error");
        }
        return result;
    }




}
