package ru.gameserver.model;

public class Authorization {

    private String login;
    private String password;
    private String gameName;

    public Authorization(String login, String password, String gameName) {
        this.login = login;
        this.password = password;
        this.gameName = gameName;
    }

    public Authorization(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
