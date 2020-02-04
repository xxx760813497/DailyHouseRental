package com.xmj.demo.controller.consumer;


import com.xmj.demo.entity.Commentary;
import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.service.CommentaryService;
import com.xmj.demo.service.ConsumerService;
import com.xmj.demo.tools.LatLonUtil;
import com.xmj.demo.tools.StringTransform;
import com.xmj.demo.unitilEmitity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



@RestController
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @Autowired
    CommentaryService commentaryService;

    @Resource
    UserMapper userMapper;



    @PostMapping("/selectHouseByAddress")
    public ArrayList<House> selectHouseByAddress(@RequestBody Map info){
        String address= (String) info.get("address");
        int round= (int) info.get("round");
        ArrayList<String> dates= (ArrayList<String>) info.get("allDates");
        Point point=new Point(address);

        ArrayList<House> houses=consumerService.selectHouseByAddress(point,round,dates);
        for (House house:houses){
            house.setHouseTitleImg(StringTransform.filePathOfView(house.getHouseTitleImg()));
        }
        System.out.println("最终返回结果条数："+houses.size());
        return houses;
    }

    @PostMapping("/commentary")
    public String addCommentary(@RequestBody Map info,HttpSession session){
        User user= (User) session.getAttribute("user");
        Integer userId=user.getId();
        int row=commentaryService.addCommentary(info,userId);
        if (row>0){
            return "success";
        }else {
            return "faild";
        }
    }

    @GetMapping("/pionnerCommentary/{houseId}")
    public Map getPoinnerCommentary(@PathVariable ("houseId") Integer houseId){
        Commentary commentary=commentaryService.getPionnerCommentary(houseId);
        User user=userMapper.getUserById(commentary.getUserId());
        int commentaryNumber=commentaryService.getCommentaryNumber(houseId);
        Map result=new HashMap();
        String userPohone=user.getPhonenum().substring(0,3);
        userPohone+="******";
        userPohone+=user.getPhonenum().substring(9);

        result.put("userPhone",userPohone);
        result.put("content",commentary.getContent());
        result.put("fraction",commentary.getFraction());
        result.put("creatTime",commentary.getCreatTime());
        result.put("commentaryNumber",commentaryNumber);
        return result;
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

            if (commentary.getImgs().indexOf(",")!=-1){
                result.put("imgs",commentary.getImgs());
            }else {
                result.put("imgs",StringTransform.filePathOfView(commentary.getImgs()));
            }
            results.add(result);
        }

        return results;
    }
}
