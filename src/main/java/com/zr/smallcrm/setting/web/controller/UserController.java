package com.zr.smallcrm.setting.web.controller;

import com.zr.smallcrm.myException.LoginException;
import com.zr.smallcrm.setting.model.User;
import com.zr.smallcrm.setting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    public void setService(UserService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String login() {
        return "/login.jsp";
    }

    @RequestMapping("/login")
    public  String loginOne(){
        return "/login.jsp";
    }

    @PostMapping("/login.do")
    public @ResponseBody
    Map<String, Object> login(String loginAct, String loginPwd, HttpServletRequest request) {
        System.out.println(loginAct);
        System.out.println(loginPwd);


        HashMap<String, Object> map = new HashMap<String, Object>();
//        System.out.println(loginAct+loginPwd);
        String addr = request.getRemoteAddr();
        System.out.println(addr);
        try {
            User user = service.loginService(loginAct, loginPwd, addr);
            request.getSession(false).setAttribute("user", user);
            //登陆成功
            map.put("status", true);

        } catch (LoginException e) {
            e.printStackTrace();

            //登录失败
            request.getSession().invalidate(); //清空session

            String message = e.getMessage();
            map.put("status", false);
            map.put("msg", message);
        }
        return map;
    }


    @RequestMapping ("/index")
    public String index(){
        return "/workbench/index.jsp";
    }

}
