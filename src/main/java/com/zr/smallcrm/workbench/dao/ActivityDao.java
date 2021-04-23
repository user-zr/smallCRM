package com.zr.smallcrm.workbench.dao;

import com.zr.smallcrm.workbench.model.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityDao {
    int saveActivity(Activity a);

    List<Activity> getList(Activity a);


    int delete(String[] ids);

    Activity getActivity(String id);

    int updateActivity(Activity a);

    Activity detail(String id);

    List<Activity> getActivityListByCid(String clueId);

    List<Activity> getActivityByName(String aname, String clueId);

    List<Activity> getActivityListByName(String aname);

}
