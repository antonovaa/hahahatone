package ru.gameserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gameserver.dao.WebShowInfoImpl;

import java.util.List;
import java.util.Map;

@Controller
public class WebShowController {



    private final WebShowInfoImpl webShowInfo;

    @Autowired
    public WebShowController(WebShowInfoImpl webShowInfo) {
        this.webShowInfo = webShowInfo;
    }

        @RequestMapping(value = "/getTags")    @ResponseBody
    public List<Map<String, Object>> getTags() {
        try {
            return webShowInfo.getTags();
        } catch (Exception e) {
            return null;
        }
    }
    @RequestMapping(value = "/getScen")    @ResponseBody
    public List<Map<String, String>> getScen(@RequestParam(value = "t") Integer[] t) {
        try {
            return webShowInfo.getScen(t);
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/getStepsInScen")    @ResponseBody
    public List<Map<String, Object>> getStepsInScen(@RequestParam(value = "id") int id) {
        try {
            return webShowInfo.getStepsInScen(id);
        } catch (Exception e) {
            return null;
        }
    }

}
