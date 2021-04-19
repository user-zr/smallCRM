package com.zr.smallcrm.workbench.dao;

import com.zr.smallcrm.workbench.model.ActivityRemark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String aid);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark remark);

    int updateRemark(ActivityRemark ar);
}
