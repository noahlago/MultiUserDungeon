package model;

public class LameKnife extends Item{
    public LameKnife(String name, String description) {
        super(name, description, ItemType.WEAPON);
        super.attackDamage += 5;
    }
}
