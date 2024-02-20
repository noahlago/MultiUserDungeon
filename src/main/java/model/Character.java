package model;

public abstract class Character {
    protected double health;
    protected double attack;
    protected int goldAmount;
    protected String description;
    protected String name;
    



    public Character(double health, double attack, String name,int goldAmount){
        this.health = health;
        this.attack = attack;
        this.name = name;
        this.goldAmount = goldAmount;
    }

    public void takeDamage(double amount){
        health = health - amount;
    }

    public double getAttack() {
        return attack;
    }
    public String getDescription() {
        return description;
    }
    public double getHealth() {
        return health;
    }
    public String getName() {
        return name;
    }
    public void editStats(double factor){
        //Only needs implementation if NPC
    }
    

}
