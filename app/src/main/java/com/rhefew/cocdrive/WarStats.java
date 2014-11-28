package com.rhefew.cocdrive;

import org.json.JSONObject;

/**
 * Created by rodrigo on 28/11/14.
 */
public class WarStats {
    private int war;
    private String against;
    private String result;
    private int maxstars;
    private String heroic_defense;
    private String heroic_attack;
    private int attacks_used;
    private int attacks_won;
    private int attacks_lost;
    private int attacks_remaining;
    private int three_stars;
    private int two_stars;
    private int one_star;
    private int new_stars_per_attack;
    private String average_destruction;
    private int average_duration;

    public WarStats(JSONObject o){
        this.war = o.optInt("war");
        this.against = o.optString("against");
        this.result = o.optString("result");
        this.maxstars = o.optInt("maxtars");
        this.heroic_defense = o.optString("heroic_defense");
        this.heroic_attack = o.optString("heroic_attack");
        this.attacks_used = o.optInt("attacks_used");
        this.attacks_won = o.optInt("attacks_won");
        this.attacks_lost = o.optInt("attacks_lost");
        this.attacks_remaining = o.optInt("attacks_remaining");
        this.three_stars = o.optInt("three_stars");
        this.two_stars = o.optInt("two_stars");
        this.one_star = o.optInt("one_star");
        this.new_stars_per_attack = o.optInt("new_stars_per_attack");
        this.average_destruction = o.optString("average_destruction");
        this.average_duration = o.optInt("average_duration");
    }

    public int getWar() {
        return war;
    }

    public void setWar(int war) {
        this.war = war;
    }

    public String getAgainst() {
        return against;
    }

    public void setAgainst(String against) {
        this.against = against;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getMaxstars() {
        return maxstars;
    }

    public void setMaxstars(int maxstars) {
        this.maxstars = maxstars;
    }

    public String getHeroic_defense() {
        return heroic_defense;
    }

    public void setHeroic_defense(String heroic_defense) {
        this.heroic_defense = heroic_defense;
    }

    public String getHeroic_attack() {
        return heroic_attack;
    }

    public void setHeroic_attack(String heroic_attack) {
        this.heroic_attack = heroic_attack;
    }

    public int getAttacks_used() {
        return attacks_used;
    }

    public void setAttacks_used(int attacks_used) {
        this.attacks_used = attacks_used;
    }

    public int getAttacks_won() {
        return attacks_won;
    }

    public void setAttacks_won(int attacks_won) {
        this.attacks_won = attacks_won;
    }

    public int getAttacks_lost() {
        return attacks_lost;
    }

    public void setAttacks_lost(int attacks_lost) {
        this.attacks_lost = attacks_lost;
    }

    public int getAttacks_remaining() {
        return attacks_remaining;
    }

    public void setAttacks_remaining(int attacks_remaining) {
        this.attacks_remaining = attacks_remaining;
    }

    public int getThree_stars() {
        return three_stars;
    }

    public void setThree_stars(int three_stars) {
        this.three_stars = three_stars;
    }

    public int getTwo_stars() {
        return two_stars;
    }

    public void setTwo_stars(int two_stars) {
        this.two_stars = two_stars;
    }

    public int getOne_star() {
        return one_star;
    }

    public void setOne_star(int one_star) {
        this.one_star = one_star;
    }

    public int getNew_stars_per_attack() {
        return new_stars_per_attack;
    }

    public void setNew_stars_per_attack(int new_stars_per_attack) {
        this.new_stars_per_attack = new_stars_per_attack;
    }

    public String getAverage_destruction() {
        return average_destruction;
    }

    public void setAverage_destruction(String average_destruction) {
        this.average_destruction = average_destruction;
    }

    public int getAverage_duration() {
        return average_duration;
    }

    public void setAverage_duration(int average_duration) {
        this.average_duration = average_duration;
    }
}
