package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Visitor;

@SuppressWarnings("unused")
public class ObstacleTile implements Tile{
    @JsonProperty("name") private String name;

    public ObstacleTile(String name){
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitObstacleTile(this);

    }

    @Override
    public String toString() {
        return "[ O ]";
    }
}
