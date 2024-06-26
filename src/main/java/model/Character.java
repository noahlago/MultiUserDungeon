package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    @JsonProperty("health") protected double health;
    @JsonProperty("attack") protected double attack;
    @JsonProperty("goldAmount") protected int goldAmount;
    @JsonProperty("description") protected String description;
    @JsonProperty("name") protected String name;
    @JsonProperty("currX") protected int currX = 0;
    @JsonProperty("currY") protected int currY = 0;
    @JsonProperty("Type") protected String type = "NPC";

    @JsonProperty("inventory") private Inventory inventory = null;
    @JsonProperty("weaponSlot") protected Item weaponSlot = null;
    @JsonProperty("armorSlot") protected Item armorSlot = null;

    @JsonProperty("isNocturnal") protected boolean isNocturnal = false;
    @JsonProperty("baseHealth") protected double baseHealth = 100;
    @JsonProperty("baseAttack") protected double baseAttack = 100;

    @JsonCreator
    public Character( @JsonProperty("health")double health, @JsonProperty("attack") double attack,  @JsonProperty("name")String name, @JsonProperty("goldAmount")int goldAmount){
        this.health = health;
        this.attack = attack;
        this.name = name;
        this.goldAmount = goldAmount;
    }

    public String takeDamage(double amount){
        health = health - amount;
        return(getName() + " took " + amount + " damage. " + getName() + "'s health is now " + getHealth());
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
    public int getGold(){
        return goldAmount;
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

    public void updateLocation(int row, int col){
        this.currX = row;
        this.currY = col;
    }

    public void moveLeft(){
        currY -= 1;
        
    }
    public void moveRight(){
        currY += 1;
    }
    public void moveUp(){
        currX -= 1;
    }
    public void moveDown(){
        currX += 1;
    }
    
    public String toString(){
        return "[name = " + getName() + ", health = " + getHealth() + ", attack = " + getAttack() + "]";
    }

    public int[] getStats(){
        return null;
    }

    public String getType(){
        return type;
    }

    public Inventory getInventory(){
        return this.inventory;
    }

    public void setType(String type){
        this.type = type;
    }
}
