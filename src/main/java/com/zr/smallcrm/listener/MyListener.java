package com.zr.smallcrm.listener;

import com.zr.smallcrm.setting.model.DicValue;
import com.zr.smallcrm.setting.service.DicService;
import com.zr.smallcrm.setting.service.impl.DicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebListener
public class MyListener implements ServletContextListener {

    @Autowired
    private DicService service;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext application = sce.getServletContext();
        Map<String, List<DicValue>> map = service.getAll();
        //将map解析为上下文本狱中
        Set<String> set = map.keySet();
        for (String key : set) {
            application.setAttribute(key, map.get(key));
        }
    }
}
