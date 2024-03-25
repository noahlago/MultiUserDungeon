package model;

import java.util.*;


public class ItemGeneration {
        
    List<Item> master_list = new ArrayList<>();
    
    public ItemGeneration() {
        Item diamond_armor = new CoolArmor("Diamond Armor","Made by Steve"); 
        master_list.add(diamond_armor);
        Item excalibur = new CoolSword("Excalibur", "Really OP");
        master_list.add(excalibur);     

        for (int i = 0; i <= 4; i++) {
            Item peasent_rags = new LameRags("Peasent Rags", "It's better than nothing");
            master_list.add(peasent_rags);
            Item banana_suit = new LameRags("Banana Suit", "Not very protective, but at least you'll stand out!");
            master_list.add(banana_suit);
            Item dull_blade = new LameKnife("Dull Blade", "At least you won't cut yourself");
            master_list.add(dull_blade);
            Item flint_knife = new LameKnife("Flint Knife", "You can make a campfire with this");
            master_list.add(flint_knife);
            Item power_potion = new GoodPotion("Power Potion", "Very very super cool power potion");
            master_list.add(power_potion);
            Item good_steak = new GoodSteak("Steak", "Cooked to perfection");
            master_list.add(good_steak);
        }  
    }

    public List<Item> getRandomizedList() {

        Collections.shuffle(master_list);

        return master_list;
    }

    public List<Item> getSpecificNumberItems(int amount) {
        Collections.shuffle(master_list);
        List<Item> res = new ArrayList<>();
        for (int i = 0; i <= amount; i++) {
            Item item = master_list.get(i);
            res.add(item);
        }

        return res;
    }




}
