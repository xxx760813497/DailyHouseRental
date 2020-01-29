package com.xmj.demo.controller.consumer;

import com.xmj.demo.entity.Order;
import com.xmj.demo.entity.User;
import com.xmj.demo.service.ConsumerService;
import com.xmj.demo.tools.StringTransform;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ConsumerOrderController {

    @Autowired
    ConsumerService consumerService;

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
