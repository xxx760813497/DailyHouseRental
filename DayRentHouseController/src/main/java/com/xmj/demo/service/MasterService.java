package com.xmj.demo.service;

import com.xmj.demo.entity.House;
import com.xmj.demo.entity.Order;
import com.xmj.demo.mapper.CommentaryMapper;
import com.xmj.demo.mapper.HouseMapper;
import com.xmj.demo.mapper.OrderMapper;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.redis.HouseRedis;
import com.xmj.demo.tools.StringTransform;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MasterService {

    @Resource
    HouseMapper houseMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    UserMapper userMapper;

    @Autowired
    HouseRedis houseRedis;

    public House getHouseById(Integer id){
        //先在redis中查找，没有再缓存到redis中
        House house=houseRedis.get(id.toString());
        if (house==null){
            house=houseMapper.getHouseById(id);
            if (house!=null){
                houseRedis.set(house.getId().toString(),30L,house);
            }
        }
        String userPhone=userMapper.getUserById(house.getUserId()).getPhonenum();
        house.setUserPhone(userPhone);

        return house;
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
        Integer price;
        if (houseInfo.get("price") instanceof Integer){
             price= (Integer) houseInfo.get("price");
        }else {
             price= Integer.valueOf((String) houseInfo.get("price"));
        }

        String equipments= StringTransform.stringsToString((ArrayList) houseInfo.get("equipmentsList"));
        int row=houseMapper.updateHouseById(id,name,describe,price,equipments);
        //修改成功删除redis中的过期缓存
        if (row>0){
            if (houseRedis.get(id.toString())!=null){
                houseRedis.delete(id.toString());
            }
        }

        return row;
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

        Integer price;
        if (houseInfo.get("price") instanceof Integer){
            price= (Integer) houseInfo.get("price");
        }else {
            price= Integer.valueOf((String) houseInfo.get("price"));
        }

        String equipments= StringTransform.stringsToString((ArrayList) houseInfo.get("equipmentsList"));
        int row=houseMapper.updateReapplyHouseById(id,name,describe,price,equipments);
        if (row>0){
            if (houseRedis.get(id.toString())!=null){
                houseRedis.delete(id.toString());
            }
        }
        return  row;
    }

    @Transactional
    public int deleteHouseById(Integer id){

        int row=houseMapper.deleteHouseById(id);
        if (row>0){
            if (houseRedis.get(id.toString())!=null){
                houseRedis.delete(id.toString());
            }
        }
        return row;
    }

    public ArrayList<Map> getOrderByUserId(Integer userId) {
        ArrayList<Order> orders= new ArrayList<>();
        ArrayList<House> houses=houseMapper.getHousesByUserId(userId);
        for (House house:houses) {
            ArrayList<Order> orders2 = orderMapper.getOrderByHouseId(house.getId());
            for (Order order : orders2) {

                orders.add(order);
            }
        }
        ArrayList<Map> results=new ArrayList<Map>();

        for (Order order:orders){
                Map result=new HashMap();
                result.put("houseId",order.getHouse().getId());
                result.put("houseName",order.getHouse().getName());
                order.getHouse().setHouseTitleImg(StringTransform.filePathOfView(order.getHouse().getHouseTitleImg()));
                result.put("houseTitleImg",order.getHouse().getHouseTitleImg());
                result.put("orderId",order.getId());
                result.put("boardDate",order.getBoardDate());
                result.put("boardUserName",order.getBoardUserName());
                result.put("boardUserTel",order.getBoardUserTel());
                result.put("orderPrice",order.getOrderPrice());
                result.put("orderProduceTime",order.getOrderProduceTime());
                result.put("orderState",order.getOrderState());
                results.add(result);
        }
        return results;
    }

    @Transactional
    public Integer updateOrderStateById(Integer id,String orderState){
        return  orderMapper.updateOrderStateById(id,orderState);
    }


}
