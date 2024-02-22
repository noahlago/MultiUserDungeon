package model;

public class CoolArmor extends Item {
    public CoolArmor(String name, String description){
        super(name, description);
        super.defensePercent += 0.50;
    }
}
