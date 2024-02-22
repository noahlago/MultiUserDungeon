package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Day implements Cycle{
    @JsonProperty("name")
    private String name = "Day";

    @Override
    public void switchState( @JsonProperty("cycle")Cycle cycle) {
        cycle = new Night();
        
    }

    @Override
    //enemy parameter
    public void modifyDiurnalEnemies(Npc[] npcs) {
        for(Npc npc : npcs){
            if(npc.getIsNocturnal() == false){
                npc.editStats(1.2);
            } 
        }
    }

    @Override
    // enemy parameter
    public void modifyNocturnalEnemies(Npc[] npcs) {
        for(Npc npc : npcs){
            if(npc.getIsNocturnal() == true){
                npc.editStats(0.8);
            } 
        }
    }

    public String toString(){
        return "Day";
    }
    
    public String getName(){
        return this.name;
    }

}
