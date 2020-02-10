package com.xmj.demo.controller.adminstrator;

import com.xmj.demo.entity.Commentary;
import com.xmj.demo.entity.House;
import com.xmj.demo.entity.Order;
import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.OrderMapper;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.service.AdminService;
import com.xmj.demo.service.CommentaryService;
import com.xmj.demo.service.MasterService;
import com.xmj.demo.tools.StringTransform;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    CommentaryService commentaryService;

    @Autowired
    MasterService masterService;

    @Resource
    OrderMapper orderMapper;



    @Resource
    UserMapper userMapper;

    @GetMapping("/reviewHouses")
    public ArrayList<House> getReviewHouses(){
        ArrayList<House> houses= adminService.getReviewHouses();
        //将后端的全文件路径转换为前端可以使用的路径
        for (House house :houses){
            house.setHouseTitleImg(StringTransform.filePathOfView(house.getHouseTitleImg()));
        }
        return houses;
    }

    @PutMapping("/reviewHouses")
    public String updateHouseState(@RequestBody Map houseInfo){
        int row=adminService.updateHouseStateById(houseInfo);
        if (row>0){
            return "ok";
        }else {
            return "error";
        }
    }

    @GetMapping("/reviewCommentaries")
    public ArrayList<Map> reviewCommentaries(){
        ArrayList<Commentary> commentaries=commentaryService.getRivewCommentaries();
        ArrayList<Map> results=new ArrayList<>();
        for (Commentary commentary:commentaries){

                Map result=new HashMap();
                User user=userMapper.getUserById(commentary.getUserId());
                String userPohone=user.getPhonenum();
                House house=masterService.getHouseById(commentary.getHouseId());
                Order order=orderMapper.getOrderById(commentary.getOrderId());

                result.put("id",commentary.getId());
                result.put("houseId",commentary.getHouseId());
                result.put("houseName",house.getName());
                result.put("orderDate",order.getBoardDate());
                result.put("orderPrice",order.getOrderPrice());


                result.put("userPhone",userPohone);
                result.put("content",commentary.getContent());
                result.put("fraction",commentary.getFraction());
                result.put("creatTime",commentary.getCreatTime());
                result.put("appeal",commentary.getAppeal());


                if (commentary.getImgs().indexOf(",")!=-1){
                    result.put("imgs",commentary.getImgs());
                }else {
                    result.put("imgs",StringTransform.filePathOfView(commentary.getImgs()));
                }
                results.add(result);

        }
        return results;
    }

    @PostMapping("/reviewCommentary")
    public String updateCommentaryStatus(@RequestBody Map info){
        Integer id= (Integer) info.get("id");
        String status= (String) info.get("status");
        int row=commentaryService.updateCommentaryStatus(id,status);
        if (row>0){
            return "success";
        }else {
            return "error";
        }
    }

    @DeleteMapping("/reviewCommentary/{id}")
    public String deleteCommentary(@PathVariable("id") Integer id){

        int row=adminService.deleteCommentaryById(id);
        if (row>0){
            return "success";
        }else {
            return "error";
        }
    }

}
