package model.Tiles;

import model.Visitor;

public class EmptyTile implements Tile{
    private String name = "empty";

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

    public String getName(){
        return this.name;
    }
}
