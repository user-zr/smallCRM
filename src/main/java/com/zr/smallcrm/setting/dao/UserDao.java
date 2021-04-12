package com.zr.smallcrm.setting.dao;

import com.zr.smallcrm.setting.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User login(String username, String password);
}
