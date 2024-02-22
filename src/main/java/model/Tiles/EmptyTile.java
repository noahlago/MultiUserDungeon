package model.Tiles;

import model.Visitor;

public class EmptyTile implements Tile{
    private int row;
    private int col;

    public EmptyTile(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitEmptyTile(this);
    }

    @Override
    public String toString() {
        return "[   ]";
    }
}
