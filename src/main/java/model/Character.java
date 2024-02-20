package model;

public abstract class Character {
    protected int health;
    protected double attack;
    protected int goldAmount;
    protected String description;
    protected String name;
    



    public Character(int health, double attack, String name,int goldAmount){
        this.health = health;
        this.attack = attack;
        this.name = name;
        this.goldAmount = goldAmount;
    }

    public void takeDamage(int amount){
        health = health - amount;
    }

    public double getAttack(int turnNumber) {
        return attack;
    }
    public String getDescription() {
        return description;
    }
    public int getHealth() {
        return health;
    }
    public String getName() {
        return name;
    }
    

}
