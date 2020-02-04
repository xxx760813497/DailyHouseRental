package com.xmj.demo.controller.master;


import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import com.xmj.demo.service.CommentaryService;
import com.xmj.demo.service.MasterService;
import com.xmj.demo.tools.StringTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HouseController {

    @Autowired
    MasterService masterService;

    @Autowired
    CommentaryService commentaryService;

    @GetMapping("/house/{id}")
    public House getHosueById(@PathVariable("id") Integer id){

        House house=masterService.getHouseById(id);

        if (house!=null){
            return house;
        }else {
            return  null;
        }
    }

    @PostMapping("/house")
    public Map addHouse(@RequestBody Map houseInfo, HttpSession session){

        System.out.println("接收到添加房屋请求");
        User user= (User) session.getAttribute("user");
        Integer userId=user.getId();
        int row=masterService.addHouse(houseInfo,userId);
        Map result=new HashMap();
        if (row>0){
            result.put("msg","success");
        }else {
           result.put("msg","error");
            System.out.println("失败");
        }
        return result;
    }

    @PutMapping("/house")
    public Map updateHouse(@RequestBody Map houseInfo){

        System.out.println("接收到修改房屋请求，数据为："+houseInfo);

        Map result=new HashMap();
        int row=masterService.updateHouse(houseInfo);
        if (row>0){
            result.put("msg","success");
        }else {
            result.put("msg","error");
        }
        return  result;
    }

    @GetMapping("/houses")
    public ArrayList<Map> getHousesByUserId(HttpSession session){
        User user= (User) session.getAttribute("user");
        ArrayList<House> houses= masterService.getHousesByUserId(user.getId());
        ArrayList<Map> results=new ArrayList<>();
        //将后端的全文件路径转换为前端可以使用的路径
        for (House house :houses){
            house.setHouseTitleImg(StringTransform.filePathOfView(house.getHouseTitleImg()));
            Map result=new HashMap();
            result.put("id",house.getId());
            result.put("userId",house.getUserId());
            result.put("name",house.getName());
            result.put("location",house.getLocation());
            result.put("address",house.getAddress());
            result.put("houseTitleImg",house.getHouseTitleImg());
            result.put("describe",house.getDescribe());
            result.put("houseImgs",house.getHouseImgs());
            result.put("price",house.getPrice());
            result.put("grade",house.getGrade());
            result.put("orderQuantity",house.getOrderQuantity());
            result.put("equipments",house.getEquipments());
            result.put("houseState",house.getHouseState());
            result.put("houseState",house.getHouseState());
            result.put("houseState",house.getHouseState());
            int noReadCommentaryNum =commentaryService.getNoReadCommentaryNumber(house.getId());
            result.put("noReadCommentaryNum",noReadCommentaryNum);
            results.add(result);
        }



        return results;
    }

    @PostMapping("/reapplyHouse")
    public String reapplyHouse(@RequestBody Map houseInfo){

        System.out.println("接收到重新申请房屋请求，数据为："+houseInfo);
        Map result=new HashMap();
        int row=masterService.updateReapplyHouseById(houseInfo);
        if (row>0){
            return "success";
        }else {
           return "error";
        }
    }

    @DeleteMapping("/house/{id}")
    public String houseWithdraw(@PathVariable("id") Integer id){

        System.out.println("接收到撤回房屋请求");
        int row=masterService.deleteHouseById(id);
        if (row>0){
            return "success";
        }else {
            return "error";
        }


    }



}
