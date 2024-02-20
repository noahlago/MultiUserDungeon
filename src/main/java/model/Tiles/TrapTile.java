package model.Tiles;

import model.Visitor;

@SuppressWarnings("unused")
public class TrapTile implements Tile{
    private String name;
    private String description;
    private boolean armed;

    public TrapTile(String name, String description){
        this.name = name;
        this.description = description;
        this.armed = true;
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitTrapTile(this);
    }

    @Override
    public String toString() {
        return "[ T ]";
    }
}
