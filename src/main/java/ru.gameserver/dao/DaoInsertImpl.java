package ru.gameserver.dao;

import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gameserver.model.Authorization;
import ru.gameserver.model.AuthorizationRequest;
import ru.gameserver.model.CrashInfoModel;
import ru.gameserver.model.GameInfoModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import ru.gameserver.model.Information;
import ru.gameserver.model.Registration;

@Repository
public class DaoInsertImpl implements Daoinsert {


    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public DaoInsertImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }


    @Override
    public int saveGameInfo(List<GameInfoModel> v, String name, String macAddr) {

        int result = 0;
        int l = v.size();
        String[] gameStart = new String[l];
        String[] gameEnd = new String[l];
        String[] playStart = new String[l];
        Integer[] playerCountListArr = new Integer[l];
        String[] serverIpListArr = new String[l];
        String[] gameName = new String[l];
        String[] info = new String[l];

        for (int i = 0; i < l; i++) {
            gameStart[i] = v.get(i).getGameStart();
            gameEnd[i] = v.get(i).getGameEnd();
            playStart[i] = v.get(i).getPlayStart();
            playerCountListArr[i] = v.get(i).getPlayerCount();
            serverIpListArr[i] = v.get(i).getServerIp();
            gameName[i] = v.get(i).getGameName();
            info[i] = v.get(i).getOtherInfrmation();

        }

        String callSQL = "select arena_info.insert_array_info_macAddres(?,?,?,?,?,?,?,?);";
        try (Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(callSQL)) {

            Array ar1 = con.createArrayOf("text", gameStart);
            Array ar2 = con.createArrayOf("text", gameEnd);
            Array ar3 = con.createArrayOf("text", playStart);
            Array ar4 = con.createArrayOf("int4", playerCountListArr);
            Array ar5 = con.createArrayOf("text", serverIpListArr);
            Array ar6 = con.createArrayOf("text", gameName);
            Array ar7 = con.createArrayOf("text", info);

            preparedStatement.setArray(1, ar1);
            preparedStatement.setArray(2, ar2);
            preparedStatement.setArray(3, ar3);
            preparedStatement.setArray(4, ar4);
            preparedStatement.setArray(5, ar5);
            preparedStatement.setArray(6, ar6);
            preparedStatement.setArray(7, ar7);
            preparedStatement.setString(8, macAddr);
            preparedStatement.execute();
            result = 0;

            try {

                if (!jdbcTemplate
                    .queryForList("select arena_info.is_allowed_this_pc_contractor_game(?,?)",
                        new Object[]{macAddr, name}, Boolean.class).get(0)) {
                    result = 1;
                }
            } catch (Exception e) {

            }
            return result;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return result;
        }
    }

    public int CheckAvailable(String name, String macAddr) {
        try {
            if (!jdbcTemplate
                .queryForList("select arena_info.is_allowed_this_pc_contractor_game(?,?)",
                    new Object[]{macAddr, name}, Boolean.class).get(0)) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }


    @Override
    public boolean saveCrashInfo(List<CrashInfoModel> crashInfoList, String name, String macAddr) {

        int l = crashInfoList.size();
        String[] playerIps = new String[l];
        String[] serverIps = new String[l];
        String[] logPlayers = new String[l];
        String[] logServers = new String[l];
        String[] gameStarts = new String[l];
        String[] gameName = new String[l];

        for (int i = 0; i < l; i++) {
            playerIps[i] = crashInfoList.get(i).getPlayerIp();
            serverIps[i] = crashInfoList.get(i).getServerIp();
            logPlayers[i] = crashInfoList.get(i).getLogPlayer();
            logServers[i] = crashInfoList.get(i).getLogServer();
            gameStarts[i] = crashInfoList.get(i).getGameStart();
            gameName[i] = crashInfoList.get(i).getGameName();
        }

        try (Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(
                "select arena_info.insert_array_crash_info_macaddres(?,?,?,?,?,?,?)")) {

            Array arPlayerIds = con.createArrayOf("text", playerIps);
            Array arServerIds = con.createArrayOf("text", serverIps);
            Array arLogPlayers = con.createArrayOf("text", logPlayers);
            Array arLogServers = con.createArrayOf("text", logServers);
            Array arGameStarts = con.createArrayOf("text", gameStarts);
            Array arGameName = con.createArrayOf("text", gameName);

            preparedStatement.setArray(1, arGameStarts);
            preparedStatement.setArray(2, arPlayerIds);
            preparedStatement.setArray(3, arServerIds);
            preparedStatement.setArray(4, arLogServers);
            preparedStatement.setArray(5, arLogPlayers);
            preparedStatement.setArray(6, arGameName);
            preparedStatement.setString(7, macAddr);
            preparedStatement.execute();

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public int registration(Registration registration) {

        try {
            String sql = "select arena_info.registration_gamer(?,?,?,?)";

            Information information=new Information(0,0,0,0,0,0,0);
            String test=information.toString();
            return jdbcTemplate.queryForList(sql,
                new Object[]{registration.getLogin(), registration.getPassword(),
                    registration.getEmail(), registration.getGameName()}, Integer.class).get(0);

        } catch (Exception e) {

            return -1;
        }

    }

    @Override
    public AuthorizationRequest authorization(Authorization authorization) {
        try {
            String sql = "select * from arena_info.authorization_gamer(?,?,?)";
            return jdbcTemplate.queryForObject(sql,
                new Object[]{authorization.getLogin(), authorization.getPassword(),
                    authorization.getGameName()}, (resultSet, rowNum) -> new AuthorizationRequest(
                        resultSet.getInt("registrated_id"),
                        resultSet.getInt("count_kill_monster"),
                        resultSet.getInt("count_kill_boss_monster"),
                        resultSet.getInt("count_kill_enemy_player"),
                        resultSet.getInt("count_death"),
                        resultSet.getInt("max_level"),
                        resultSet.getInt("team_a"),
                        resultSet.getInt("team_b"),
                        resultSet.getString("info")
                ));
        } catch (Exception e) {
            return new AuthorizationRequest(-1, 0,0,0,0,0,0,0,"none");
        }
    }

    @Override
    public int update(AuthorizationRequest authorizationRequest) {
        try {
            String sql = "select arena_info.update_gamer(?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.queryForList(sql,
                authorizationRequest.getRegistrated_id(),
                authorizationRequest.getInfo(),
                authorizationRequest.getCount_kill_monster(),
                authorizationRequest.getCount_kill_boss_monster(),
                authorizationRequest.getCount_kill_enemy_player(),
                authorizationRequest.getCount_death(),
                authorizationRequest.getMax_level(),
                authorizationRequest.getTeam_a(),
                authorizationRequest.getTeam_b()
            );
            return 0;
        } catch (Exception e) {

            return -1;
        }
    }
}
