package model;

public class LameRags extends Item{
    public LameRags(String name, String description){
        super(name, description, ItemType.ARMOR);
        super.defensePercent += 0.1;
    }
}
