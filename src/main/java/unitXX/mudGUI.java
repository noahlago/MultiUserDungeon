package unitXX;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import model.Npc;

public class mudGUI extends Application implements mudObserver {
    User currentProf = new User("a", "A");
    GameFileDAO saveManager = new GameFileDAO();
    ProfileCSVFileDAO profileDAO;

    MUD mud = new MUD(new Map(), "New Game");

    VBox accountContent = new VBox(10);

    Label newUsernameLabel = new Label("New Username:");
    TextField newUsernameTextField = new TextField();

    Label newPasswordLabel = new Label("New Password:");
    PasswordField newPasswordField = new PasswordField();

    Button confirmAccountButton = new Button("Confirm Account");
    Popup accountPopup = new Popup();
    Stage currentStage;
    Font dungeon = Font.loadFont("file:resources/fonts/Magical World.ttf", 45);

    @Override
    public void start(Stage stage) {
        try {
            saveManager.newSaveGame(mud);
            saveManager.updateSaveGame(mud);
            saveManager.save();
        } catch (IOException io) {

        }
        mud.setOnUpdate(this);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        Text t = new Text();
        t.setText("Multi User Dungeon");
        t.setFont(dungeon);

        Button login = new Button("Log In");

        Button createAccountButton = new Button("Create Account");
        Button viewMaps = new Button("View Maps");
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

        Label errorLabel = new Label("Incorrect username or Password");
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernameTextField.getText();
                String password = passwordField.getText();

                try {
                    profileDAO = new ProfileCSVFileDAO();
                    profileDAO.logIn(username, password);
                    currentProf = profileDAO.getUser(username);
                    System.out.println(currentProf.getUsername());
                    showLoggedIn(stage);
                    currentStage = stage;
                    popup.hide();

                } catch (IOException io) {

                    if (popupContent.getChildren().contains(errorLabel) != true) {
                        popupContent.getChildren().add(errorLabel);
                    }
                }
            }
        });
        popupContent.getChildren().clear(); // Clear before adding new children
        popupContent.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordField,
                confirmButton);

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
                if (!popup.isShowing()) {
                    popup.getContent().clear();
                    popup.getContent().addAll(displayTiles(new GridPane(), mud.getCurrentRoom()));
                    popup.show(stage);
                } else {
                    popup.hide();
                }
            }
        };

        Label newUsernameLabel = new Label("New Username:");
        TextField newUsernameTextField = new TextField();

        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();

        Popup accountPopup = new Popup();
        VBox accountPopupContent = new VBox(10);
        accountPopupContent.setStyle("-fx-background-color: #FFFFFF;");
        accountPopupContent.setPadding(new Insets(10)); // Set some padding around the elements

        // Add the username and password fields to the VBox
        Button confirmAccountButton = new Button("Create");

        Label error = new Label("Already in Use");
        confirmAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = newUsernameTextField.getText();
                String password = newPasswordField.getText();
                try {
                    profileDAO = new ProfileCSVFileDAO();
                    for (User user : profileDAO.getAllUsers()) {
                        if (user.getUsername() == username) {
                            Label error = new Label("Username already in use");
                            accountPopup.getContent().remove(error);
                            accountPopup.getContent().add(error);
                            throw new IOException("Already used");
                        }
                    }

                    profileDAO.newUser(new User(username, password));
                    profileDAO.logIn(username, password);
                    currentProf = profileDAO.getUser(username);
                    showLoggedIn(stage);
                    accountPopup.hide();

                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });
        accountPopupContent.getChildren().addAll(newUsernameLabel, newUsernameTextField, newPasswordLabel,
                newPasswordField, confirmAccountButton);

        accountPopup.getContent().clear();
        accountPopup.getContent().add(accountPopupContent);
        EventHandler<ActionEvent> newEvent = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                if (!accountPopup.isShowing())
                    accountPopup.show(stage);
                else
                    accountPopup.hide();
            }
        };

        createAccountButton.setOnAction(newEvent);
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

    private void confirmCreate() {
        confirmAccountButton.setOnAction(event -> {
            String newUsername = newUsernameTextField.getText();
            String newPassword = newPasswordField.getText();

            // Assuming ProfileCSVFileDAO has a method to create a new account
            try {
                profileDAO = new ProfileCSVFileDAO();
                for (User user : profileDAO.getAllUsers()) {
                    if (user.getUsername() == newUsername) {
                        Label error = new Label("Username already in use");
                        accountPopup.getContent().remove(error);
                        accountPopup.getContent().add(error);
                        throw new IOException("Already used");
                    }
                }

                profileDAO.newUser(new User(newUsername, newPassword));
                Label success = new Label("Account Created Successfully!");
                accountPopup.getContent().remove(success);
                accountPopup.getContent().add(success);
                accountPopup.hide();

            } catch (IOException io) {
                io.printStackTrace();
            }
        });
    }

    private void showLoggedIn(Stage stage) {
        stage.close();
        Stage profileStage = new Stage();
        currentStage = profileStage;
        VBox vbox = new VBox(10); // Added spacing between elements for better layout
        Label goldLabel = new Label("Total Gold: " + currentProf.getTotalGold());
        Label gamesPlayed = new Label("Games Played: " + currentProf.getGamesPlayed());
        Label livesLost = new Label("Lives Lost: " + currentProf.getLivesLost());
        Label monstersKilled = new Label("Monsters Killed: " + currentProf.getMonstersKilled());
        Label itemsFound = new Label("Items Found: " + currentProf.getItemsFound());

        Button viewCurrentGamesButton = new Button("Continue Current Game");
        System.out.println(currentProf.getGameInProgress());
        viewCurrentGamesButton.setDisable(currentProf.getGameInProgress() == null);
        viewCurrentGamesButton.setOnAction(e -> startCurrentGame(profileStage));

        Button startNewGameButton = new Button("Start New Game");
        startNewGameButton.setOnAction(e -> startNewGame(profileStage));
        Button logout = new Button("Logout");
        logout.setOnAction(e -> start(profileStage));

        vbox.getChildren().addAll(logout, new Label(currentProf.getUsername()), gamesPlayed, livesLost, monstersKilled,
                goldLabel, itemsFound, viewCurrentGamesButton, startNewGameButton);

        profileStage.setScene(new Scene(vbox));
        profileStage.show();
    }

    private void startNewGame(Stage stage) {
        stage.close();
        Stage newGameStage = new Stage();
        currentStage = newGameStage;
        Button backToProfileButton = new Button("Back to Profile");
        backToProfileButton.setOnAction(e -> {
            newGameStage.close();

            showLoggedIn(new Stage());
        });
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Button startEndlessGameButton = new Button("Start New Endless Game");
        startEndlessGameButton.setOnAction(e -> startEndlessGame(newGameStage));

        Button startRegularGameButton = new Button("Start New Regular Game");
        startRegularGameButton.setOnAction(e -> startRegularGame(newGameStage));

        Button joinEndlessGameButton = new Button("Join Current Endless Game");
        joinEndlessGameButton.setOnAction(e -> joinEndlessGame(newGameStage));

        layout.getChildren().addAll(backToProfileButton, startEndlessGameButton, startRegularGameButton,
                joinEndlessGameButton);

        Scene scene = new Scene(layout);
        newGameStage.setScene(scene);
        newGameStage.setTitle("Start New Game");
        newGameStage.show();
    }

    private void startEndlessGame(Stage stage) {
        stage.close();
        Stage newGameStage = new Stage();
        currentStage = newGameStage;
        VBox layout = new VBox(10);

        Scene scene = new Scene(layout);
        newGameStage.setScene(scene);
        newGameStage.setTitle("Start New Game");
        newGameStage.show();
    }

    private void startRegularGame(Stage stage) {
        stage.close();
        Stage regularGameStage = new Stage();
        currentStage = regularGameStage;
        VBox layout = new VBox(10); // Vertical layout with spacing of 10

        // Create a text field for the player to input a name

        TextField playerNameField = new TextField();
        playerNameField.setPromptText("Enter your name");

        // Create a button to start the game, initially disabled
        Button startGameButton = new Button("Start Game");
        startGameButton.setDisable(true);

        playerNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            startGameButton.setDisable(newValue.trim().isEmpty());
        });

        startGameButton.setOnAction(e -> {
            String playerName = playerNameField.getText().trim();
            if (!playerName.isEmpty()) {
                startGame(playerName, regularGameStage);
            }
        });

        layout.getChildren().addAll(new Label("Character Name"), playerNameField, startGameButton);

        Scene scene = new Scene(layout); // Adjust size as needed
        regularGameStage.setScene(scene);
        regularGameStage.setTitle("Start Regular Game");
        regularGameStage.show();
    }

    private void startGame(String playerName, Stage gameStage) {
        // Prepare the game environment as before
        mud = new MUD(new Map(), playerName);
        currentProf.startGame(mud);
        GridPane grid = new GridPane();
        displayTiles(grid, mud.getCurrentRoom());
        VBox box = new VBox();
        VBox keyDisplay = createKeyDisplay();
        
        Button backToProfileButton = new Button("Back to Profile");
        backToProfileButton.setOnAction(e -> {
            gameStage.close();

            showLoggedIn(new Stage());
        });

        // Add the grid (game environment) and the button to the VBox
        box.getChildren().addAll(backToProfileButton, grid);
        addMovementControls(box, mud);
        box.getChildren().add(keyDisplay);
        // Adjust the scene and stage as before
        Scene gameScene = new Scene(box); // Adjust the size according to your needs
        gameStage.setScene(gameScene);
        gameStage.setTitle("Game: " + playerName); // Set a title for the window
        gameStage.show();
    }

    private void startCurrentGame(Stage gameStage) {

        GridPane grid = new GridPane();
        displayTiles(grid, currentProf.getGameInProgress().getCurrentRoom());
        VBox box = new VBox();

        Button backToProfileButton = new Button("Back to Profile");
        backToProfileButton.setOnAction(e -> {
            gameStage.close();

            showLoggedIn(new Stage());
        });
        VBox keyDisplay = createKeyDisplay();
 

        // Add the grid (game environment) and the button to the VBox
        box.getChildren().addAll(grid, backToProfileButton);
        addMovementControls(box, currentProf.getGameInProgress());
        box.getChildren().add(keyDisplay);
        // Adjust the scene and stage as before
        Scene gameScene = new Scene(box); // Adjust the size according to your needs
        gameStage.setScene(gameScene);
        mud = currentProf.getGameInProgress();
        gameStage.setTitle("Game: " + currentProf.getGameInProgress().getName()); // Set a title for the window
        gameStage.show();
    }

    private void joinEndlessGame(Stage stage) {
        stage.close();
    }

    private Node[] displayTiles(GridPane gridPane, Room room) {
        ConcreteTile[][] tiles = room.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Rectangle rect = createTileRectangle(tiles[i][j]);
                gridPane.add(rect, j, i); // Note: (columnIndex, rowIndex)
            }
        }
        return null;
    }
    private VBox createKeyDisplay() {
        VBox keyDisplay = new VBox(5); // Vertical box with spacing of 5
        keyDisplay.setPadding(new Insets(10, 0, 10, 0)); // Add some padding for aesthetics
    
        Label title = new Label("Key:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    
        // Each entry in the key
        Label characterLabel = new Label("Character");
        characterLabel.setGraphic(new Rectangle(10, 10, Color.AQUAMARINE));
        Label npcLabel = new Label("NPC");
        npcLabel.setGraphic(new Rectangle(10, 10, Color.RED));
        Label chestLabel = new Label("Chest");
        chestLabel.setGraphic(new Rectangle(10, 10, Color.GOLD));
        Label exitLabel = new Label("Exit");
        exitLabel.setGraphic(new Rectangle(10, 10, Color.GREEN));
        Label trapLabel = new Label("Trap");
        trapLabel.setGraphic(new Rectangle(10, 10, Color.ORANGE));
        Label obstacleLabel = new Label("Obstacle");
        obstacleLabel.setGraphic(new Rectangle(10, 10, Color.BLACK));
    
        // Add all labels to the VBox
        keyDisplay.getChildren().addAll(title, characterLabel, npcLabel, chestLabel,exitLabel, trapLabel, obstacleLabel);
    
        return keyDisplay;
    }

    private void addMovementControls(VBox mainLayout, MUD game) {
        // Create a new GridPane for the movement controls
        GridPane movementControls = new GridPane();
        movementControls.setPadding(new Insets(10)); // Add some padding for aesthetics
        movementControls.setVgap(5); // Vertical gap between buttons
        movementControls.setHgap(5); // Horizontal gap between buttons

        // Create the movement buttons
        Button upButton = new Button("Up");
        Button downButton = new Button("Down");
        Button leftButton = new Button("Left");
        Button rightButton = new Button("Right");

        // Add the buttons to the GridPane
        movementControls.add(leftButton, 0, 1); // Column 0, Row 1
        movementControls.add(upButton, 1, 0); // Column 1, Row 0
        movementControls.add(downButton, 1, 2); // Column 1, Row 2
        movementControls.add(rightButton, 2, 1); // Column 2, Row 1

        // Align the GridPane to the center
        movementControls.setAlignment(Pos.CENTER);

        // Optionally set the maximum width of the buttons for a uniform look
        upButton.setMaxWidth(Double.MAX_VALUE);
        downButton.setMaxWidth(Double.MAX_VALUE);
        leftButton.setMaxWidth(Double.MAX_VALUE);
        rightButton.setMaxWidth(Double.MAX_VALUE);

        // Optionally set click event handlers for the buttons
        upButton.setOnAction(e -> {
            game.movePlayer(-1, 0);
            mudUpdated(mud);
        });
        downButton.setOnAction(e -> {
            game.movePlayer(1, 0);
            mudUpdated(mud);
        });
        leftButton.setOnAction(e -> {
            game.movePlayer(0, -1);
            mudUpdated(mud);
        });
        rightButton.setOnAction(e -> {
            game.movePlayer(0, 1);
            mudUpdated(mud);
        });

        // Add the movement controls GridPane to the bottom of the main layout
        mainLayout.getChildren().add(movementControls);
    }

    private Rectangle createTileRectangle(ConcreteTile tile) {
        Rectangle rect = new Rectangle(20, 20); // Size of the square
        rect.setStroke(Color.BLACK); // Border color
        // Customize the fill color based on the tile type
        switch (tile.getType()) {
            case "CHARACTER":
                if (tile.getCharacter() instanceof Npc) {
                    rect.setFill(Color.RED);
                } else {
                    rect.setFill(Color.AQUAMARINE);
                }

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
            case "OBSTACLE":
                rect.setFill(Color.BLACK);
                break;
            default:
                rect.setFill(Color.WHITE);
                break;
        }
        return rect;
    }

    public void handle(ActionEvent event) {

    }

    @Override
    public void mudUpdated(MUD board) {
        System.out.println("called");
        GridPane grid = new GridPane();
        displayTiles(grid, currentProf.getGameInProgress().getCurrentRoom());
        VBox box = new VBox();

        Button backToProfileButton = new Button("Back to Profile");
        backToProfileButton.setOnAction(e -> {
            currentStage.close();

            showLoggedIn(new Stage());
        });

        // Add the grid (game environment) and the button to the VBox
        box.getChildren().addAll(backToProfileButton, grid);
        addMovementControls(box, currentProf.getGameInProgress());
        VBox keyDisplay = createKeyDisplay();
        box.getChildren().add(keyDisplay);
        // Adjust the scene and stage as before
        Scene gameScene = new Scene(box); // Adjust the size according to your needs
        currentStage.setScene(gameScene);
        mud = currentProf.getGameInProgress();
        currentStage.setTitle("Game: " + currentProf.getGameInProgress().getName()); // Set a title for the window
    }

    public static void main(String[] args) {
        launch(args);
    }
}