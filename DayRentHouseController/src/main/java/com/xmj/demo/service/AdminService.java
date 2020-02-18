package com.xmj.demo.service;

import com.xmj.demo.entity.House;
import com.xmj.demo.mapper.CommentaryMapper;
import com.xmj.demo.mapper.HouseMapper;
import com.xmj.demo.mapper.OrderMapper;
import com.xmj.demo.redis.HouseRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminService {

    @Resource
    HouseMapper houseMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    CommentaryMapper commentaryMapper;

    @Autowired
    HouseRedis houseRedis;

    public ArrayList<House> getReviewHouses(){
        ArrayList<House> houses=houseMapper.getReviewingHouses();
        return houses;
    }

    public int updateHouseStateById(Map houseInfo){
        Integer id= (Integer) houseInfo.get("id");
        String houseState= (String) houseInfo.get("houseState");
        int row=houseMapper.updateHouseStateById(id,houseState);
        if (row>0){
            if (houseRedis.get(id.toString())!=null){
                houseRedis.delete(id.toString());
            }
        }
        return  row;
    }

    public  int deleteCommentaryById(Integer id){
        return commentaryMapper.deleteCommentaryById(id);
    }

}
