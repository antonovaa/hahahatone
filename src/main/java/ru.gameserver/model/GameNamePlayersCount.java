package ru.gameserver.model;

public class GameNamePlayersCount {

    private String gameName;
    private int max_players;

    public GameNamePlayersCount(String gameName, int max_players) {
        this.gameName = gameName;
        this.max_players = max_players;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }
}
