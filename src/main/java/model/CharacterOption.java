package model;

public abstract class CharacterOption {
    private model.Character character;

    public String getDescription() {
        return character.getDescription();
    }

    public int getHealth() {
        return character.getHealth();
    }

    public String getName() {
        return character.getName();
    }

    public double getAttack() {
        return character.getAttack();
    }

    public void attack(model.Character enemy) {
        // attack logic
        int damage = (int)character.getAttack();
        enemy.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        character.takeDamage(damage);
    }
}
