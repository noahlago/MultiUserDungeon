package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item  {
    @JsonProperty("type") protected ItemType type;
    @JsonProperty("name") protected String name;
    @JsonProperty("description") protected String description;
    @JsonProperty("attackDamage") protected double attackDamage;
    @JsonProperty("healthPoints") protected double healthPoints;
    @JsonProperty("defensePercent") protected double defensePercent;

    @JsonCreator 
    public Item(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("type") ItemType type) {
        this.name = name;
        this.attackDamage = 0.0;
        this.healthPoints = 0.0;
        this.defensePercent = 0.0;
        this.type = type;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public ItemType getType() {
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

    @Override
    public String toString() {
        if(this.attackDamage != 0){
            return this.name + " [damage: " + this.attackDamage + "]";
        }else if(this.healthPoints != 0){
            return this.name + " [hp: " + this.healthPoints + "]";
        }else{
            return this.name + " [defense: " +this.defensePercent + "%]";
        }
    }

}
