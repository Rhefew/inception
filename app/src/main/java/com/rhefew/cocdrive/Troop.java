package com.rhefew.cocdrive;

/**
 * Created by rodrigo on 07/01/15.
 */
public class Troop {

    private String name;
    private int housing;
    private int troop_drawable;

    public Troop(Type troop){
        switch (troop){

            case Barbarian: {this.name = "Bárbaro"; this.housing = 1; this.troop_drawable = R.drawable.barbarian;}break;
            case Archer: {this.name = "Arquera"; this.housing = 1; this.troop_drawable = R.drawable.archer;}break;
            case Goblin: {this.name = "Duende"; this.housing = 1; this.troop_drawable = R.drawable.goblin;}break;
            case Giant: {this.name = "Gigante"; this.housing = 5; this.troop_drawable = R.drawable.giant;}break;
            case WallBreaker: {this.name = "Rompemuros"; this.housing = 2; this.troop_drawable = R.drawable.wallbreaker;}break;
            case Balloon: {this.name = "Globo"; this.housing = 5; this.troop_drawable = R.drawable.balloon;}break;
            case Wizard: {this.name = "Mago"; this.housing = 4; this.troop_drawable = R.drawable.wizard;}break;
            case Healer: {this.name = "Sanadora"; this.housing = 12; this.troop_drawable = R.drawable.healer;}break;
            case Dragon: {this.name = "Dragón"; this.housing = 20; this.troop_drawable = R.drawable.dragon;}break;
            case Pekka: {this.name = "P.E.K.K.A"; this.housing = 25; this.troop_drawable = R.drawable.pekka;}break;
            case Minion: {this.name = "Esbirro"; this.housing = 2; this.troop_drawable = R.drawable.minion;}break;
            case HogRider: {this.name = "Montapuercos"; this.housing = 5; this.troop_drawable = R.drawable.hogrider;}break;
            case Valkirie: {this.name = "Valkiria"; this.housing = 8; this.troop_drawable = R.drawable.valkirie;}break;
            case Golem: {this.name = "Golem"; this.housing = 30; this.troop_drawable = R.drawable.golem;}break;
            case Witch: {this.name = "Bruja"; this.housing = 12; this.troop_drawable = R.drawable.witch;}break;
            case LavaHound: {this.name = "Perro Infernal"; this.housing = 30; this.troop_drawable = R.drawable.lavahound;}break;

        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHousing() {
        return housing;
    }

    public void setHousing(int housing) {
        this.housing = housing;
    }

    public int getTroop_drawable() {
        return troop_drawable;
    }

    public void setTroop_drawable(int troop_drawable) {
        this.troop_drawable = troop_drawable;
    }

    public static enum Type{
        Barbarian,
        Archer,
        Goblin,
        Giant,
        WallBreaker,
        Balloon,
        Wizard,
        Healer,
        Dragon,
        Pekka,
        Minion,
        HogRider,
        Valkirie,
        Golem,
        Witch,
        LavaHound
    }
}
