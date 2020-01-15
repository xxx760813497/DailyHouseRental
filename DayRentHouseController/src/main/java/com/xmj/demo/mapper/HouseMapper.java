package com.xmj.demo.mapper;

import com.xmj.demo.entity.House;
import com.xmj.demo.unitilEmitity.HouseInfo;

import java.util.ArrayList;
import java.util.Map;

public interface HouseMapper {

    House getHouseById(Integer id);
    int addHouse(House house);
    ArrayList<House> getHousesByUserId(Integer userId);
    int updateHouseById(Integer id,String name,String describe,Integer price,String equipments);
    ArrayList<House> getReviewingHouses();
    int updateHouseStateById(Integer id,String houseState);
    int updateReapplyHouseById(Integer id,String name,String describe,Integer price,String equipments);
    int deleteHouseById(Integer id);
    ArrayList<HouseInfo> getHouseIdAndAddress();
}
