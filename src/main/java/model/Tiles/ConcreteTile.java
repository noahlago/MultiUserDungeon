package model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import model.Visitor;

/**
 * THIS CLASS IS PURELY FOR DATA STORAGE PURPOSES
 * DO NOT INSTANTIATE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConcreteTile implements Tile{
    private String name = "TILE";
    private String description = "description";

    @JsonCreator
    public ConcreteTile(){};


    @Override
    public void accept(Visitor visitor) {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


} 
