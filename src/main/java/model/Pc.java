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

    public boolean useItem(Item item){
        if(item.getHealthPoints() > 0){
            inventory.remove(item);
            gainHealth(item.getHealthPoints());
            return true;
        }
        return false;
    }

    public boolean equipArmor(Item newArmor){
        if(inventory.remove(newArmor) == true && newArmor.getDefensePercent() != 0){
            this.armorSlot = newArmor;
            return true;
        }
        return false;
    }
    public boolean equipWeapon(Item newWeapon){
        if(newWeapon.getAttack() != 0 && inventory.remove(newWeapon) == true){
            this.weaponSlot = newWeapon;
            return true;
        }
        return false;
    }
    
    public boolean RemoveArmor(){
        if(armorSlot != null){
        this.inventory.add(armorSlot);
        this.armorSlot = null;
        return true;
        }
        return false;
    }
    public boolean RemoveWeapon(){
        if(weaponSlot != null){
        this.inventory.add(weaponSlot);
        this.weaponSlot = null;
        }
    }
    
    public void destroyItem(Item item){

        this.inventory.remove(item);
    }
}
