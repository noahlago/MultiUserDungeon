package model;

public class Pc extends Character {
    private Inventory inventory;
    protected Item weaponSlot;
    protected Item armorSlot;
    public Pc(double health, double attack, String name,Inventory inventory,int goldAmount){
        super(health,attack,name,goldAmount);
        this.inventory = inventory;
    }
    @Override
    public void takeDamage(double amount){
        if(armorSlot == null || armorSlot.getDefensePercent() == 0){
            health -= amount;
        }else{
            health = health - (amount * (1- armorSlot.getDefensePercent()));
        }
    }
    @Override
    public double getAttack(){
        if(weaponSlot == null || weaponSlot.getAttack() == 0){
            return attack;
        }else{
            return (attack + weaponSlot.getAttack());
        }
    }
}
