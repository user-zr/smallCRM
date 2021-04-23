package com.zr.smallcrm.setting.dao;

import com.zr.smallcrm.setting.model.DicType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DicTypeDao {
   List<DicType> getTypeList();
}
