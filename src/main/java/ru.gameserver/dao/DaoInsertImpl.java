package ru.gameserver.dao;

import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.gameserver.model.Authorization;
import ru.gameserver.model.AuthorizationRequest;
import ru.gameserver.model.CharacterGamer;
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

            Information information = new Information(0, 0, 0, 0, 0, 0, 0);
            String test = information.toString();
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

            String sqlReg = "select arena_info.get_id_authorization_gamer(?,?,?)";
            String sqlChar = "select * from arena_info.get_characters_gamer(?)";

            List<Integer> id = jdbcTemplate.queryForList(sqlReg,
                new Object[]{authorization.getLogin(), authorization.getPassword(),
                    authorization.getGameName()}, Integer.class);
            if (id.isEmpty()) {
                return new AuthorizationRequest(-1, null);
            } else {

                int registrated_id = id.get(0);
                if(registrated_id==-1){
                    return new AuthorizationRequest(registrated_id, null);
                }
                List<CharacterGamer> characterGamers = jdbcTemplate
                    .query(sqlChar, new Object[]{registrated_id},
                        (rs, rowNum) -> new CharacterGamer(
                            rs.getInt("characters_id"),
                            rs.getInt("character_level"),
                            rs.getInt("character_type"),
                            rs.getInt("count_kill_monster"),
                            rs.getInt("count_kill_boss_monster"),
                            rs.getInt("count_kill_enemy_player"),
                            rs.getInt("count_death"),
                            rs.getInt("max_level"),
                            rs.getInt("team_a"),
                            rs.getInt("team_b"),
                            rs.getString("info")
                        ));
                if(characterGamers.isEmpty()){
                    return new AuthorizationRequest(registrated_id, null);
                }
                return new AuthorizationRequest(registrated_id, characterGamers);
            }
        } catch (Exception e) {
            return new AuthorizationRequest(-1, null);
        }
    }

    @Override
    public int update(AuthorizationRequest authorizationRequest) {
        try {
            if (authorizationRequest.getRegistrated_id() == 0||authorizationRequest.getRegistrated_id()==-1||authorizationRequest.getRegistrated_id()==-10) {
                return -1;
            }
            List<CharacterGamer> characterGamers = authorizationRequest.getCharacterGamer();

            if(characterGamers.get(0).getCharacters_id()==-1){
                String sql = "select arena_info.insert_character(?,?,?,?,?,?,?,?,?,?,?)";

                int id=(int)jdbcTemplate.queryForList(sql,
                    authorizationRequest.getRegistrated_id(),
                    characterGamers.get(0).getCharacter_level(),
                    characterGamers.get(0).getCharacter_type(),
                    characterGamers.get(0).getCount_kill_monster(),
                    characterGamers.get(0).getCount_kill_boss_monster(),
                    characterGamers.get(0).getCount_kill_enemy_player(),
                    characterGamers.get(0).getCount_death(),
                    characterGamers.get(0).getMax_level(),
                    characterGamers.get(0).getTeam_a(),
                    characterGamers.get(0).getTeam_b(),
                    characterGamers.get(0).getInfo()
                ).get(0).get("insert_character");
                return id;

            }
            else{
                String sql = "select arena_info.update_character(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                jdbcTemplate.queryForList(sql,
                    authorizationRequest.getRegistrated_id(),
                    characterGamers.get(0).getCharacters_id(),
                    characterGamers.get(0).getCharacter_level(),
                    characterGamers.get(0).getCharacter_type(),
                    characterGamers.get(0).getCount_kill_monster(),
                    characterGamers.get(0).getCount_kill_boss_monster(),
                    characterGamers.get(0).getCount_kill_enemy_player(),
                    characterGamers.get(0).getCount_death(),
                    characterGamers.get(0).getMax_level(),
                    characterGamers.get(0).getTeam_a(),
                    characterGamers.get(0).getTeam_b(),
                    characterGamers.get(0).getInfo(),
                    authorizationRequest.isAdditionally()
                    );
            }
                return characterGamers.get(0).getCharacters_id();
            } catch (Exception e) {
                return -2;
            }
        }
    }
