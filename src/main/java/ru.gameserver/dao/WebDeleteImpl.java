package ru.gameserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class WebDeleteImpl implements WebDelete {



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WebDeleteImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean deleteGames(int games_id) {
        try {
            String sql = "select arena_info.delete_games("+games_id+")";
            jdbcTemplate.execute(sql);
            return true;
        }
        catch (Exception e){
            return  false;
        }
    }
}
