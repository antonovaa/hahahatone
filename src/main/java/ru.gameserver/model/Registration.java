package ru.gameserver.model;

public class Registration {

    private String login;
    private String password;
    private String email;
    private String gameName;


    public Registration(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public Registration(String login, String password, String email, String gameName) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getLogin() {
        return login;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
