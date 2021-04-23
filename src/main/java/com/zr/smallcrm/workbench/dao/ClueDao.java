package com.zr.smallcrm.workbench.dao;


import com.zr.smallcrm.workbench.model.Clue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClueDao {


    boolean saveClue(Clue clue);

    List<Clue> getClueList();

    Clue detail(String id);
}
