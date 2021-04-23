package com.zr.smallcrm.workbench.dao;

import com.zr.smallcrm.workbench.model.ClueActivityRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClueActivityRelationDao {


    int unbund(String id);

    int addActivity(ClueActivityRelation car);
}
