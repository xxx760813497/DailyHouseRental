package com.xmj.demo.controller.master;


import com.xmj.demo.mapper.HouseMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HouseController {

    @Resource
    HouseMapper houseMapper;

    @PostMapping("/house")
    public Map addHouse(@RequestBody Map houseInfo){

        System.out.println("接收到添加房屋请求");
//        System.out.println(houseInfo);
        String name= (String) houseInfo.get("name");
        String address= (String) houseInfo.get("address");
//        String houseTitleImg= (String) houseInfo.get("houseTitleImg");
        String describe= (String) houseInfo.get("describe");
//        String houseImgs= (String) houseInfo.get("houseImgs");
        String equipments= (String) houseInfo.get("equipments");
//        Integer price= Integer.valueOf(houseInfo.get("price").toString());
        String grade= (String) houseInfo.get("grade");
//        Integer orderQuantity= Integer.valueOf(houseInfo.get("orderQuantity").toString());



        Map result=new HashMap();
        result.put("msg","ok");
        return result;
    }

}
