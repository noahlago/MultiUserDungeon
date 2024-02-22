package model.Tiles;

import model.Visitor;

public class ExitTile implements Tile{
    private String name = "EXIT";

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

    public String getName(){
        return this.name;
    }
}
