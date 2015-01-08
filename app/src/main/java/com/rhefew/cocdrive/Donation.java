package com.rhefew.cocdrive;

import java.util.ArrayList;

/**
 * Created by rodrigo on 07/01/15.
 */
public class Donation {

    private int capacity;
    private int current;
    private ArrayList<Troop> troops = new ArrayList<Troop>();

    public Donation(int capacity){
        this.capacity = capacity;
        this.current = 0;
    }

    public boolean setTroop(Troop troop){
        boolean result;
        if(current + troop.getHousing() <= capacity){
            this.troops.add(troop);
            current+=troop.getHousing();
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }

    public boolean removeTroop(Troop troop){

        boolean result;
        if(troops.contains(troop)){
            this.troops.remove(troop);
            current-=troop.getHousing();
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }
}
