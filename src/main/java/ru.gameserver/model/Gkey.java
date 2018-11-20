package ru.gameserver.model;

public class Gkey {
    private String place;

    private String ip;

    private String key;

    private String mac;

    public Gkey() {
    }

    public Gkey(String place, String ip, String key, String mac) {
        this.place = place;
        this.ip = ip;
        this.key = key;
        this.mac = mac;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
