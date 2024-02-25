package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Item  {
    @JsonProperty("type") protected String type;
    @JsonProperty("name") protected String name;
    @JsonProperty("attackDamage") protected double attackDamage;
    @JsonProperty("healthPoints") protected double healthPoints;
    @JsonProperty("defensePercent") protected double defensePercent;

    @JsonCreator 
    public Item(@JsonProperty("name") String name, @JsonProperty("description") String description) {
        this.name = name;
        this.attackDamage = 0.0;
        this.healthPoints = 0.0;
        defensePercent = 0.0;
    }

    public double getAttackDamage() {
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
