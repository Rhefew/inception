package com.rhefew.cocdrive.activity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rodrigo on 24/11/14.
 */
public class WarInfo {
    private int war;
    private String status;
    private int count;
    private JSONArray members;
    private JSONArray votations;
    private JSONArray comments;



    public WarInfo(JSONObject o) {
        this.war = o.optInt("war");
        this.status = o.optString("status");
        this.count = o.optInt("count");
        this.members = o.optJSONArray("members");
        this.votations = o.optJSONArray("votations");
        this.comments = o.optJSONArray("comments");
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
}


