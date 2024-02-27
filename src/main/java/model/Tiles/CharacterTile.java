package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import model.Character;
import model.Npc;
import model.Visitor;


public class CharacterTile extends ConcreteTile{
    @JsonProperty("character")private Character character;
    @JsonProperty("row") private int row;
    @JsonProperty("col") private int col;

    @JsonCreator
    public CharacterTile(@JsonProperty("character")Character character, @JsonProperty("row") int row, @JsonProperty("col") int col){
        this.character = character;
        super.setType("CHARACTER");

        this.row = row;
        this.col = col;
    }
    
    @Override
    public void accept(Visitor visitor) {

        //Double dispatch, object calls visitors proper method
        visitor.visitCharacterTile(this);
    }

    @Override
    public String toString() {
        //If the character is an NPC display it as !
        if(this.character instanceof Npc){
            return "[ ! ]";
        }
        //if playable character display as i
        return "[ i ]";
    }

    public Character getCharacter(){
        return this.character;
    }

    @Override
    public int getRow(){
        return row;
    }

    @Override
    public int getCol(){
        return col;
    }
}