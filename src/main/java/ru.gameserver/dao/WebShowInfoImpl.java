package ru.gameserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class WebShowInfoImpl implements WebShowInfo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WebShowInfoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Map<String, Object>> getGamesLog(String date_begin,String date_end,int contractor_id,int game_id) {

        List<Map<String, Object>> gameInfoModels;
        String gameName="";
        try {
            gameName = jdbcTemplate.queryForList("select g.game_name from arena_info.games g where g.games_id=" + game_id, String.class).get(0);
        }
        catch (Exception ignored){
        }
        String sql = "Select * from arena_info.get_log_game(?,?,?,?)";
        gameInfoModels=jdbcTemplate.queryForList(sql,gameName, date_begin,date_end,game_id);

        return gameInfoModels;
    }

    @Override
    public List<Map<String, Object>> getGamesCrash(String date_begin,String date_end,int contractor_id,int game_id) {
        List<Map<String, Object>> gameInfoModels;
        String gameName="";
        try {
            gameName = jdbcTemplate.queryForList("select * from arena_info.games g where g.games_id=" + game_id, String.class).get(0);
        }
        catch (Exception ignored){
        }
        String sql = "Select * from arena_info.get_crash_game(?,?,?,?)";
        gameInfoModels=jdbcTemplate.queryForList(sql,gameName,date_begin,date_end,game_id);
        return gameInfoModels;
    }

    @Override
    public List<Map<String, Object>> getGamersGame(int gamesId) {
        try {
            return jdbcTemplate.queryForList("select * from arena_info.get_gamers_game(" + gamesId + ")");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getGames() {
        try {
            return jdbcTemplate.queryForList("select g.games_id,g.game_name from arena_info.games g");
        } catch (Exception e) {
            return null;
        }
    }

}
