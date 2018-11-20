package ru.gameserver.model;

public class AuthorizationRequest {

    private int registrated_id;

    private String info;


    public AuthorizationRequest(int id, String info) {
        this.registrated_id = id;
        this.info = info;
    }

    public int getRegistrated_id() {
        return registrated_id;
    }

    public void setRegistrated_id(int registrated_id) {
        this.registrated_id = registrated_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
