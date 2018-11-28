package ru.gameserver.model;

public class Information {

    private int count_kill_monster;
    private int count_kill_boss_monster;
    private int count_kill_enemy_player;
    private int count_death;

    private int max_level;

    private int team_a;
    private int team_b;


    public Information(int count_kill_monster, int count_kill_boss_monster,
        int count_kill_enemy_player,
        int count_death, int max_level, int team_a, int team_b) {
        this.count_kill_monster = count_kill_monster;
        this.count_kill_boss_monster = count_kill_boss_monster;
        this.count_kill_enemy_player = count_kill_enemy_player;
        this.count_death = count_death;
        this.max_level = max_level;
        this.team_a = team_a;
        this.team_b = team_b;
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
}
