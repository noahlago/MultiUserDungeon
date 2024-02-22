package model;

public class Day implements Cycle{

    @Override
    public void switchState(Cycle cycle) {
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
    
}
