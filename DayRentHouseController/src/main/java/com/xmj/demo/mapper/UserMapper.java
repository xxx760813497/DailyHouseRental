package com.xmj.demo.mapper;

import com.xmj.demo.entity.User;

public interface UserMapper {

    User getUserById(Integer id);

    String selectUserPhonenum(String phonenum);

    User getUserByPhonenumAndPassword(String phonenum,String password);

    int insertNewUser(User User);

    int updateUserStatus(Integer id,String status);


}
