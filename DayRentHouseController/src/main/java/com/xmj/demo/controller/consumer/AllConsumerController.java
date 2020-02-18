package com.xmj.demo.controller.consumer;

import com.xmj.demo.entity.Commentary;
import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.service.CommentaryService;
import com.xmj.demo.service.ConsumerService;
import com.xmj.demo.tools.StringTransform;
import com.xmj.demo.unitilEmitity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class AllConsumerController {
    @Autowired
    ConsumerService consumerService;

    @Autowired
    CommentaryService commentaryService;

    @Resource
    UserMapper userMapper;


    @PostMapping("/commentaryImg")
    public String titleImgUpload (@RequestParam("file") MultipartFile file, HttpSession session){

        if (file.isEmpty()){
            return "error";
        }
        User user= (User) session.getAttribute("user");
        Integer userId=user.getId();
        String filePath=CommentaryImgUtil.fileUpload(file,userId,"CommentaryImg");
        System.out.println("上传路径："+filePath);
        return  filePath;
    }

    @PostMapping("/deleteCommentaryImg")
    public String deleteHouseTitleImg(@RequestParam("filePath") String filePath){
        System.out.println("删除路径:"+filePath);
        if (CommentaryImgUtil.deleteFile(filePath)){
            return "success";
        }else {
            return "error";
        }
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

    @PostMapping("/consumerOrder")
    public String addOrder(@RequestBody Map info, HttpSession session){
        System.out.println(info);
        User user= (User) session.getAttribute("user");
        Integer userId=user.getId();
        Integer houseId= (Integer) info.get("houseId");
        ArrayList dates= (ArrayList) info.get("dates");
        Integer orderPrice= (Integer) info.get("expense");
        String orderUser= (String) info.get("orderUser");
        String orderUserTel= (String) info.get("orderUserTel");
        int row=consumerService.addOrder(userId,houseId,dates,orderUser,orderUserTel,orderPrice);
        if (row>0) {
            return "ok";
        }
        else {
            return "failed";
        }
    }

    @DeleteMapping("/consumerOrder/{id}")
    public String deleteOrder(@PathVariable("id") Integer id){
        int row=consumerService.deleteOrderById(id);
        if (row>0){
            return "success";
        }else {
            return "failed";
        }
    }

    @GetMapping("/consumerOrders")
    public ArrayList<Map> getOrderByUserId(HttpSession session){

        User user= (User) session.getAttribute("user");
        Integer userId=user.getId();
        ArrayList<Map> result=consumerService.getOrderByUserId(userId);
        if (result.size()>0){
            return  result;
        }else {
            return  null;
        }
    }

    @PutMapping("/consumerOrder")
    public String updateOrderState(@RequestBody Map info){

        Integer id= (Integer) info.get("orderId");
        String orderState= (String) info.get("orderState");
        int row=consumerService.updateOrderStateById(id,orderState);
        if (row>0){
            return "success";
        }else {
            return "failed";
        }
    }



}
