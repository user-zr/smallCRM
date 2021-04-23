package com.zr.smallcrm.setting.service.impl;

import com.zr.smallcrm.setting.dao.DicTypeDao;
import com.zr.smallcrm.setting.dao.DicValueDao;
import com.zr.smallcrm.setting.model.DicType;
import com.zr.smallcrm.setting.model.DicValue;
import com.zr.smallcrm.setting.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicTypeDao tDao;

    @Autowired
    private DicValueDao vdao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        HashMap<String, List<DicValue>> map = new HashMap<>();

        //将字典类型列表取出
        List<DicType> dtList = tDao.getTypeList();

        System.out.println(dtList);
        //将字典类型遍历
        for (DicType dt : dtList) {

            String code = dt.getCode();

            List<DicValue> dvList = vdao.getListByCode(code);

            map.put(code, dvList);
        }
        return map;
    }
}
