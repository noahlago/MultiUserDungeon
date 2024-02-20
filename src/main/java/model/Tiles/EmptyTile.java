package model.Tiles;

import model.Visitor;

public class EmptyTile implements Tile{

    public EmptyTile(){

    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitEmptyTile(this);
    }

    @Override
    public String toString() {
        return "[   ]";
    }
}
