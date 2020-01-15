package com.xmj.demo.service;

import com.xmj.demo.entity.House;
import com.xmj.demo.mapper.HouseMapper;
import com.xmj.demo.tools.LatLonUtil;
import com.xmj.demo.unitilEmitity.HouseInfo;
import com.xmj.demo.unitilEmitity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ConsumerService {

    @Resource
    HouseMapper houseMapper;

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
}
