package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Npc extends Character {
    @JsonProperty("isNocturnal") protected boolean isNocturnal = false;
    @JsonProperty("baseHealth") protected double baseHealth;
    @JsonProperty("baseAttack") protected double baseAttack;

    public Npc(double health, double attack, String name,int goldAmount){
        super(health,attack,name,goldAmount);
        baseHealth = health;
        baseAttack = attack;
    }

    @Override
    public double getAttack(){
       return attack;
     }
     @Override
     public void editStats(double factor){
        attack = baseAttack * factor;
        health = baseHealth * factor;
     }
     public boolean getIsNocturnal(){
        return isNocturnal;
     }

     public double getBaseAttack() {
         return baseAttack;
     }

     public double getBaseHealth() {
         return baseHealth;
     }
     public double getHealth(){
        return health;
     }
     public String getName(){
        return name;
     }
     public int getGoldAmount(){
        return goldAmount;
     }
    
}