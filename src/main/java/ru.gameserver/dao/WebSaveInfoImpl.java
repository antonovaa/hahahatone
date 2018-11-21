package ru.gameserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WebSaveInfoImpl implements WebSaveInfo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WebSaveInfoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean addGame(String gameName) {
        try {
            jdbcTemplate.execute("insert into arena_info.games (game_name) values ('" + gameName + "')");
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
