package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cycle {
    @JsonCreator
    public Cycle(){}
    /*
     * Interface for day/night cycle subsystem using state pattern
     */

    // switches from day to night / vice versa
    public Cycle switchState(Cycle cycle){
        return null;
    };

    // modifies diurnal enemy stats based on current state
    public void modifyDiurnalEnemies(Npc[] npcs){};
    
    // modifies nocturnal enemy stats based on current state
    public void modifyNocturnalEnemies(Npc[] npcs){};

    // prints current state
    public String toString(){
        return "";
    };
}
