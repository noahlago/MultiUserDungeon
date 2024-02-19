package model.Tiles;

import model.Visitor;

public class ChestTile implements Tile{

    public ChestTile(){

    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitChestTile(this);
    }
}
