package model;

public class Night implements Cycle{

    @Override
    public void switchState(Cycle cycle) {
        cycle = new Day();
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

    public String toString(){
        return "Night";
    }
    
}
