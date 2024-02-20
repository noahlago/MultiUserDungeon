package model.Tiles;

import model.Visitor;

@SuppressWarnings("unused")
public class CharacterTile implements Tile{
    private Character character;

    public CharacterTile(Character character){
        this.character = character;
    }
    
    @Override
    public void accept(Visitor visitor) {

        //Double dispatch, object calls visitors proper method
        visitor.visitCharacterTile(this);
    }

    @Override
    public String toString() {
        return "[ i ]";
    }
}