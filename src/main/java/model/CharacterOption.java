package model;

public abstract class CharacterOption extends Character{
    protected Character character;
    protected String name;
    protected String description;
    protected int goldWorth;
    public CharacterOption(Character character, String name, String description, int goldWorth){
        super(character.health, character.attack, character.name, character.goldAmount);
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
