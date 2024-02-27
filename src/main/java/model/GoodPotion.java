package model;

public class GoodPotion extends Item {
    public GoodPotion(String name, String description){
        super(name, description, ItemType.CONSUMABLE);
        super.healthPoints += 5;
        super.attackDamage += 5;
        super.defensePercent += 5;
    }
}
