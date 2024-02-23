package model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.Chest;
import model.Visitor;
import model.Character;

/**
 * THIS CLASS IS PURELY FOR DATA STORAGE PURPOSES
 * DO NOT INSTANTIATE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConcreteTile implements Tile{
    @JsonProperty("description") private String description = "description";
    @JsonProperty("type") private String type = "";
    @JsonProperty("character") private Character character = null;
    @JsonProperty("chest") private Chest chest = null;
    @JsonProperty("row") private int row = -1;
    @JsonProperty("col") private int col = -1;
    @JsonProperty("armed") private boolean armed = false;
    @JsonProperty("name") private String name = "";

    @JsonCreator
    public ConcreteTile(){}

    @JsonCreator
    public ConcreteTile(@JsonProperty("description") String description, @JsonProperty("type") String type, @JsonProperty("character") Character character, @JsonProperty("chest") Chest chest, @JsonProperty("row") int row, @JsonProperty("col") int col, @JsonProperty("armed") boolean armed, @JsonProperty("name") String name){
        this.description = description;
        this.type = type;
        this.character = character;
        this.chest = chest;
        this.row = row;
        this.col = col;
        this.armed = armed; 
        this.name = name;
    };


    @Override
    public void accept(Visitor visitor) {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    public String getDescription() {
        return description;
    }

    public String getType(){
        return this.type;
    }


    public Character getCharacter() {
        return character;
    }


    public Chest getChest() {
        return chest;
    }


    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }


    public boolean isArmed() {
        return armed;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

} 
