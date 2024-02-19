package model;

public class Night implements Cycle{

    @Override
    public void switchState(Cycle cycle) {
        cycle = new Day();
    }

    @Override
    // enemy parameter
    public void modifyDiurnalEnemies() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyDiurnalEnemies'");

        // Diurnal creature stats are reduced by 10% (rounded down)
        // enemy.setStats(0.1)
    }

    @Override
    // enemy parameter
    public void modifyNocturnalEnemies() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyNocturnalEnemies'");

        // Nocturnal creature stats are increased by 20% (rounded up)
        // enemy.setStats(1.2)
    }
    
}
