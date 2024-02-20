package model;

public class CoolSword extends Item {
    public CoolSword(String name, String description){
        super(name, description);
        super.attackDamage += 10;
    }
}
