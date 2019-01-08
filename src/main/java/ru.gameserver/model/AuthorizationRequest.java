package ru.gameserver.model;

import java.util.List;

public class AuthorizationRequest {

    private int registrated_id;
    private boolean additionally;

    private List<CharacterGamer> characterGamer;


    public AuthorizationRequest(int registrated_id,
        List<CharacterGamer> characterGamer) {
        this.registrated_id = registrated_id;
        this.characterGamer = characterGamer;
    }

    public AuthorizationRequest(int registrated_id, boolean additionally,
        List<CharacterGamer> characterGamer) {
        this.registrated_id = registrated_id;
        this.additionally = additionally;
        this.characterGamer = characterGamer;
    }

    public boolean isAdditionally() {
        return additionally;
    }

    public void setAdditionally(boolean additionally) {
        this.additionally = additionally;
    }

    public int getRegistrated_id() {
        return registrated_id;
    }

    public void setRegistrated_id(int registrated_id) {
        this.registrated_id = registrated_id;
    }

    public List<CharacterGamer> getCharacterGamer() {
        return characterGamer;
    }

    public void setCharacterGamer(List<CharacterGamer> characterGamer) {
        this.characterGamer = characterGamer;
    }
}
