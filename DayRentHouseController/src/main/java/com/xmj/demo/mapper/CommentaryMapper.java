package com.xmj.demo.mapper;

import com.xmj.demo.entity.Commentary;

import java.util.ArrayList;

public interface CommentaryMapper {
    Integer addCommentary(Integer userId,Integer  houseId,String content,String creatTime,String imgs,Double fraction);

    Commentary getCommentaryById(Integer id);

    ArrayList<Commentary> getCommentaryByHouseId(Integer id);

    Commentary getPionnerCommentary(Integer houseId);

     int getCommentaryNumber(Integer houseId);

    int getNoReadCommentaryNumber(Integer houseId);

    int updateCommentaryReply(Integer id,String content);
}
