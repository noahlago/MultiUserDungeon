package model;

public class Npc extends Character {
    boolean isNocturnal = false;
    public Npc(int health, double attack, String name,int goldAmount){
        super(health,attack,name,goldAmount);
    }

    @Override
    public double getAttack(int turnNumber){
        if(turnNumber - (turnNumber % 10) < 10){
            //is day
            if(isNocturnal == false){
                return (attack * 1.2);
            }else{
                return (attack / 1.2);
            }
        }else if((turnNumber - (turnNumber % 10) / 10 ) == 0){
            if(isNocturnal == false){
                return (attack * 1.2);
            }else{
                return (attack / 1.2);
            }
            }else{
                if(isNocturnal == true){
                    return (attack * 1.2);
                }else{
                    return (attack / 1.2);
                }
            }
     }
}