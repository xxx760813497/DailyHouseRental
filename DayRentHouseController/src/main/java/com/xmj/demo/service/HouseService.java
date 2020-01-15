package com.xmj.demo.service;

import com.xmj.demo.entity.House;
import com.xmj.demo.mapper.HouseMapper;
import com.xmj.demo.tools.StringTransform;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class HouseService {

    @Resource
    HouseMapper houseMapper;

    public House getHouseById(Integer id){
        return houseMapper.getHouseById(id);
    }

    @Transactional
    public int addHouse(Map houseInfo,int userId){
        String name= (String) houseInfo.get("name");
        String location= (String) houseInfo.get("location");
        String address= (String) houseInfo.get("address");
        String houseTitleImg= (String) houseInfo.get("titleImg");
        String describe= (String) houseInfo.get("describe");
        String houseImgs= StringTransform.stringsToString((ArrayList) houseInfo.get("imgList"));
        String equipments= StringTransform.stringsToString((ArrayList) houseInfo.get("equipmentsList"));
        Integer price= Integer.valueOf(houseInfo.get("price").toString());
        House house=new House(userId,name,location,address,houseTitleImg,describe,houseImgs,price,equipments);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        house.setAddDate(df.format(new Date()));
        house.setHouseState("审核中");
        return houseMapper.addHouse(house);
    }

    @Transactional
    public int updateHouse(Map houseInfo){

        Integer id= (Integer) houseInfo.get("id");
        String name= (String) houseInfo.get("name");
        String describe= (String) houseInfo.get("describe");
        Integer price= Integer.valueOf((String) houseInfo.get("price"));
        String equipments= StringTransform.stringsToString((ArrayList) houseInfo.get("equipmentsList"));

        System.out.println("处理后的数据：");
        System.out.println("name:"+name);
        System.out.println("describe:"+describe);
        System.out.println("price:"+price);
        System.out.println("equipments:"+equipments);
        return houseMapper.updateHouseById(id,name,describe,price,equipments);
    }

    public ArrayList<House> getHousesByUserId(Integer userId){
        ArrayList<House> houses=houseMapper.getHousesByUserId(userId);
        return houses;
    }

    @Transactional
    public int updateReapplyHouseById(Map houseInfo){
        Integer id= (Integer) houseInfo.get("id");
        String name= (String) houseInfo.get("name");
        String describe= (String) houseInfo.get("describe");
        System.out.println(houseInfo.get("price").getClass());
        Integer price= Integer.valueOf((String) houseInfo.get("price"));
        String equipments= StringTransform.stringsToString((ArrayList) houseInfo.get("equipmentsList"));

        System.out.println("处理后的数据：");
        System.out.println("name:"+name);
        System.out.println("describe:"+describe);
        System.out.println("price:"+price);
        System.out.println("equipments:"+equipments);
        return houseMapper.updateReapplyHouseById(id,name,describe,price,equipments);
    }

    public int deleteHouseById(Integer id){
        return houseMapper.deleteHouseById(id);
    }

}
