package com.xmj.demo.controller.adminstrator;

import com.xmj.demo.entity.House;
import com.xmj.demo.service.AdminService;
import com.xmj.demo.tools.StringTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/reviewHouses")
    public ArrayList<House> getReviewHouses(){
        System.out.println("接收到获取审核中的房屋请求");
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


}
