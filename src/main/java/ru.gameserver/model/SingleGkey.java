package ru.gameserver.model;

public class SingleGkey {

    private String place;

    private String gameName;

    private String key;

    private String mac;

    public SingleGkey(String place, String gameName, String key, String mac) {
        this.place = place;
        this.gameName = gameName;
        this.key = key;
        this.mac = mac;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}

