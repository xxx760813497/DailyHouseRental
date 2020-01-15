package com.xmj.demo.service;

import com.xmj.demo.entity.House;
import com.xmj.demo.mapper.HouseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminService {

    @Resource
    HouseMapper houseMapper;

    public ArrayList<House> getReviewHouses(){
        ArrayList<House> houses=houseMapper.getReviewingHouses();
        return houses;
    }

    public int updateHouseStateById(Map houseInfo){
        Integer id= (Integer) houseInfo.get("id");
        String houseState= (String) houseInfo.get("houseState");
        return houseMapper.updateHouseStateById(id,houseState);
    }

}
