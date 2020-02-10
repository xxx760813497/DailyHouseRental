package com.xmj.demo.controller.master;

import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import com.xmj.demo.service.CommentaryService;
import com.xmj.demo.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class MasterController {

    @Autowired
    MasterService masterService;

    @Autowired
    CommentaryService commentaryService;

    @GetMapping("/masterOrders")
    public ArrayList<Map> getMasterOrders(HttpSession session){
        User user= (User) session.getAttribute("user");
        Integer id=user.getId();
        ArrayList<Map> result=masterService.getOrderByUserId(id);
        if (result.size()>0){
            return result;
        }else {
            return null;
        }
    }

    @PostMapping("/masterOrder")
    public String updateOrderState(@RequestBody Map info){

        Integer id= (Integer) info.get("orderId");
        String orderState= (String) info.get("orderState");
        int row=masterService.updateOrderStateById(id,orderState);
        if (row>0){
            return "success";
        }else {
            return "failed";
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

    @PostMapping("/updateCommentaryReply")
    public String updateCommentaryReply(@RequestBody Map info){
        Integer commentaryId= (Integer) info.get("commentaryId");
        String content= (String) info.get("content");

        int row=commentaryService.updateCommentaryReply(commentaryId,content);

        if (row>0){
            return "success";
        }else {
            return "error";
        }
    }

    @PostMapping("/updateCommentaryAppeal")
    public String updateCommentaryAppeal(@RequestBody Map info){
        Integer commentaryId= (Integer) info.get("commentaryId");
        String content= (String) info.get("content");

        int row=commentaryService.updateCommentaryAppeal(commentaryId,content);

        if (row>0){
            return "success";
        }else {
            return "error";
        }
    }

}
