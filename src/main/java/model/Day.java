package model;

public class Day implements Cycle{

    @Override
    public void switchState(Cycle cycle) {
        cycle = new Night();
    }

    @Override
    //enemy parameter
    public void modifyDiurnalEnemies() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyDiurnalEnemies'");

        // Diurnal creature stats are increased by 10% (rounded up)
        // enemy.setStats(1.1)
    }

    @Override
    // enemy parameter
    public void modifyNocturnalEnemies() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyNocturnalEnemies'");

        // Nocturnal creature stats are reduced by 20% (rounded down)
        // enemy.setStats(0.2)
    }
    
}
