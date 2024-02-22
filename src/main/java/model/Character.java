package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Character {
    @JsonProperty("health") protected double health;
    @JsonProperty("attack") protected double attack;
    @JsonProperty("goldAmount") protected int goldAmount;
    @JsonProperty("description") protected String description;
    @JsonProperty("name") protected String name;
    @JsonProperty("currX") protected int currX = 0;
    @JsonProperty("currY") protected int currY = 0;

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
    
    public String toString(){
        return "[name = " + getName() + ", health = " + getHealth() + ", attack = " + getAttack() + "]";
    }
}
