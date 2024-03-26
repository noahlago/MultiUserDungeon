package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.Inventory;
import model.Item;
import model.ItemType;
import model.MUD;
import model.Map;
import model.persistence.GameFileDAO;

public class PTUI {
    private MUD game;
    public static Scanner scanner = new Scanner(System.in);


    public PTUI(MUD game){
        this.game = game;
    }

    public void renderRooms(){
        this.game.renderRooms();
    }

    public void printControls(){
        System.out.println("Controls:");
        System.out.println("'w' -> move up");
        System.out.println("'s' -> move down");
        System.out.println("'a' -> move left");
        System.out.println("'d' -> move right");
        System.out.println("Press ENTER after each command. ");
    }

    public void printHelp(){
        System.out.println("Help:");
        System.out.println("'m': print map");
        System.out.println("'l': print map legend");
        System.out.println("'c': print control menu");
        System.out.println("'e': exit to main menu");
        System.out.println("'q': quit game completely");
        System.out.println("'i': print inventory");
        System.out.println("'u': edit inventory");
    }

    public void printLegend(){
        System.out.println("Map Legend:");
        System.out.println("'i': player character (you)");
        System.out.println("'!': enemy character");
        System.out.println("'C': chest");
        System.out.println("'O': obstacle");
        System.out.println("'E': exit tile");
        System.out.println("'T': trap");
    }

    public static int chooseItem(){
        String input = scanner.next();
        int itemNum;
        try{
            itemNum = Integer.parseInt(input);
        }catch(NumberFormatException e){
            System.out.println("Enter a valid #");
            return chooseItem();
        }
        return itemNum;
    }
    public void editInventory(){
        System.out.println("Enter 'd' to delete an item, or 'u' to equip/use an item: ");
        char command = scanner.next().charAt(0);
        if(command == 'd'){
            deleteItem();
        }else if(command == 'u'){
            useItem();
        }else{
            System.out.println("Invalid command. Try again. ");
        }
    }

    public void useItem(){
        Inventory inv = game.getInventory();
        ArrayList<Item> items = inv.items();
        int itemNum = 1;
        for(Item item : items){
            System.out.println(itemNum + ": " + item);
            itemNum++;
        }
        System.out.println("Enter the # of the item you wish to use/equip: ");
        int selection = scanner.nextInt() - 1;
        try{
            Item selectedItem = items.get(selection);
            ItemType type = selectedItem.getType();
            if(type == ItemType.ARMOR){
                this.game.equipArmor(selectedItem);
            }else if(type == ItemType.WEAPON){
                this.game.equipWeapon(selectedItem);
            }else{
                this.game.useItem(selectedItem);
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println("Invalid item number. Try again.");
        }
    }

    public void deleteItem(){
        Inventory inv = game.getInventory();
        ArrayList<Item> items = inv.items();
        int itemNum = 1;
        for(Item item : items){
            System.out.println(itemNum + ": " + item);
            itemNum++;
        }
        System.out.println("Enter the # of the item you wish to remove: ");
        int selection = scanner.nextInt() - 1;
        try{
            Item selectedItem = items.get(selection);
            inv.remove(selectedItem);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Invalid item number. Try again. ");
        }
    }

    public static char visitMerchant(){
        System.out.println("What would you like to do?\n\'s\': sell item\n\'b\': buy item\n\'e\': exit");
        char selection = scanner.next().charAt(0);
        return selection;
    }

    /**
     * Main implementation of the PTUI, allowing the player to interact with the game, and control their character. 
     * @return whether or not to exit the game entirely (if not, exits to main menu instead). 
     */
    public boolean playGame() {
        while(game != null && !(game.getGameOver())) {
            System.out.println("Round: " + (game.getNumTurns())); //add round count from MUD instance
            System.out.println("Current Health: " + game.getHealth());
            System.out.println(this.game.closeTilesString());
            System.out.println("Enter a command or 'h' for a help menu: ");
            char command = scanner.next().charAt(0);
            switch(command){
                case 'h':
                    this.printHelp();
                case 'c':
                    this.printControls();
                case 'w':
                    game.movePlayer(-1, 0);
                    break;
                case 's':
                    game.movePlayer(1, 0);
                    break;
                case 'a':
                    game.movePlayer(0, -1);
                    break;
                case 'd':
                    game.movePlayer(0, 1);
                    break;
                case 'm':
                    System.out.println(game.toString());
                    break;
                case 'l':
                    printLegend();
                    break;
                case 'i':
                    System.out.println("Gold: $" + this.game.getPlayerGold());
                    System.out.println(this.game.inventoryString());
                    break;
                case 'u':
                    editInventory();
                    break;
                case 'e':
                    return false;
                case 'q':
                    return true;
                default:
                    this.printHelp();
            }
        }
        if(game == null){
            System.out.println("GAME IS NULL!");
        }
        return !game.getGameOver();
    }

    public static void main(String[] args) throws IOException {
        GameFileDAO saveManager = new GameFileDAO();
        boolean exit = false;
        while(!exit){
            System.out.println("Welcome to the ultimate Multi-User Dungeon!");
            System.out.println("Enter 's' to load a previous game file, 'd' to delete a saved game, or 'n' for a new game. Or enter 'x' to exit the main menu.");
            PTUI currentGame = new PTUI(null);

            String input = scanner.nextLine();
            char command = 'a';
            if(input.length() > 0){
                command = input.charAt(0);
            }
            String gameName;


            switch(command){
                case 'n':
                    System.out.println("Enter your character's name for the new game:");
                    System.out.println("(Note: entering a previously used name will overwrite that save)");
                    gameName = scanner.nextLine();
                    Map map = new Map();
                    MUD game = new MUD(map,gameName);
                    map.setPlayer(game.getPlayer());
                    currentGame = new PTUI(game);
                    saveManager.newSaveGame(game);
                    exit = currentGame.playGame();
                    saveManager.updateSaveGame(game);
                    saveManager.save();
                    break;
                case 'd':
                    System.out.println("Enter the name of the saved game you want to delete:");
                    gameName = scanner.nextLine();
                    try {
                        saveManager.deleteSaveGame(gameName);
                        saveManager.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 's':
                    try {
                        HashMap<String,MUD> allGames = saveManager.getGames();
                        for(String name : allGames.keySet()){
                            System.out.println(name);
                        }
                        System.out.println("Enter the name of the save file you want to load:");
                        gameName = scanner.nextLine();
                        if(allGames.containsKey(gameName)){
                            currentGame = new PTUI(allGames.get(gameName));
                            currentGame.renderRooms();
                            exit = currentGame.playGame();
                            saveManager.updateSaveGame(currentGame.game);
                            saveManager.save();
                        }
                        else{
                            throw new IOException();
                        }
                    } catch (IOException e) {
                        System.out.println("Saved game not found.");
                    }   
                    break;
                case 'x':
                    exit = true;
                    break;
                case 'a':
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
            // while(currentGame.playGame());
        }
    }
}
