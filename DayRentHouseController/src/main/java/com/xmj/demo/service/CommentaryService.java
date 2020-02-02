package com.xmj.demo.service;


import com.xmj.demo.entity.Commentary;
import com.xmj.demo.mapper.CommentaryMapper;
import com.xmj.demo.mapper.OrderMapper;
import com.xmj.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class CommentaryService {

    @Resource
    CommentaryMapper commentaryMapper;

    @Resource
    OrderMapper orderMapper;


    @Transactional
    public Integer  addCommentary(Map info,Integer userId){
        Integer houseId= (Integer) info.get("houseId");
        Integer orderId= (Integer) info.get("orderId");
        double interiorScore= (int) info.get("interiorScore");
        double masterScore= (int) info.get("masterScore");
        double aroundScore= (int) info.get("aroundScore");
        String content= (String) info.get("content");
        ArrayList<String> imgsList= (ArrayList<String>) info.get("imgs");
        String imgs="";
        if (imgsList.size()>0){
            for (int i=0;i<imgsList.size();i++){
                imgs+=imgsList.get(i);
                if (i!=imgsList.size()-1){
                    imgs+=",";
                }
            }
        }
        Date date=new Date() ;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currenDate=simpleDateFormat.format(date);
        double fraction=(interiorScore+masterScore+aroundScore)/3;
        fraction = new BigDecimal(fraction).setScale(2, RoundingMode.UP).doubleValue();
        int row= commentaryMapper.addCommentary(userId,houseId,content,currenDate,imgs,fraction);

        if (row>0){
            return orderMapper.updateOrderStateById(orderId,"已评价");
        }else {
            return  -1;
        }
    }

    public Commentary getPionnerCommentary(Integer houseId){
        return commentaryMapper.getPionnerCommentary(houseId);
    }

    public int getCommentaryNumber(Integer houseId){
        return commentaryMapper.getCommentaryNumber(houseId);
    }

    public ArrayList<Commentary> getCommentaryByHouseId(Integer houseId){
        return  commentaryMapper.getCommentaryByHouseId(houseId);
    }

}
