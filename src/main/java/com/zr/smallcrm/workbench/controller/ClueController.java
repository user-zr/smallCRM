package com.zr.smallcrm.workbench.controller;

import com.zr.smallcrm.setting.model.User;
import com.zr.smallcrm.setting.service.DicService;
import com.zr.smallcrm.setting.service.UserService;
import com.zr.smallcrm.utils.DateTimeUtil;
import com.zr.smallcrm.utils.UUIDUtil;
import com.zr.smallcrm.workbench.model.Activity;
import com.zr.smallcrm.workbench.model.Clue;
import com.zr.smallcrm.workbench.model.ClueActivityRelation;
import com.zr.smallcrm.workbench.service.ActivityService;
import com.zr.smallcrm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clue")
public class ClueController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueService service;

    @GetMapping("/getUserList")
    public @ResponseBody
    List<User> getUserList() {
        return activityService.getUserList();
    }

    @PostMapping("/saveClue")
    public @ResponseBody
    boolean saveClue(Clue clue) {
        System.out.println(clue.getCreateBy());
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        return service.saveClue(clue);
    }

    @GetMapping("/getClueList")
    public @ResponseBody
    Map<String, Object> getClueList(int pageNo, int pageSize, Clue clue) {
        return service.getClueList(pageNo, pageSize, clue);
    }

    @RequestMapping("/detail")
    public ModelAndView detail(String id) {
        System.out.println(id);
        ModelAndView mv = new ModelAndView();
        Clue c = service.detail(id);
        System.out.println(c);
        mv.addObject("c", c);
        mv.setViewName("/workbench/clue/detail.jsp");
        return mv;
    }

    @GetMapping("/getActivityListByCid")
    public @ResponseBody
    List<Activity> getActivityListByCid(String clueId) {
        return activityService.getActivityListByCid(clueId);
    }

    @PostMapping("/unbund")
    public @ResponseBody
    boolean unbund(String id) throws Exception {
        return service.unbund(id);
    }


    @RequestMapping("/getActivityByName")
    public @ResponseBody
    List<Activity> getActivityByName(String aname, String clueId) {
        System.out.println(aname + "   " + clueId);
        return activityService.getActivityByName(aname, clueId);
    }

    @PostMapping("/addActivity")
    public @ResponseBody
    boolean addActivity(String activityId, String clueId) {
        return service.addActivity(activityId, clueId);
    }

    @GetMapping("/getActivityListByName")
    public @ResponseBody
    List<Activity> getActivityListByName(String aname) {

        return activityService.getActivityListByName(aname);
    }
}