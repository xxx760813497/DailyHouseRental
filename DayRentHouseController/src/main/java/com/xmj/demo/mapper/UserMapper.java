package com.xmj.demo.mapper;

import com.xmj.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getUserById(Integer id);

    String selectUserPhonenum(String phonenum);

    User getUserByPhonenumAndPassword(String phonenum,String password);

    int insertNewUser(User User);

    int updateUserStatus(Integer id,String status);

    int updateUserPassword(Integer id,String password);

    User getUserByPhoneNum(String phonenum);
}
