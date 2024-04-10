package model;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Night extends Cycle{

    @JsonProperty("name")
    private String name = "night";
    @Override
    public Cycle switchState( @JsonProperty("cycle")Cycle cycle) {
        cycle = new Day();
        return cycle;
    }

    @Override
    // enemy parameter
    public void modifyDiurnalEnemies(Npc[] npcs) {
        for(Npc npc : npcs){
            if(npc.getIsNocturnal() == false){
                npc.editStats(0.8);
            } 
        }
    }   
    

    @Override
    // enemy parameter
    public void modifyNocturnalEnemies(Npc[] npcs) {
        for(Npc npc : npcs){
            if(npc.getIsNocturnal() == true){
                npc.editStats(1.2);
            } 
        }
    }
    public String getName(){
        return name;
    }

    public String toString(){
        return "Night";
    }
    
}
