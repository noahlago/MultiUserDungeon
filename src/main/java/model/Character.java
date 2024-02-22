package model;

public abstract class Character {
    protected double health;
    protected double attack;
    protected int goldAmount;
    protected String description;
    protected String name;
    protected int currX = 0;
    protected int currY = 0;


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
    public void gainHealth(double amount){
        health += amount;
    }

    public int[] getLocation(){
        return new int[] {currX,currY};
    }

    public void moveLeft(){
        currX -= 1;
        
    }
    public void moveRight(){
        currX += 1;
    }
    public void moveUp(){
        currY =+ 1;
    }
    public void moveDown(){
        currY =- 1;
    }
    

}
