package model;

public class GoodSteak extends Item {
    public GoodSteak(String name, String description){
        super(name, description);
        super.healthPoints += 10;
    }
    
   
}
