package com.zr.smallcrm.setting.service;

import com.zr.smallcrm.myException.LoginException;
import com.zr.smallcrm.setting.model.User;

public interface UserService  {
    User loginService(String username,String password,String ip)throws LoginException;
}
