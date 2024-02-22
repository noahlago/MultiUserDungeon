package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import model.MUD;
import model.Map;
import model.persistence.GameFileDAO;

public class PTUI {
    private MUD game;
    public static Scanner scanner = new Scanner(System.in);


    public PTUI(MUD game){
        this.game = game;
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

    /**
     * Main implementation of the PTUI, allowing the player to interact with the game, and control their character. 
     * @return whether or not to exit the game entirely (if not, exits to main menu instead). 
     */
    public boolean playGame() {
        while(true) {
            System.out.println("Round: " + (game.getNumTurns() / 2)); //add round count from MUD instance
            System.out.println("Enter a command or 'h' for a help menu: ");
            char command = scanner.next().charAt(0);
            switch(command){
                case 'h':
                    this.printHelp();
                case 'c':
                    this.printControls();
                case 'w':
                    game.movePlayer(0, -1);
                    break;
                case 's':
                    game.movePlayer(0, 1);
                    break;
                case 'a':
                    game.movePlayer(-1, 0);
                    break;
                case 'd':
                    game.movePlayer(1, 0);
                    break;
                case 'm':
                    System.out.println(game.toString());
                    break;
                case 'l':
                    printLegend();
                    break;
                case 'e':
                    return false;
                case 'q':
                    return true;
                default:
                    this.printHelp();
            }
        }
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
                    break;
                case 'd':
                    System.out.println("Enter the name of the saved game you want to delete:");
                    gameName = scanner.nextLine();
                    try {
                        saveManager.deleteSaveGame(gameName);
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
            while(currentGame.playGame() == true);
        }
    }
}
