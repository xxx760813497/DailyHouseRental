package com.xmj.demo.controller;

import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FirstLoginController {

    @Resource
    UserMapper userMapper;

    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Map firstLogin(@RequestBody String info, HttpSession session){

        User user = (User) session.getAttribute("user");
        int row =userMapper.updateUserStatus(user.getId(),info);

        Map result =new HashMap();
        if (row>0){
            result.put("msg","success");
        }else {
            result.put("msg","error");
        }
        return result;
    }
}
