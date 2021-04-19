package com.zr.smallcrm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zr.smallcrm.setting.dao.UserDao;
import com.zr.smallcrm.setting.model.User;
import com.zr.smallcrm.workbench.dao.ActivityDao;
import com.zr.smallcrm.workbench.dao.ActivityRemarkDao;
import com.zr.smallcrm.workbench.model.Activity;
import com.zr.smallcrm.workbench.model.ActivityRemark;
import com.zr.smallcrm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Transactional
    @Override
    public boolean saveActivity(Activity activity) {
//        System.out.println(activity.getId());
        int count = activityDao.saveActivity(activity);
        if (count == 1) {
            return true;
        }
        return false;
    }


    @Override
    public Map pageList(int pageNo, int pageSize, Activity a) {
        HashMap map = new HashMap();

        PageHelper.startPage(pageNo, pageSize);

        List<Activity> activities = activityDao.getList(a);

//        System.out.println(a.getOwner());

        //包含了分页查询的集合对象和分页的参数信息
        PageInfo<Activity> pi = new PageInfo<Activity>(activities);

        long total = pi.getTotal();

//        System.out.println("分页查询的集合" + pi.getList());

        map.put("total", total);

        map.put("list", pi.getList());

        return map;
    }

    @Transactional
    @Override
    public boolean deleteActivity(String[] ids) {

        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回收到影响的条数
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1 != count2) {
            return false;
        }
        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3 != ids.length) {
            return false;
        }

        return true;
    }

    @Override
    public Activity getActivity(String id) {

        return activityDao.getActivity(id);
    }

    @Transactional
    @Override
    public boolean updateActivity(Activity a) {

        int i = activityDao.updateActivity(a);
        if (i != 1) {
            return false;
        }

        return true;
    }

    @Override
    public Activity detail(String id) {
        Activity a = activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String aid) {
        return activityRemarkDao.getRemarkListByAid(aid);

    }

    @Transactional
    @Override
    public boolean deleteRemark(String id) {
        int i = activityRemarkDao.deleteRemark(id);
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean saveRemark(ActivityRemark remark) {
        boolean b = true;
        int i = activityRemarkDao.saveRemark(remark);
        if (i != 1) {
            b = false;
        }
        return b;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        int i = activityRemarkDao.updateRemark(ar);
        if (i!=1){
            return false;
        }
        return true;
    }


}
