package model;

public abstract class Item extends CharacterOption {
    private String type;
    private String name;
    private double attackDamage;
    private double healthPoints;

    public Item(String type, String name, double attackDamage, double healthPoints, String desString) {
        this.type = type;
        this.name = name;
        this.attackDamage = 0.0;
        this.healthPoints = 0.0;
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

    public void takeDamage(double damage) {
        healthPoints = healthPoints - damage;
    }

}
