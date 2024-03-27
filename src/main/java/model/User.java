package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("gamesPlayed") private int gamesPlayed;
    @JsonProperty("livesLost") private int livesLost;
    @JsonProperty("monstersKilled") private int monstersKilled;
    @JsonProperty("totalGold") private int totalGold;
    @JsonProperty("itemsFound") private int itemsFound;
    @JsonProperty("gameInProgress") private MUD gameInProgress;
    @JsonProperty("loggedIn") private boolean loggedIn;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.gamesPlayed = 0;
        this.livesLost = 0;
        this.monstersKilled = 0;
        this.totalGold = 0;
        this.itemsFound = 0;
        this.gameInProgress = null;
        this.loggedIn = true;
    }

    public User(String username, String password, int gamesPlayed, int livesLost, int monstersKilled, int totalGold, int itemsFound){
        this.username = username;
        this.password = password;
        this.gamesPlayed = gamesPlayed;
        this.livesLost = livesLost;
        this.monstersKilled = monstersKilled;
        this.totalGold = totalGold;
        this.itemsFound = itemsFound;
        this.gameInProgress = null;
        this.loggedIn = false;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getGamesPlayed(){
        return gamesPlayed;
    }

    public int getLivesLost(){
        return livesLost;
    }

    public int getMonstersKilled(){
        return monstersKilled;
    }

    public int getTotalGold(){
        return totalGold;
    }

    public int getItemsFound(){
        return itemsFound;
    }

    public MUD getGameInProgress(){
        return gameInProgress;
    }

    public boolean getLoggedIn(){
        return loggedIn;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed += 1;
    }

    public void incrementLivesLost() {
        this.livesLost += 1;
    }

    public void incrementMonstersKilled() {
        this.monstersKilled += 1;
    }

    public void incrementItemsFound() {
        this.itemsFound += 1;
    }

    public void increaseTotalGold(int gold) {
        this.totalGold += gold;
    }

    /**
     * Stores an instance of MUD after users starts a new game
     * Overrides previous game in progress if one exists
     * @param game game started
     */
    public void startGame(MUD game){
        this.gameInProgress = game;
    }

    /**
     * Resets game to null
     */
    public void endGame(){
        this.gameInProgress = null;
    }

    /*
     * Log out of account
     */
    public void logOut(){
        this.loggedIn = false;
    }

    /**
     * Sets loggedIn to true if password is correct
     * @param enteredPassword password attempt
     * @return true if logged in successfully, false otherwise
     */
    public boolean logIn(String enteredPassword){
        if(enteredPassword == getPassword()){
            this.loggedIn = true;
            return true;
        }

        return false;
    }

    /**
     * Sets a new password if old password is entered correctly
     * @param newPassword updated password
     * @param oldPassword old password
     * @return true if successfully changed password, false otherwise
     */
    public boolean changePassword(String newPassword, String oldPassword) {
        if(oldPassword == getPassword()){
            this.password = newPassword;
            return true;
        }

        return false;
    }

    public void updateStats(int livesLost, int monstersKilled, int totalGold, int itemsFound){
        this.livesLost += livesLost;
        this.monstersKilled += monstersKilled;
        this.totalGold += totalGold;
        this.itemsFound += itemsFound;
    }

    public void setHashedPassword(String newPassword){
        this.password = newPassword;
    }

}
