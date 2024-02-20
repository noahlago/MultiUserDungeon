package model;

public abstract class Item  {
    protected String type;
    protected String name;
    protected double attackDamage;
    protected double healthPoints;
    protected double defensePercent;

    public Item(String name, String description) {
        this.name = name;
        this.attackDamage = 0.0;
        this.healthPoints = 0.0;
        defensePercent = 0.0;
    }

    public double getAttack() {
        return attackDamage;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public double getDefensePercent() {
        return defensePercent;
    }

}
