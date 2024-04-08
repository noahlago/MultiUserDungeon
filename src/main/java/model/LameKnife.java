package model;

public class LameKnife extends Item{
    public LameKnife(String name, String description) {
        super(name, description, ItemType.WEAPON, 15);
        super.attackDamage += 5;
    }
}
