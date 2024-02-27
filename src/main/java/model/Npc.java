package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Random;

public class Npc extends Character {
    @JsonProperty("isNocturnal") protected boolean isNocturnal = false;
    @JsonProperty("baseHealth") protected double baseHealth;
    @JsonProperty("baseAttack") protected double baseAttack;

    @JsonCreator
    public Npc(@JsonProperty("health") double health, @JsonProperty("attack") double attack, @JsonProperty("name") String name, @JsonProperty("goldAmount") int goldAmount){
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