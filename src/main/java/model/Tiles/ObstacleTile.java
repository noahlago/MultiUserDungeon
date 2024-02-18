package model.Tiles;

import model.Visitor;

public class ObstacleTile implements Tile{

    public ObstacleTile(){

    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitObstacleTile(this);

    }
}
