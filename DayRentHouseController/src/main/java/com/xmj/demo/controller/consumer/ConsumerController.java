package com.xmj.demo.controller.consumer;


import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import com.xmj.demo.service.ConsumerService;
import com.xmj.demo.tools.LatLonUtil;
import com.xmj.demo.tools.StringTransform;
import com.xmj.demo.unitilEmitity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;



@RestController
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @PostMapping("/selectHouseByAddress")
    public ArrayList<House> selectHouseByAddress(@RequestBody Map info){
        String address= (String) info.get("address");
        int round= (int) info.get("round");
        ArrayList<String> dates= (ArrayList<String>) info.get("allDates");
        Point point=new Point(address);

        System.out.println("传来的dates:"+dates);

        ArrayList<House> houses=consumerService.selectHouseByAddress(point,round,dates);
        for (House house:houses){
            house.setHouseTitleImg(StringTransform.filePathOfView(house.getHouseTitleImg()));
        }
        System.out.println("最终返回结果条数："+houses.size());
        return houses;
    }


}
