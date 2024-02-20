package model.Tiles;

import model.Visitor;

@SuppressWarnings("unused")
public class ObstacleTile implements Tile{
    private String name;

    public ObstacleTile(String name){
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitObstacleTile(this);

    }
}
