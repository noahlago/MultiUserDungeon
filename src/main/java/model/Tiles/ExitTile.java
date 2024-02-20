package model.Tiles;

import model.Visitor;

public class ExitTile implements Tile{

    public ExitTile(){

    }
    
    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitExitTile(this);
        
    }

    @Override
    public String toString() {
        return "[ E ]";
    }
}
