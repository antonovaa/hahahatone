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
    public boolean addContractor(String contractorName, String contractorPlace) {
        try {
            jdbcTemplate.execute("insert into arena_info.contractor (name, place) values ('" + contractorName + "','" + contractorPlace + "')");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addGame(String gameName,int maxPlayers) {
        try {
            jdbcTemplate.execute("insert into arena_info.games (game_name,max_players) values ('" + gameName + "','"+maxPlayers+"')");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addContractorGames(int contractorId, int gameId) {
        try {
            jdbcTemplate.execute("insert into arena_info.contractor_games (contractor_id, games_id) values (" + contractorId + "," + gameId + ")");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
