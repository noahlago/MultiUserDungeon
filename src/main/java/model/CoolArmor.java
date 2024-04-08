package model;

public class CoolArmor extends Item {
    public CoolArmor(String name, String description){
        super(name, description, ItemType.ARMOR, 50);
        super.defensePercent += 0.50;
    }
}
