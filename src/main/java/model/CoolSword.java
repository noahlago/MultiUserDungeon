package model;

public class CoolSword extends Item {
    public CoolSword(String name, String description){
        super(name, description, ItemType.WEAPON, 30);
        super.attackDamage += 10;
    }
}
