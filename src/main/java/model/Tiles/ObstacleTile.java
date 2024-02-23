package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import model.Visitor;

public class ObstacleTile extends ConcreteTile{
    @JsonProperty("name") private String name;
    @JsonProperty("type") private String type = "OBSTACLE";
    @JsonCreator
    public ObstacleTile(@JsonProperty("name")String name){
        this.name = name;
        super.setType("OBSTACLE");
    }

    public String getName() {
        return name;
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
