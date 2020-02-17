package com.xmj.demo.mapper;

import com.xmj.demo.entity.Commentary;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CommentaryMapper {
    Integer addCommentary(Integer userId,Integer houseId,Integer orderId,String content,String creatTime,String imgs,Double fraction);

    Commentary getCommentaryById(Integer id);

    ArrayList<Commentary> getCommentaryByHouseId(Integer id);

    Commentary getPionnerCommentary(Integer houseId);

     int getCommentaryNumber(Integer houseId);

    int getNoReadCommentaryNumber(Integer houseId);

    int updateCommentaryReply(Integer id,String content);

    int updateCommentaryAppeal(Integer id,String content);

    ArrayList<Commentary> getRivewCommentaries();

    int updateCommentaryStatus(Integer id,String status);

    int deleteCommentaryById(Integer id);
}
