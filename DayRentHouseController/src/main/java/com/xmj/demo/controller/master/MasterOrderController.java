package com.xmj.demo.controller.master;

import com.xmj.demo.entity.User;
import com.xmj.demo.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class MasterOrderController {

    @Autowired
    MasterService masterService;


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



}
