package ru.gameserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gameserver.model.Gkey;
import ru.gameserver.model.ResponseGame;
import ru.gameserver.model.SingleGkey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContractorGameImpl implements ContractorGame {

    String KEYFORONCECONSTANTINE = "KeyGeneratedForOnceContractor";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContractorGameImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public String getKeyToday(int contractor_games_id) {

        String sql = "select * from arena_info.get_contractor_game_place(" + contractor_games_id + ")";
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForList(sql).get(0);
        Date date = new Date();
        String contGame = (String) stringObjectMap.get("name") + (String) stringObjectMap.get("game_name");
        return getHash(contGame + date.getMonth() + date.getDate());
    }

    @Override
    public String getHash(String str) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");

            md.update(str.getBytes());
            byte[] dataBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dataBytes.length; i++) {
                sb.append(Integer.toString((dataBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }


    @Override
    public String getExistMd5Key(String macAddress, String gameName) {
        try {
            String sql = "select * from arena_info.get_key_exist_init_game(?,?)";
            return jdbcTemplate.queryForList(sql, new Object[]{macAddress, gameName}, String.class).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseGame InitSaveGkey(Gkey gkey) {
        boolean ifExist = false;
        int contractor_games_id = -1;
        int maxPlayers = 0;
        int generated_contractor_key_id = -1;
        String gameName = null;
        try {
            Map<String, Object> GamePlayesr = jdbcTemplate.queryForList("select * from arena_info.get_game_name_players_by_key(?)", new Object[]{gkey.getKey()}).get(0);
            gameName = (String) GamePlayesr.get("game_name");
            maxPlayers = (int) GamePlayesr.get("max_players");
        } catch (Exception e) {

        }
        if (gameName != null && !gameName.equals("")) {

            //TODO change logic for generic
            String initGameKey;
            initGameKey = getExistMd5Key(gkey.getMac(), gameName);
            Map<String, Object> idsMap;
            if (initGameKey == null) {//if key exist, but do not initialized


                if (!getAllMd5Keys(gkey.getKey()).isEmpty()) {
                    idsMap = getAllMd5Keys(gkey.getKey()).get(0);
                    generated_contractor_key_id = (int) idsMap.get("id");
                    contractor_games_id = (int) idsMap.get("contractor_games_id");
                    try {
                        String sql = "insert into arena_info.init_contractor_game_mac_key (key_game, place_game, mac_address, contractor_games_id) values ('" + gkey.getKey() + "','" + gkey.getPlace() + "','" + gkey.getMac() + "'," + contractor_games_id + ")";
                        jdbcTemplate.execute(sql);
                        decreaseCountKey(generated_contractor_key_id);
                        return new ResponseGame(0, gameName, gameName + "Server", maxPlayers);//"success init game";
                    } catch (Exception e) {
                        return new ResponseGame(2, gameName, gameName + "Server", maxPlayers);//"error init game";
                    }
                } else {
                    return new ResponseGame(5,  gameName, gameName + "Server", maxPlayers);//"this key not can be used: count=0";
                }


            } else if (initGameKey.equals(gkey.getKey())) {//if key exist, and initialized
                if (!jdbcTemplate.queryForList("select arena_info.is_allowed_this_pc_contractor_game(?,?)", new Object[]{gkey.getMac(), gameName}, Boolean.class).get(0)) {
                    return new ResponseGame(3, gameName, gameName + "Server", maxPlayers);//"error init game";
                }
                return new ResponseGame(0, gameName, gameName + "Server", maxPlayers);//"success init game";
            } else {
                return new ResponseGame(4, gameName, gameName + "Server", maxPlayers);//"this PC already used key";
            }
        } else {
            return new ResponseGame(1, "null", "null",0);//"this key failed";
        }

    }

    @Override
    public int InitSingleGameGkey(SingleGkey singleGkey) {

        String gameName = null;
        try {
            Map<String, Object> GamePlayesr = jdbcTemplate.queryForList("select * from arena_info.get_game_name_players_by_key(?)", new Object[]{singleGkey.getKey()}).get(0);
            gameName = (String) GamePlayesr.get("game_name");


            if(gameName.equals(singleGkey.getGameName())){ // key existing

            int generated_contractor_key_id,contractor_games_id;
            String initGameKey;
            initGameKey = getExistMd5Key(singleGkey.getMac(), singleGkey.getGameName());
            Map<String, Object> idsMap;

            if (initGameKey == null) {//if key exist, but do not initialized


                    if (!getAllMd5Keys(singleGkey.getKey()).isEmpty()) {
                        idsMap = getAllMd5Keys(singleGkey.getKey()).get(0);
                        generated_contractor_key_id = (int) idsMap.get("id");
                        contractor_games_id = (int) idsMap.get("contractor_games_id");
                        try {
                            String sql = "insert into arena_info.init_contractor_game_mac_key (key_game, place_game, mac_address, contractor_games_id) values ('" + singleGkey.getKey() + "','" + singleGkey.getPlace() + "','" + singleGkey.getMac() + "'," + contractor_games_id + ")";
                            jdbcTemplate.execute(sql);
                            decreaseCountKey(generated_contractor_key_id);
                            return  10;//"success init game and add key new";
                        } catch (Exception e) {
                            return 11;//"error init game";
                        }
                    } else {
                        return 11;//"this key not can be used: count=0";
                    }
                }

                else {//if key exist, and initialized
                if (!jdbcTemplate.queryForList("select arena_info.is_allowed_this_pc_contractor_game(?,?)", new Object[]{singleGkey.getMac(), gameName}, Boolean.class).get(0)) {
                    return 12;//"error init game";
                }
                else {
                    return 10;//"success init game not blocked";
                }
            }
        }
        else{
            return 11;
            }
        } catch (Exception e) {
            return 11;
        }

    }

    @Override
    public String changeResolution(int init_contractor_game_mac_key_id) {
        try {
            jdbcTemplate.execute("update arena_info.init_contractor_game_mac_key set isallowed=not isallowed where init_contractor_game_mac_key_id=" + init_contractor_game_mac_key_id);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }


    @Override
    public Map<String, Object> getMd5Key(int contractor_games_id) {
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        String sql = "select key_game,count from arena_info.generated_contractor_key where count>0 and contractor_games_id=" + contractor_games_id;
        try {

            return jdbcTemplate.queryForList(sql).get(0);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<Map<String, Object>> getAllMd5Keys(String key_game) {


        String sql = "select contractor_games_id,id from arena_info.generated_contractor_key where count>0 and key_game=?";
        return jdbcTemplate.queryForList(sql, key_game);
    }

    @Override
    public String generateAndSaveMd5Key(int contractor_games_id, int count, boolean isConstantine) {
        String keyG;
        if (isConstantine) {
            keyG = KEYFORONCECONSTANTINE;
        } else {
            keyG = getKeyToday(contractor_games_id);
        }
        String sql = "insert into arena_info.generated_contractor_key (key_game, contractor_games_id, count) values ('" + keyG + "'," + contractor_games_id + "," + count + ");";
        try {
            jdbcTemplate.execute(sql);
            return keyG;
        } catch (Exception e) {
            return "error";
        }
    }

    @Override
    public boolean decreaseCountKey(int generated_contractor_key_id) {

        String sql = "update arena_info.generated_contractor_key set count=count-1 where id=" + generated_contractor_key_id;
        try {
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
