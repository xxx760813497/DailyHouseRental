package com.xmj.demo.controller;

import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.service.CommentaryService;
import com.xmj.demo.service.ConsumerService;
import com.xmj.demo.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ConsumerService consumerService;

    @Autowired
    CommentaryService commentaryService;

    @Autowired
    MasterService masterService;

    @Resource
    UserMapper userMapper;


    @PostMapping("/updateStatus")
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

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestBody Map info, HttpSession session){
        String password2= (String) info.get("newPassword");
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password=bCryptPasswordEncoder.encode(password2);
        User user= (User) session.getAttribute("user");
        Integer id=user.getId();
        int row=consumerService.updateUserPassword(id,password);
        if (row>0){
            return "success";
        }else {
            return  "error";
        }
    }

    @GetMapping("/getUserState/{id}")
    public String getUserState(@PathVariable("id") Integer id,HttpSession session){
        if (session.getAttribute("user")!=null){
            User user= (User) session.getAttribute("user");
            House house=masterService.getHouseById(id);
            if (house.getUserId().equals(user.getId())){
                return "master";
            }else {
                return "noMaster";
            }
        }else {
            return "noLogin";
        }
    }


}
