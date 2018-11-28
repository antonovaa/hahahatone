package ru.gameserver.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gameserver.dao.Daoinsert;
import ru.gameserver.model.Authorization;
import ru.gameserver.model.AuthorizationRequest;
import ru.gameserver.model.CrashInfoModel;
import ru.gameserver.model.GameInfoModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import ru.gameserver.model.Registration;


@Controller
public class LogController {

    private final Daoinsert daoinsert;

    @Autowired
    public LogController(Daoinsert daoinsert) {
        this.daoinsert = daoinsert;

    }




    @RequestMapping("/registration")
    @ResponseBody
    public int registration(@RequestBody String registration) {

        try{

            return daoinsert.registration(new Gson().fromJson(registration, Registration.class));
        }
        catch (Exception e){
            return -100;
        }
    }
//{"login":"T1","password":"T2","email":"T3","gameName":"Test2"}

    @RequestMapping("/authorization")
    @ResponseBody
    public AuthorizationRequest authorization(@RequestBody String authorization) {
        try{

        return daoinsert.authorization(new Gson().fromJson(authorization, Authorization.class));

    }
        catch (Exception e){
        return new AuthorizationRequest(-1, 0,0,0,0,0,0,0,"none");
    }
    }


    @RequestMapping("/update")
    @ResponseBody
    public int update(@RequestBody String update) {
        try{

            return  daoinsert.update(new Gson().fromJson(update, AuthorizationRequest.class));
    }
        catch (Exception e){
            return -100;
            }
    }


    //same
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
