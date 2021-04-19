package com.zr.smallcrm.setting.dao;

import com.zr.smallcrm.setting.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


//springboot dao层的注解
@Mapper
public interface UserDao {
    //登录方发
    User login(String username, String password);

    //对用户的crud
    List<User> getUserList ();
}
