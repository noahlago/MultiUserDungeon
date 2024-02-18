package model.Tiles;

import model.Visitor;

public class TrapTile implements Tile{

    public TrapTile(){

    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitTrapTile(this);
        
    }
}
