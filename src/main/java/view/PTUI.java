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
            System.out.println("Round: "); //add round count from MUD instance
            System.out.println("Enter a command or 'h' for a help menu: ");
            char command = scanner.next().charAt(0);
            switch(command){
                case 'h':
                    this.printHelp();
                case 'c':
                    this.printControls();
                case 'w':
                    //game.moveUp()
                    break;
                case 's':
                    //game.moveDown()
                    break;
                case 'a':
                    //game.moveLeft()
                    break;
                case 'd':
                    //game.moveRight()
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

    public static void main(String[] args) {
        GameFileDAO saveManager = new GameFileDAO();
        System.out.println("Welcome to the ultimate Multi-User Dungeon!");
        //Print a list of all saved games (with numerical ID)
        System.out.println("Enter 's' to load a previous game file, 'd' to delete a saved game, or 'n' for a new game.");
        PTUI currentGame = new PTUI(null);
        char command = scanner.nextLine().charAt(0);
        String gameName;

        switch(command){
            case 'n':
                System.out.println("Enter your character's name for the new game:");
                System.out.println("(Note: entering a previously used name will overwrite that save)");
                gameName = scanner.nextLine();
                currentGame = new PTUI(new MUD(new Map(), gameName));
                break;
            case 'd':
                System.out.println("Enter the name of the saved game you want to delete:");
                gameName = scanner.nextLine();
                try {
                    saveManager.deleteSaveGame(gameName);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case 's':
                try {
                    HashMap<String,MUD> allGames = saveManager.getGames();

                } catch (IOException e) {
                    System.out.println("Save file not found.");
                }   
                break;
        }

        currentGame.playGame();
    }
}
