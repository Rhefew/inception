package com.rhefew.cocdrive;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rodrigo on 24/11/14.
 */
public class WarInfo {
    private int count;
    private int war;
    private int maxstars;
    private String heroic_defense;
    private String heroic_attack;
    private int status_code;
    private String status;
    private String against;

    private JSONArray members;
    private JSONArray votations;
    private JSONArray comments;

    public WarInfo(JSONObject o) {
        this.war = o.optInt("war");
        this.status_code = o.optJSONObject("status").optInt("code");
        this.status = o.optJSONObject("status").optString("description");
        this.count = o.optInt("count");
        this.members = o.optJSONArray("members");
        this.votations = o.optJSONArray("votations");
        this.comments = o.optJSONArray("comments");
        this.against = o.optString("against");
        this.maxstars = o.optInt("maxstars");
        this.heroic_attack = o.optString("heroic_attack");
        this.heroic_defense = o.optString("heroic_defense");
    }

    public int getWar() {
        return war;
    }

    public void setWar(int war) {
        this.war = war;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JSONArray getMembers() {
        return members;
    }

    public void setMembers(JSONArray members) {
        this.members = members;
    }

    public JSONArray getVotations() {
        return votations;
    }

    public void setVotations(JSONArray votations) {
        this.votations = votations;
    }

    public JSONArray getComments() {
        return comments;
    }

    public void setComments(JSONArray comments) {
        this.comments = comments;
    }

    public String getAgainst() {
        return against;
    }

    public void setAgainst(String against) {
        this.against = against;
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

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public int getStatus_code() {
        return status_code;
    }
}


