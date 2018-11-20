package ru.gameserver.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gameserver.dao.Daoinsert;
import ru.gameserver.model.CrashInfoModel;
import ru.gameserver.model.GameInfoModel;
import ru.gameserver.model.GameReportLog;
import ru.gameserver.model.SingleCrashModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


@Controller
public class LogController {

    private final Daoinsert daoinsert;

    @Autowired
    public LogController(Daoinsert daoinsert) {
        this.daoinsert = daoinsert;

    }




    @RequestMapping("/registration/{name}/{macAddr}")
    @ResponseBody
    public int registration(@RequestBody String registration,
                                                 @PathVariable("name") String name,
                                                 @PathVariable("macAddr") String macAddr) {
        Type listType = new TypeToken<ArrayList<GameInfoModel>>() {
        }.getType();


        return 0;
        //return daoinsert.saveGameInfo(new Gson().fromJson(registration, listType),name,macAddr);
    }


    @RequestMapping("/authorization/{name}/{macAddr}")
    @ResponseBody
    public int authorization(@RequestBody String authorization,
                                                 @PathVariable("name") String name,
                                                 @PathVariable("macAddr") String macAddr) {
        Type listType = new TypeToken<ArrayList<GameInfoModel>>() {
        }.getType();


        return 0;
        //return daoinsert.saveGameInfo(new Gson().fromJson(registration, listType),name,macAddr);
    }


    @RequestMapping("/update/{name}/{macAddr}")
    @ResponseBody
    public int update(@RequestBody String authorization,
                                                 @PathVariable("name") String name,
                                                 @PathVariable("macAddr") String macAddr) {
        Type listType = new TypeToken<ArrayList<GameInfoModel>>() {
        }.getType();


        return 0;
        //return daoinsert.saveGameInfo(new Gson().fromJson(registration, listType),name,macAddr);
    }

    @RequestMapping("/CheckAvailable/{name}/{macAddr}")
    @ResponseBody
    public int CheckAvailable(@PathVariable("name") String name,@PathVariable("macAddr") String macAddr) {
        return daoinsert.CheckAvailable(name,macAddr);

    }

    @RequestMapping("/saveGameInfoList/{name}/{macAddr}")
    @ResponseBody
    public int saveGameInfoList(@RequestBody String gameInfoList,
                                @PathVariable("name") String name,
                                @PathVariable("macAddr") String macAddr) {
        Type listType = new TypeToken<ArrayList<GameInfoModel>>() {
        }.getType();
        return daoinsert.saveGameInfo(new Gson().fromJson(gameInfoList, listType),name,macAddr);
    }

    @RequestMapping("/saveCrashInfo/{name}/{macAddr}")
    @ResponseBody
    public int saveCrashInfo(@RequestBody String gameInfoList,
                                              @PathVariable("name") String name,
                                              @PathVariable("macAddr") String macAddr) {
        Type listType = new TypeToken<ArrayList<CrashInfoModel>>() {
        }.getType();
        HashMap<String, String> hashMap = new HashMap<>();
        if (daoinsert.saveCrashInfo(new Gson().fromJson(gameInfoList, listType),name,macAddr)) {
            return 0;//success
        } else {
            return 1;//fail
        }
    }
}
