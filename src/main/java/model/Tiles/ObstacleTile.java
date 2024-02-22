package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import model.Visitor;

@SuppressWarnings("unused")
public class ObstacleTile extends ConcreteTile{
    @JsonProperty("name") private String name;
    @JsonCreator
    public ObstacleTile(@JsonProperty("name")String name){
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

    public String getName() {
        return name;
    }

    
}
