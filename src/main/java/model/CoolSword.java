package model;

public class CoolSword extends Item {
    public CoolSword(String name, String description){
        super(name, description, ItemType.WEAPON);
        super.attackDamage += 10;
    }
}
