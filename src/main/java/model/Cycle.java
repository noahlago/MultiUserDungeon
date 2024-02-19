package model;

public interface Cycle {
    /*
     * Interface for day/night cycle subsystem using state pattern
     */

    // switches from day to night / vice versa
    public void switchState();

    // modifies diurnal enemy stats based on current state
    public void modifyDiurnalEnemies();
    
    // modifies nocturnal enemy stats based on current state
    public void modifyNocturnalEnemies();
}
