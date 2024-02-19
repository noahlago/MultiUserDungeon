package model;

public abstract class Character {
    private int health;
    private int attack;
    private int goldAmount;
    private String description;
    private String name;
    



    public Character(int health, int attack, String name,int goldAmount){
        this.health = health;
        this.attack = attack;
        this.name = name;
        this.goldAmount = goldAmount;
    }

    public void takeDamage(int amount){
        health = health - amount;
    }

    public int getAttack() {
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
