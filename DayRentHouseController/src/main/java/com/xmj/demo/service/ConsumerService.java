package com.xmj.demo.service;

import com.xmj.demo.entity.House;
import com.xmj.demo.entity.Order;
import com.xmj.demo.entity.User;
import com.xmj.demo.mapper.HouseMapper;
import com.xmj.demo.mapper.OrderMapper;
import com.xmj.demo.mapper.UserMapper;
import com.xmj.demo.tools.LatLonUtil;
import com.xmj.demo.tools.StringTransform;
import com.xmj.demo.unitilEmitity.HouseInfo;
import com.xmj.demo.unitilEmitity.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConsumerService {

    @Resource
    HouseMapper houseMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    UserMapper userMapper;

    public ArrayList<House> selectHouseByAddress(Point p, int round,ArrayList<String> dateList){

        ArrayList<HouseInfo> houseInfos=houseMapper.getHouseIdAndAddress();
        ArrayList<House> houses=new ArrayList<House>();
        for (HouseInfo houseInfo:houseInfos){
            Point p2=new Point(houseInfo.getAddress());
            //进行距离判断
            double distance=LatLonUtil.getDistance(p,p2);

            if (distance<=round){
                //距离合格后进行时间上的判断
                //由于存在数据库中的时间为字符串的形式，以“，”分割，所以还需要分别处理房屋已经出租一天或者多天的情况
                //两次循环检测，用户选择的时间房屋是否已经被租出，如果没有那么将房屋添加到返回给前端的房屋数组中
                House house=houseMapper.getHouseById(houseInfo.getId());
                String houseDate=house.getDate();

                int dateDetermine=0;
                //如果房屋已经出租的天数不止一天
                if (houseDate!=null&&houseDate.length()>9){
                    System.out.println("执行--出租多天");
                    String[] dates=houseDate.split(",");
                    for (String date:dateList){
                        int dateDetermin2=0;
                        for (int i=0;i<dates.length;i++){
                            System.out.println("遍历的房屋数组date:"+dates[i]);
                            if (dates[i].equals(date)){
                                break;
                            }
                            dateDetermin2++;
                        }
                        if (dateDetermin2==dates.length){
                            dateDetermine++;
                        }
                    }
                    System.out.println("dateDetermine:"+dateDetermine+",dateListSize:"+dateList.size());
                    if (dateDetermine==dateList.size()){
                        house.setDistance(distance);
                        houses.add(house);
                    }

                //房屋出租的时间只有一天或者从未出租过
                }else {
                    //从未出租
                    if (houseDate==null){
                        System.out.println("执行--从未出租");
                        house.setDistance(distance);
                        houses.add(house);

                    //出租了一天
                    }else {
                        System.out.println("执行--出租一天");
                        if (!dateList.contains(houseDate)){
                            houses.add(house);
                        }
                    }
                }
            }

        }


        return  houses;
    }

    @Transactional
    public int addOrder(Integer userId,Integer houseId,ArrayList boardDate, String boardUserName,String boardUserTel, Integer orderPrice){

        String boardDateString="";
        for (int i=0;i<boardDate.size();i++){
            boardDateString+=boardDate.get(i);
            if (i!=boardDate.size()-1){
                boardDateString+=',';
            }
        }

        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderProduceTime=simpleDateFormat.format(date);
        String orderState="已下单";
        return orderMapper.addOrder(userId,houseId,boardDateString,boardUserName,boardUserTel,orderPrice,orderProduceTime,orderState);
    };

    public ArrayList<Map> getOrderByUserId(Integer userId) {
        ArrayList<Order> orders= orderMapper.getOrderByUserId(userId);
        ArrayList<Map> results=new ArrayList<Map>();
        for (Order order:orders){
            if (order.getUser().getStatus().equals("consumer")){
                Map result=new HashMap();
                result.put("houseId",order.getHouse().getId());
                result.put("houseName",order.getHouse().getName());
                order.getHouse().setHouseTitleImg(StringTransform.filePathOfView(order.getHouse().getHouseTitleImg()));
                result.put("houseTitleImg",order.getHouse().getHouseTitleImg());
                User master=userMapper.getUserById(order.getHouse().getUserId());
                result.put("userPhone",master.getPhonenum());
                result.put("orderId",order.getId());
                result.put("boardDate",order.getBoardDate());
                result.put("boardUserName",order.getBoardUserName());
                result.put("boardUserTel",order.getBoardUserTel());
                result.put("orderPrice",order.getOrderPrice());
                result.put("orderProduceTime",order.getOrderProduceTime());
                result.put("orderState",order.getOrderState());
                results.add(result);
            }
        }
        return results;
    }

    @Transactional
    public Integer deleteOrderById(Integer id){
        return orderMapper.deleteOrderById(id);
    }

    @Transactional
    public Integer updateOrderStateById(Integer id,String orderState){
        return  orderMapper.updateOrderStateById(id,orderState);
    }
}
