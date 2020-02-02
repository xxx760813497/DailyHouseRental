package com.xmj.demo.mapper;

import com.xmj.demo.entity.Order;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;

public interface OrderMapper {

    int addOrder
            (Integer userId,Integer houseId,String boardDate,
             String boardUserName,String boardUserTel,
             Integer orderPrice,String orderProduceTime,String orderState);

    ArrayList<Order> getOrderByUserId(Integer userId);
    ArrayList<Order> getOrderByHouseId(Integer houseId);

    Integer updateOrderStateById(Integer id,String orderState);
    Integer deleteOrderById(Integer id);
}