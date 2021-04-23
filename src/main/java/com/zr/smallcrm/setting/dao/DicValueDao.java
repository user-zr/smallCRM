package com.zr.smallcrm.setting.dao;

import com.zr.smallcrm.setting.model.DicValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
