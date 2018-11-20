package ru.gameserver.dao;

import ru.gameserver.model.Authorization;
import ru.gameserver.model.AuthorizationRequest;
import ru.gameserver.model.CrashInfoModel;
import ru.gameserver.model.GameInfoModel;

import java.util.List;
import ru.gameserver.model.Registration;

public interface Daoinsert {
    public int saveGameInfo(List<GameInfoModel> v, String name, String macAddr);

    public boolean saveCrashInfo(List<CrashInfoModel> crashInfoList, String name, String macAddr);

    public int CheckAvailable(String name,String macAddr);







    public int registration(Registration registration,String gameName);

    public AuthorizationRequest authorization(Authorization authorization,String gameName);

    public int update(AuthorizationRequest authorizationRequest);

}
