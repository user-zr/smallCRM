package com.zr.smallcrm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zr.smallcrm.utils.UUIDUtil;
import com.zr.smallcrm.workbench.dao.ClueActivityRelationDao;
import com.zr.smallcrm.workbench.dao.ClueDao;
import com.zr.smallcrm.workbench.model.Clue;
import com.zr.smallcrm.workbench.model.ClueActivityRelation;
import com.zr.smallcrm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao dao;

    @Autowired
    private ClueActivityRelationDao carDao;

    @Override
    public boolean saveClue(Clue clue) {
        return dao.saveClue(clue);
    }

    @Override
    public Map<String, Object> getClueList(int pageNo, int pageSize, Clue clue) {

        PageHelper.startPage(pageNo, pageSize);
        List<Clue> list = dao.getClueList();

        PageInfo<Clue> pi = new PageInfo<>(list);


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list", pi.getList());
        map.put("total", pi.getTotal());
        return map;


    }

    @Override
    public Clue detail(String id) {

        return dao.detail(id);

    }

    @Transactional
    @Override
    public boolean unbund(String id) throws Exception {
        int i = carDao.unbund(id);
        if (i != 1) {
            if (1==1) {
                throw new Exception("") ;
            }
            return false;
        }
        return true;

    }

    @Override
    public boolean addActivity(String activityId, String clueId) {
        String[] strings = activityId.split(",");

        for (String aid : strings) {
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(clueId);
            System.out.println(car);
            int i = carDao.addActivity(car);
            if (i != 1) {
                return false;
            }
        }
        return true;
    }
}