package ru.gameserver.dao;

import java.util.List;
import java.util.Map;

public interface WebShowInfo {



    public List<Map<String, String>> getScen(Integer[] t);
    public List<Map<String, Object>> getTags();

    public List<Map<String, Object>> getStepsInScen(int id);


}
