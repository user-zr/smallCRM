package com.zr.smallcrm.workbench.controller;

import com.zr.smallcrm.setting.model.User;
import com.zr.smallcrm.utils.DateTimeUtil;
import com.zr.smallcrm.utils.UUIDUtil;
import com.zr.smallcrm.workbench.model.Activity;
import com.zr.smallcrm.workbench.model.ActivityRemark;
import com.zr.smallcrm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/activity")
public class ActivityController {


    @Autowired
    private ActivityService service;

    public void setService(ActivityService service) {
        this.service = service;
    }

    @GetMapping("/getUserList")
    public @ResponseBody
    List<User> getUserList() {
        return service.getUserList();
    }

    @PostMapping("/saveActivity")
    public @ResponseBody
    boolean saveActivity(String owner,
                         String name,
                         String startDate,
                         String endDate,
                         String cost,
                         String description,
                         HttpSession session
    ) {

        String id = UUIDUtil.getUUID();
        User user = (User) session.getAttribute("user");
        String createBy = user.getName();
        String createTime = DateTimeUtil.getSysTime();

//        System.out.println(id+"   "+createTime+"   "+createBy);

        Activity a = new Activity(id, owner, name, startDate, endDate, cost, description, createTime, createBy);
//        System.out.println(a.getId());
        boolean b = service.saveActivity(a);

        return b;
    }

    @RequestMapping("/pageList")
    public @ResponseBody
    Map pageList(String pageNo, String pageSize, Activity a) {

        Map map = service.pageList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), a);

        return map;
    }

    @PostMapping("/deleteActivity")
    public @ResponseBody
    boolean deleteActivity(String id) {
        String[] ids = id.split(",");
        boolean b = service.deleteActivity(ids);
        return b;
    }

    @GetMapping("/getUserListAndActivity")
    public @ResponseBody
    Map getUserListAndActivity(String aid) {
        System.out.println(aid);
        HashMap map = new HashMap();
        map.put("ulist", service.getUserList());
        map.put("a", service.getActivity(aid));
        return map;
    }

    @PostMapping("/updateActivity")
    public @ResponseBody
    boolean updateActivity(Activity a, HttpSession session) {
        User user = (User) session.getAttribute("user");
        a.setEditBy(user.getName());
        a.setEditTime(DateTimeUtil.getSysTime());
        boolean b = service.updateActivity(a);
        return b;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(String id) {
        ModelAndView mv = new ModelAndView();
        Activity a = service.detail(id);
        mv.addObject("a", a);
        mv.setViewName("/workbench/activity/detail.jsp");
        return mv;
    }

    @GetMapping("/getRemarkListByAid")
    public @ResponseBody
    List<ActivityRemark> getRemarkListByAid(String id) {
        System.out.println(id);
        System.out.println(service.getRemarkListByAid(id));
        return service.getRemarkListByAid(id);
    }

    @PostMapping("/deleteRemark")
    public @ResponseBody
    boolean deleteRemark(String id) {
        return service.deleteRemark(id);
    }

    @PostMapping("/saveRemark")
    public @ResponseBody
    Map<String, Object> saveRemark(ActivityRemark remark, HttpSession session) {
        String uuid = UUIDUtil.getUUID();
        System.out.println(uuid);
        remark.setId(uuid);

        remark.setCreateTime(DateTimeUtil.getSysTime());
        User user = (User) session.getAttribute("user");
        remark.setCreateBy(user.getName());
        remark.setEditFlag("0");

        boolean b = service.saveRemark(remark);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("success", b);
        map.put("ar", remark);
        return map;
    }


    @RequestMapping("/updateRemark")
    public @ResponseBody
    boolean updateRemark(ActivityRemark ar ,HttpSession session) {
        User user = (User) session.getAttribute("user");
        ar.setEditBy(user.getName());
        ar.setEditTime(DateTimeUtil.getSysTime());
        ar.setEditFlag("1");
        return service.updateRemark(ar);
    }
}
