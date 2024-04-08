package model;

public class GoodSteak extends Item {
    public GoodSteak(String name, String description){
        super(name, description, ItemType.CONSUMABLE, 10);
        super.healthPoints += 10;
    }
    
   
}
