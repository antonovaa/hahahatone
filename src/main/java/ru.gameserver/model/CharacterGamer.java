package ru.gameserver.model;

public class CharacterGamer {


    private int characters_id;
    private int character_level;
    private int character_type;
    private int count_kill_monster;
    private int count_kill_boss_monster;
    private int count_kill_enemy_player;
    private int count_death;
    private int max_level;
    private int team_a;
    private int team_b;
    private String info;

    public CharacterGamer() {
    }

    public CharacterGamer(int characters_id, int character_level, int character_type,
        int count_kill_monster, int count_kill_boss_monster, int count_kill_enemy_player,
        int count_death, int max_level, int team_a, int team_b, String info) {
        this.characters_id = characters_id;
        this.character_level = character_level;
        this.character_type = character_type;
        this.count_kill_monster = count_kill_monster;
        this.count_kill_boss_monster = count_kill_boss_monster;
        this.count_kill_enemy_player = count_kill_enemy_player;
        this.count_death = count_death;
        this.max_level = max_level;
        this.team_a = team_a;
        this.team_b = team_b;
        this.info = info;
    }

    public int getCharacters_id() {
        return characters_id;
    }

    public void setCharacters_id(int characters_id) {
        this.characters_id = characters_id;
    }

    public int getCharacter_level() {
        return character_level;
    }

    public void setCharacter_level(int character_level) {
        this.character_level = character_level;
    }

    public int getCharacter_type() {
        return character_type;
    }

    public void setCharacter_type(int character_type) {
        this.character_type = character_type;
    }

    public int getCount_kill_monster() {
        return count_kill_monster;
    }

    public void setCount_kill_monster(int count_kill_monster) {
        this.count_kill_monster = count_kill_monster;
    }

    public int getCount_kill_boss_monster() {
        return count_kill_boss_monster;
    }

    public void setCount_kill_boss_monster(int count_kill_boss_monster) {
        this.count_kill_boss_monster = count_kill_boss_monster;
    }

    public int getCount_kill_enemy_player() {
        return count_kill_enemy_player;
    }

    public void setCount_kill_enemy_player(int count_kill_enemy_player) {
        this.count_kill_enemy_player = count_kill_enemy_player;
    }

    public int getCount_death() {
        return count_death;
    }

    public void setCount_death(int count_death) {
        this.count_death = count_death;
    }

    public int getMax_level() {
        return max_level;
    }

    public void setMax_level(int max_level) {
        this.max_level = max_level;
    }

    public int getTeam_a() {
        return team_a;
    }

    public void setTeam_a(int team_a) {
        this.team_a = team_a;
    }

    public int getTeam_b() {
        return team_b;
    }

    public void setTeam_b(int team_b) {
        this.team_b = team_b;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
