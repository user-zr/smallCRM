package com.zr.smallcrm.setting.service;

import com.zr.smallcrm.setting.model.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getAll();
}
