package com.zr.smallcrm.workbench.service;

import com.zr.smallcrm.setting.model.User;
import com.zr.smallcrm.workbench.model.Activity;
import com.zr.smallcrm.workbench.model.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    List<User> getUserList();

    boolean saveActivity(Activity activity);

    Map pageList(int pageNo, int pageSize , Activity a);

    boolean deleteActivity(String[] ids);

    Activity getActivity(String id);

    boolean updateActivity(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String aid);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark remark);

    boolean updateRemark(ActivityRemark ar);
}
