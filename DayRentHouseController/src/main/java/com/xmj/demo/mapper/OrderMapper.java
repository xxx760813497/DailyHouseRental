package com.xmj.demo.mapper;

import com.xmj.demo.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;

@Mapper
public interface OrderMapper {

    int addOrder
            (Integer userId,Integer houseId,String boardDate,
             String boardUserName,String boardUserTel,
             Integer orderPrice,String orderProduceTime,String orderState);

    ArrayList<Order> getOrderByUserId(Integer userId);
    ArrayList<Order> getOrderByHouseId(Integer houseId);

    Integer updateOrderStateById(Integer id,String orderState);
    Integer deleteOrderById(Integer id);

    Order getOrderById(Integer id);

}
