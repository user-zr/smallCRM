package com.zr.smallcrm.setting.service.impl;

import com.zr.smallcrm.myException.LoginException;
import com.zr.smallcrm.setting.dao.UserDao;
import com.zr.smallcrm.setting.model.User;
import com.zr.smallcrm.setting.service.UserService;
import com.zr.smallcrm.utils.DateTimeUtil;
import com.zr.smallcrm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {


    //spring的依赖注入
    @Autowired
    private UserDao dao;

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public User loginService(String username, String password, String ip) throws LoginException {
        String md5 = MD5Util.getMD5(password);
        User user = dao.login(username, md5);

        //验证账号密码
        if (user == null) {
            throw new LoginException("用户名密码错误");
        }

        //验证状态码
        if ("0".equals(user.getLockState())) {
            throw new LoginException("该用户被锁定，请联系管理员");
        }

        //验证失效时间
        String expireTime = user.getExpireTime();
        String time = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(time) < 0) {
            throw new LoginException("该用户已过期，请联系管理员");
        }

        //验证ip地址
        String ips = user.getAllowIps();
        if (!ips.contains(ip)) {
            throw  new LoginException("ip地址以受限，请联系管理员");
        }
        return user;
    }
}
