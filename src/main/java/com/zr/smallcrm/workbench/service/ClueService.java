package com.zr.smallcrm.workbench.service;

import com.zr.smallcrm.workbench.model.Clue;
import com.zr.smallcrm.workbench.model.ClueActivityRelation;

import java.util.Map;

public interface ClueService {
    boolean saveClue(Clue clue);

    Map<String, Object> getClueList(int pageNo, int pageSize, Clue clue);

    Clue detail(String id);

    boolean unbund(String id) throws Exception;



    boolean addActivity(String activityId, String clueId);
}
