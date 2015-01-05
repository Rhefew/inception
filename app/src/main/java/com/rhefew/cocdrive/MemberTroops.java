package com.rhefew.cocdrive;

import org.json.JSONObject;

/**
 * Created by rodrigo on 28/11/14.
 */
public class MemberTroops {
    private String member;
    private String troop;
    private int quantity;


    public MemberTroops(JSONObject o){
        this.member = o.optString("member");
        this.troop = o.optString("troop");
        this.quantity = o.optInt("quantity");
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTroop() {
        return troop;
    }

    public void setTroop(String troop) {
        this.troop = troop;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
