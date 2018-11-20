package ru.gameserver.model;

public class ResponseGame {

    private int result;

    private String gameName;

    private String gameServerName;

    private int maxPlayers;

    public ResponseGame(int result, String gameName, String gameServerName, int maxPlayers) {
        this.result = result;
        this.gameName = gameName;
        this.gameServerName = gameServerName;
        this.maxPlayers = maxPlayers;
    }

    public ResponseGame(int result, String gameName, String gameServerName) {
        this.result = result;
        this.gameName = gameName;
        this.gameServerName = gameServerName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getGameServerName() {
        return gameServerName;
    }

    public void setGameServerName(String gameServerName) {
        this.gameServerName = gameServerName;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
