package unitXX;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.MUD;
import model.Map;
import model.Room;
import model.User;
import model.mudObserver;
import model.Tiles.ConcreteTile;
import model.persistence.GameFileDAO;
import model.persistence.ProfileDAO;
import model.persistence.ProfileCSVFileDAO;
import model.User;

public class mudGUI extends Application {
    User currentProf = new User("a","A");
    GameFileDAO saveManager = new GameFileDAO();
    ProfileCSVFileDAO profileDAO;
    
    MUD mud = new MUD(new Map(),"New Game");
    
    VBox accountContent = new VBox(10);
   
   
    Label newUsernameLabel = new Label("New Username:");
    TextField newUsernameTextField = new TextField();
    
    Label newPasswordLabel = new Label("New Password:");
    PasswordField newPasswordField = new PasswordField();
    
    Button confirmAccountButton = new Button("Confirm Account");
    @Override
    public void start(Stage stage) {
        try{
        saveManager.newSaveGame(mud);
        saveManager.updateSaveGame(mud);
        saveManager.save();
        }catch(IOException io){

        }
        VBox vbox = new VBox();
        Text t = new Text();
        t.setText("Multi User Dungeon");
        Button login = new Button("Log In");
        Button viewMaps = new Button("View Maps");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        Label usernameLabel = new Label("Username:");
        TextField usernameTextField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Popup popup = new Popup();
        VBox popupContent = new VBox(10); 
        popupContent.setStyle("-fx-background-color: #FFFFFF;");
        popupContent.setPadding(new Insets(10)); // Set some padding around the elements

        
        // Add the username and password fields to the VBox
        Button confirmButton = new Button("Confirm");
confirmButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

            try{
             profileDAO = new ProfileCSVFileDAO();
             profileDAO.logIn(username, password);
             currentProf = profileDAO.getUser(username);
             System.out.println(currentProf.getUsername());
             showLoggedIn(stage);
             popup.hide(); 

            }catch(IOException io){
                Label errorLabel = new Label("Incorrect username or Password");
                popupContent.getChildren().add(errorLabel);
            }
    }
});
        popupContent.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordField,confirmButton);

       
        popup.getContent().clear(); 
        popup.getContent().add(popupContent); 
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                if (!popup.isShowing())
                    popup.show(stage);
                else
                    popup.hide();
            }
        };
        EventHandler<ActionEvent> mapEvent = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                if (!popup.isShowing()){
                popup.getContent().clear(); 
                popup.getContent().addAll(displayTiles(new GridPane(),mud.getCurrentRoom())); 
                    popup.show(stage);
                }
                else{
                    popup.hide();
                }
            }
        };
        Button createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction(e -> showCreateAccountPopup(stage));
        login.setOnAction(event);
        viewMaps.setOnAction(mapEvent);
        vbox.getChildren().add(t);
        vbox.getChildren().add(login);
        vbox.getChildren().add(viewMaps);
        vbox.getChildren().add(createAccountButton);
        stage.setScene(new Scene(vbox));
        stage.setTitle("MUD Game");
        stage.show();
    }
    private void showCreateAccountPopup(Stage stage) {
        accountContent.setStyle("-fx-background-color: #FFFFFF;");
        accountContent.setPadding(new Insets(10));
        Popup accountPopup = new Popup();
        if (!accountPopup.isShowing()){
                accountPopup.show(stage);
                        
                accountPopup.getContent().clear();
                accountContent.getChildren().addAll(newUsernameLabel, newUsernameTextField, newPasswordLabel, newPasswordField, confirmAccountButton);

                accountPopup.getContent().add(accountContent);
        }
            else{
                accountPopup.hide();
            }
   
        
        confirmAccountButton.setOnAction(event -> {
            String newUsername = newUsernameTextField.getText();
            String newPassword = newPasswordField.getText();
            
            // Assuming ProfileCSVFileDAO has a method to create a new account
            try {
                profileDAO = new ProfileCSVFileDAO();
                for( User user : profileDAO.getAllUsers()){
                    if(user.getUsername() == newUsername){
                        Label error = new Label("Username already in use");
                        accountPopup.getContent().add(error);
                        throw new IOException("Already used");
                    }
                }
                
                profileDAO.newUser(new User(newUsername, newPassword));
                accountPopup.hide();
                
               
            } catch(IOException io) {
                io.printStackTrace();
            }
        });

      
        accountPopup.show(stage);
    }

    private void showLoggedIn(Stage stage){
        stage.close();
        Stage profileStage = new Stage();
        VBox vbox = new VBox(10); // Added spacing between elements for better layout
        Label goldLabel = new Label("Total Gold: " + currentProf.getTotalGold());
        Label gamesPlayed = new Label("Games Played: " + currentProf.getGamesPlayed());
        Label livesLost = new Label("Lives Lost: " + currentProf.getLivesLost());
        Label monstersKilled = new Label("Monsters Killed: " + currentProf.getMonstersKilled());
        Label itemsFound = new Label("Items Found: " + currentProf.getItemsFound());
    
        Button viewCurrentGamesButton = new Button("View Current Games");
        viewCurrentGamesButton.setOnAction(e -> viewCurrentGames());

        Button startNewGameButton = new Button("Start New Game");
        startNewGameButton.setOnAction(e -> startNewGame(profileStage));

        vbox.getChildren().addAll(new Label(currentProf.getUsername()), gamesPlayed, livesLost, monstersKilled, goldLabel, itemsFound, viewCurrentGamesButton, startNewGameButton);
    
        profileStage.setScene(new Scene(vbox));
        profileStage.show();
    }
    
    private void viewCurrentGames() {
        System.out.println("Viewing current games");
    }
    
    private void startNewGame(Stage stage) {
        stage.close();
        Stage newGameStage = new Stage();
        VBox layout = new VBox(10); 
        Button startEndlessGameButton = new Button("Start New Endless Game");
        startEndlessGameButton.setOnAction(e -> startEndlessGame());
    
        Button startRegularGameButton = new Button("Start New Regular Game");
        startRegularGameButton.setOnAction(e -> startRegularGame());
    
        Button joinEndlessGameButton = new Button("Join Current Endless Game");
        joinEndlessGameButton.setOnAction(e -> joinEndlessGame());
    
        layout.getChildren().addAll(startEndlessGameButton, startRegularGameButton, joinEndlessGameButton);
    
        Scene scene = new Scene(layout, 300, 200);
        newGameStage.setScene(scene);
        newGameStage.setTitle("Start New Game");
        newGameStage.show();
    }
    
    private void startEndlessGame() {
        System.out.println("Starting a new endless game...");
    }
    
    private void startRegularGame() {
        System.out.println("Starting a new regular game...");
    }
    
    private void joinEndlessGame() {
        System.out.println("Joining a current endless game...");
    }
    
    private Node[] displayTiles(GridPane gridPane, Room room) {
        ConcreteTile[][] tiles = room.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                // Rectangle rect = createTileRectangle(tiles[i][j]);
                // gridPane.add(rect, j, i); // Note: (columnIndex, rowIndex)
            }
        }
        return null;
    }

    private Rectangle createTileRectangle(ConcreteTile tile) {
        Rectangle rect = new Rectangle(20, 20); // Size of the square
        rect.setStroke(Color.BLACK); // Border color
        // Customize the fill color based on the tile type
        switch (tile.getType()) {
            case "CHARACTER":
                rect.setFill(Color.RED);
                break;
            case "CHEST":
                rect.setFill(Color.GOLD);
                break;
            case "EMPTY":
                rect.setFill(Color.WHITE);
                break;
            case "EXIT":
                rect.setFill(Color.GREEN);
                break;
            case "TRAP":
                rect.setFill(Color.ORANGE);
                break;
            default:
                rect.setFill(Color.GRAY);
                break;
        }
        return rect;
    }

    public void handle(ActionEvent event) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}

// class GUIupdater implements mudObserver{
// private mudGUI gui;

// public GUIupdater(MUD game){
// gui.moveCharacter(mud.)
// }
// }