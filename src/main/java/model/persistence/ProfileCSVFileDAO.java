package model.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import model.User;

/**
 * This class handles the csv file storing all user profiles and their statistics and login info. 
 * Scanner is used to read from the file, storing all profiles in a HashMap. 
 * New profiles can be added, profiles can be deleted, and profiles can be accessed/logged in to. 
 * @author Noah Lago ndl3389@rit.edu
 */
public class ProfileCSVFileDAO implements ProfileDAO{
    /**HashMap containing all profiles that have been created, with their usernames as keys (no repeat usernames allowed) */
    private HashMap<String, User> profiles;
    /**File where all user profiles have been stored */
    private String filename;

    /**
     * Creates a new instance of the class, and loads all pre-existing profiles frome the profiles.csv file
     * @throws IOException if the file is not found when loading
     */
    public ProfileCSVFileDAO() throws IOException{
        this.filename = "data/profiles.csv";
        this.profiles = new HashMap<>();

        load();
    }

    /**
     * Loads all pre-existing user profiles and their associated statistics from the given profiles file
     * @throws IOException if the file was not found, or an error occurred while reading profiles
     */
    private void load() throws IOException{
        File file = new File(this.filename);
        Scanner scanner = new Scanner(file);
        scanner.nextLine(); //skips header line
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] values = line.split(",");
            //Creates a new user with the values from the CSV file, in order
            User user = new User(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]));
            profiles.put(values[0], user); //pairs User profiles to their usernames in a HashMap
        }
        scanner.close();
    }

    /**
     * Saves all current user profiles to the profiles.csv file, to update it. 
     * This method is called whenever an update is made to the user profiles, internally or externally. 
     * @throws IOException
     */
    public void save() throws IOException{
        File file = new File(filename);
        FileWriter writer = new FileWriter(file);
        writer.append("username,password,gamesPlayed,livesLost,totalGold,itemsFound\n");
        for(String username : profiles.keySet()){
            User user = profiles.get(username);
            writer.append(username + "," + user.getPassword() + "," + user.getGamesPlayed() + "," + user.getLivesLost() + "," + user.getMonstersKilled() + "," + user.getTotalGold() + "," + user.getItemsFound() + "\n");
        }

        writer.flush();
        writer.close();
    }

    /**
     * Returns all existing user profiles
     */
    @Override
    public Collection<User> getAllUsers() {
        return this.profiles.values();
    }

    /**
     * Gets a specific user, based off their username
     * @throws IOException if the username is not found in the save file
     * @return the User instance
     */
    @Override
    public User getUser(String username) throws IOException {
        if(profiles.containsKey(username)){
            return profiles.get(username);
        }else{
            throw new IOException("Username not found.");
        }
    }

    /**
     * Adds a new User profile to the HashMap
     * @param newUser the user instance to be added
     * @return false if the username is already used, true otherwise
     */
    @Override
    public boolean newUser(User newUser) {
        if(profiles.containsKey(newUser.getUsername())){
            return false;
        }else{
            this.profiles.put(newUser.getUsername(), newUser);
            return true;
        }
    }

    /**
     * Removes the User with the matching username from the saved profiles
     * @param username of the user to be removed
     * @return whether the user was found and removed
     */
    @Override
    public boolean deleteUser(String username) {
        if(!profiles.containsKey(username)){
            return false;
        }else{
            profiles.remove(username);
            return true;
        }
    }

    /**
     * Attempts to log in a user, with their username and password
     * @throws IOException if the username was not found in the saved profiles, or if the password does not match
     * @return the User instance if the user was successfully logged in
     */
    @Override
    public User logIn(String username, String password) throws IOException {
        User user = this.getUser(username);
        if(user.getPassword().equals(password)){
            return user;
        }else{
            throw new IOException("Incorrect password.");
        }
    }

    /**
     * This method allows for a user's stats to be updated when they complete a game in the premade map mode, or leave a game in the endless map mode
     * Increments the count of games played by one. 
     * Updates the remaining stats by the specified quantities, which were tracked by the game and sent when the game ended. 
     */
    @Override
    public boolean updateStats(String username, int livesLost, int monstersKilled, int totalGold, int itemsFound) {
        if(this.profiles.containsKey(username)){ //checks that a user with the given username exists 
            User user = profiles.get(username); //gets the user with the specified username 
            user.incrementGamesPlayed(); //increments gamesPlayed count
            user.updateStats(livesLost, monstersKilled, totalGold, itemsFound); //updates remaining stats
            return true;
        }else{
            System.out.println("Username not found. ");
            return false;
        }
    }
    
}
