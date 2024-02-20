package model;

public class LameKnife extends Item{
    public LameKnife(String name, String description) {
        super(name, description);
        super.attackDamage += 5;
    }
}
