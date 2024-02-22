package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Visitor;

@SuppressWarnings("unused")
public class TrapTile implements Tile{
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("armed") private boolean armed;

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
