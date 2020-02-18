package com.xmj.demo.controller;

import com.xmj.demo.entity.Commentary;
import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.service.CommentaryService;
import com.xmj.demo.service.ConsumerService;
import com.xmj.demo.service.MasterService;
import com.xmj.demo.tools.StringTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/all")
public class PublicController {
    @Autowired
    ConsumerService consumerService;

    @Autowired
    CommentaryService commentaryService;

    @Autowired
    MasterService masterService;

    @Resource
    UserMapper userMapper;

    @GetMapping("/pionnerCommentary/{houseId}")
    public Map getPoinnerCommentary(@PathVariable("houseId") Integer houseId){
        Commentary commentary=commentaryService.getPionnerCommentary(houseId);

        Map result=new HashMap();
        if (commentary!=null){
            int commentaryNumber=commentaryService.getCommentaryNumber(houseId);

            User user=userMapper.getUserById(commentary.getUserId());
            String userPohone=user.getPhonenum().substring(0,3);
            userPohone+="******";
            userPohone+=user.getPhonenum().substring(9);
            result.put("userPhone",userPohone);
            result.put("content",commentary.getContent());
            result.put("fraction",commentary.getFraction());
            result.put("creatTime",commentary.getCreatTime());
            result.put("commentaryNumber",commentaryNumber);
            return result;
        }else {
            result.put("commentaryNumber",0);
            return result;
        }
    }

    @GetMapping("/commentarys/{houseId}")
    public ArrayList<Map> getCommentaries(@PathVariable("houseId") Integer houseId){
        ArrayList<Map> results =new ArrayList<>();
        ArrayList<Commentary> commentaries=commentaryService.getCommentaryByHouseId(houseId);

        for (Commentary commentary:commentaries){
            Map result=new HashMap();
            User user=userMapper.getUserById(commentary.getUserId());
            String userPohone=user.getPhonenum().substring(0,3);
            userPohone+="******";
            userPohone+=user.getPhonenum().substring(9);
            result.put("id",commentary.getId());
            result.put("userPhone",userPohone);
            result.put("content",commentary.getContent());
            result.put("fraction",commentary.getFraction());
            result.put("creatTime",commentary.getCreatTime());
            result.put("reply",commentary.getReply());
            result.put("isRead",commentary.getIsRead());
            result.put("appeal",commentary.getAppeal());
            result.put("appealStatus",commentary.getAppealStatus());
            if (commentary.getImgs().indexOf(",")!=-1){
                result.put("imgs",commentary.getImgs());
            }else {
                result.put("imgs",StringTransform.filePathOfView(commentary.getImgs()));
            }
            results.add(result);
        }
        return results;
    }

    @GetMapping("/userPhone")
    public String getUserPhone(HttpSession session){
        User user= (User) session.getAttribute("user");
        return user.getPhonenum();
    }

    @GetMapping("/house/{id}")
    public House getHosueById(@PathVariable("id") Integer id){


        House house=masterService.getHouseById(id);


        if (house!=null){
            return house;
        }else {
            return  null;
        }
    }



}
