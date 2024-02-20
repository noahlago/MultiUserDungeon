package model;

public abstract class CharacterOption extends Npc{
    protected Npc npc;
    protected String name;
    protected String description;
    protected int goldWorth;
    public CharacterOption(Npc npc, String name, String description, int goldWorth){
        super(npc.health, npc.attack, npc.name, npc.goldAmount);
        this.npc = npc;
        this.name = name;
        this.description = description;
        this.goldWorth = goldWorth;
        
    }
    public CharacterOption(String name, String description, int goldWorth){
        this.name = name;
        this.description = description;
        this.goldWorth = goldWorth;
        
    }

    @Override
    public double getHealth(){
        return super.getHealth();
    }

    @Override
    public double getAttack(){
        return super.getAttack();
    }
    @Override
    public void takeDamage(double damage){
        super.takeDamage(damage);
    }
}
