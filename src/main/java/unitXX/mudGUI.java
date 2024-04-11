package unitXX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Chest;
import model.EndlessMUD;
import model.Item;
import model.MUD;
import model.Map;
import model.Room;
import model.User;
import model.mudObserver;
import model.Tiles.ConcreteTile;
import model.Tiles.TrapTile;
import model.persistence.EndlessGameFileDAO;
import model.persistence.ExportProfile;
import model.persistence.GameFileDAO;
import model.persistence.ImportProfile;
import model.persistence.ProfileCSVFileDAO;
import model.Npc;
import model.Pc;
import model.PremadeMaps;

public class mudGUI extends Application implements mudObserver {
    User currentProf = new User("a", "A");
    GameFileDAO saveManager = new GameFileDAO();
    EndlessGameFileDAO endlessSaveManager = new EndlessGameFileDAO();
    ProfileCSVFileDAO profileDAO;

    MUD mud = new MUD(new Map(), "New Game");
    EndlessMUD endlessMUD = new EndlessMUD(new Map(), "New Game");
    Boolean endless = false;


    VBox accountContent = new VBox(10);

    Label newUsernameLabel = new Label("New Username:");
    TextField newUsernameTextField = new TextField();

    Label newPasswordLabel = new Label("New Password:");
    PasswordField newPasswordField = new PasswordField();

    Button confirmAccountButton = new Button("Confirm Account");
    Popup accountPopup = new Popup();
    Stage currentStage;
    Font dungeon = Font.loadFont("file:resources/fonts/Magical World.ttf", 85);
    TextField field = new TextField();
    Label messages = new Label();
    PremadeMaps maps = new PremadeMaps();
    int mapNum = 0;

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
        vbox.setSpacing(5);
        Text t = new Text();
        t.setText("Multi User Dungeon");
        t.setFont(dungeon);

        Button login = new Button("Log In");

        Button createAccountButton = new Button("Create Account");
        Button viewMaps = new Button("View Maps");
        Button importAccount = new Button("Import Account");
        Label usernameLabel = new Label("Username:");
        TextField usernameTextField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Popup popup = new Popup();
        VBox popupContent = new VBox(10);
        popupContent.setStyle("-fx-background-color: #FFFFFF;");
        popupContent.setPadding(new Insets(10));

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
                    displayPremade(stage);
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
        accountPopupContent.setPadding(new Insets(10));

        // Add the username and password fields to the VBox
        Button confirmAccountButton = new Button("Create");

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
        EventHandler<ActionEvent> csvButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ImportProfile importManager = new ImportProfile(profileDAO);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open CSV File");

                File file = fileChooser.showOpenDialog(stage);
                try {
                    if (file != null) {
                        String extension = "";
                        int i = file.getName().lastIndexOf('.');
                        if (i >= 0) {
                            extension = file.getName().substring(i + 1);
                        }
                        if (extension == "csv") {
                            importManager.importCSV(file.getAbsolutePath());
                        }
                    }
                } catch (IOException t) {
                }
                ;
            }
        };
        EventHandler<ActionEvent> xmlButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ImportProfile importManager = new ImportProfile(profileDAO);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open XML File");
                File file = fileChooser.showOpenDialog(stage);
                try {
                    if (file != null) {
                        String extension = "";
                        int i = file.getName().lastIndexOf('.');
                        if (i >= 0) {
                            extension = file.getName().substring(i + 1);
                        }
                        if (extension == "xml") {
                            importManager.importXML(file.getAbsolutePath());
                        }
                    }
                } catch (SAXException t) {
                } catch (IOException t) {
                } catch (ParserConfigurationException p) {
                }
                ;

            }
        };
        EventHandler<ActionEvent> jsonButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ImportProfile importManager = new ImportProfile(profileDAO);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open JSON File");
                File file = fileChooser.showOpenDialog(stage);
                try {
                    if (file != null) {
                        String extension = "";
                        int i = file.getName().lastIndexOf('.');
                        if (i >= 0) {
                            extension = file.getName().substring(i + 1);
                        }
                        if (extension == "json") {
                            importManager.importJSON(file.getAbsolutePath());
                        }
                    }
                } catch (IOException t) {
                }
                ;
            }
        };
        Popup iPopup = new Popup();
        VBox iPopupContent = new VBox(10);
        iPopupContent.setStyle("-fx-background-color: #FFFFFF;");
        iPopupContent.setPadding(new Insets(10));
        Button cButton = new Button("Import CSV");
        cButton.setOnAction(csvButton);
        Button xButton = new Button("Import XML");
        xButton.setOnAction(xmlButton);
        Button jButton = new Button("Import JSON");
        jButton.setOnAction(jsonButton);
        iPopupContent.getChildren().addAll(cButton, xButton, jButton);
        iPopup.getContent().clear();
        iPopup.getContent().add(iPopupContent);
        EventHandler<ActionEvent> iEvent = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                if (!iPopup.isShowing()) {
                    iPopup.show(stage);
                } else {
                    iPopup.hide();
                }
            }
        };

        createAccountButton.setOnAction(newEvent);
        importAccount.setOnAction(iEvent);
        login.setOnAction(event);
        viewMaps.setOnAction(mapEvent);
        vbox.getChildren().add(t);
        vbox.getChildren().add(login);
        vbox.getChildren().add(createAccountButton);
        vbox.getChildren().add(importAccount);
        vbox.getChildren().add(viewMaps);
        stage.setScene(new Scene(vbox, 500, 300));
        stage.setTitle("MUD Game");
        stage.show();
    }
    private void takeItem(Chest chest, Item item, Pc player){
        chest.remove(item);
        player.addItem(item);
    }

    public static void chestInteraction(Chest chest, Pc player) {
        ArrayList<Item> items = chest.getItems();
        
        // Create a new stage (or use an existing stage if part of a bigger application)
        Stage stage = new Stage();
        stage.setTitle("Chest Contents");
    
        // Create VBox to hold popup content
        VBox iPopupContent = new VBox(10);
        iPopupContent.setStyle("-fx-background-color: #FFFFFF;");
        iPopupContent.setPadding(new Insets(10));
    
        // Adding buttons for each item
        for (Item item : items) {
            System.out.println(item.getName());
            Button itemButton = new Button(item.getName());
            itemButton.setOnAction(event -> {
                // Implement your takeItem logic here
                System.out.println("Taking item: " + item.getName());
            });
            iPopupContent.getChildren().add(itemButton);
        }
    
        // Creating a scene with the VBox
        Scene scene = new Scene(iPopupContent);
        stage.setScene(scene);
    
        // Show the stage
        stage.show();
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
        Button exportAccount = new Button("Export Account");

        Button viewCurrentGamesButton = new Button("Continue Current Game");
        Popup iPopup = new Popup();
        VBox iPopupContent = new VBox(10);

        EventHandler<ActionEvent> iEvent = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                System.out.println("Adsfa");
                if (!iPopup.isShowing()) {

                    iPopup.show(profileStage);
                } else {
                    iPopup.hide();
                }
            }
        };
        iPopupContent.setStyle("-fx-background-color: #FFFFFF;");
        iPopupContent.setPadding(new Insets(10));

        EventHandler<ActionEvent> xmlButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ExportProfile exportManager = new ExportProfile(profileDAO);
                DirectoryChooser fileChooser = new DirectoryChooser();
                fileChooser.setTitle("Export XML File");
                File direct = fileChooser.showDialog(stage);
                try {
                    if (direct != null) {
                        try {
                            exportManager.exportXML(direct.getName(), currentProf.getUsername());
                        } catch (SAXException a) {
                        } catch (TransformerException y) {
                        }
                    }
                } catch (IOException t) {
                } catch (ParserConfigurationException p) {
                }
                ;

            }
        };
        EventHandler<ActionEvent> jsonButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ExportProfile exportManager = new ExportProfile(profileDAO);
                DirectoryChooser fileChooser = new DirectoryChooser();
                fileChooser.setTitle("Export JSON File");
                File direct = fileChooser.showDialog(stage);
                try {
                    if (direct != null) {
                        exportManager.exportJSON(direct.getName(), currentProf.getUsername());
                    }
                } catch (IOException t) {
                }
                ;

            }
        };
        EventHandler<ActionEvent> csvButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ExportProfile exportManager = new ExportProfile(profileDAO);
                DirectoryChooser fileChooser = new DirectoryChooser();
                fileChooser.setTitle("Export CSV File");
                File direct = fileChooser.showDialog(stage);
                try {
                    if (direct != null) {
                        exportManager.exportCSV(direct.getName(), currentProf.getUsername());
                    }
                } catch (IOException t) {
                }
                ;

            }
        };
        Button xButton = new Button("Export as XML");
        xButton.setOnAction(xmlButton);
        Button jButton = new Button("Export as JSON");
        jButton.setOnAction(jsonButton);
        Button cButton = new Button("Export as CSV");
        xButton.setOnAction(xmlButton);
        iPopupContent.getChildren().addAll(cButton, xButton, jButton);
        iPopup.getContent().clear();
        iPopup.getContent().add(iPopupContent);
        if (saveManager != null) {
            currentProf.setGameInProgress(saveManager);
        }

        viewCurrentGamesButton.setDisable(currentProf.getGameInProgress() == null);
        Stage newGameStage = new Stage();
        currentStage = newGameStage;
        viewCurrentGamesButton.setOnAction(e -> {
            startCurrentGame(currentProf.getUsername(), currentStage);
        });

        Button startNewGameButton = new Button("Start New Game");
        startNewGameButton.setOnAction(e -> {
            stage.close();
            Stage newStage = new Stage();
            currentStage = newStage;
            startNewGame(newStage);
            messages = new Label();
        });
        Button logout = new Button("Logout");
        logout.setOnAction(e -> start(profileStage));
        exportAccount.setOnAction(iEvent);
        vbox.getChildren().addAll(logout, new Label(currentProf.getUsername()), gamesPlayed, livesLost, monstersKilled,
                goldLabel, itemsFound, viewCurrentGamesButton, startNewGameButton, exportAccount);

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
        startEndlessGameButton.setOnAction(e -> {
            try {
                startEndlessGame(currentProf.getUsername(), newGameStage);
            } catch (IOException y) {
            }
            ;
        });

        Button startRegularGameButton = new Button("Start New Regular Game");
        startRegularGameButton.setOnAction(e -> {
            try {
                startGame(currentProf.getUsername(), newGameStage);
            } catch (IOException y) {
            }
            ;
        });
        Button startPremadeGameButton = new Button("Start on a premade Map");
        startPremadeGameButton.setOnAction(e -> {
                displayPremadeLoggedin(newGameStage);
            
        });

        Button joinEndlessGameButton = new Button("Join Current Endless Game");
        joinEndlessGameButton.setOnAction(e -> joinEndlessGame(newGameStage));

        layout.getChildren().addAll(backToProfileButton, startEndlessGameButton, startRegularGameButton,startPremadeGameButton,
                joinEndlessGameButton);

        Scene scene = new Scene(layout);
        newGameStage.setScene(scene);
        newGameStage.setTitle("Start New Game");
        messages = new Label();
        newGameStage.show();
    }

    private void startGame(String playerName, Stage gameStage) throws IOException {
        // Prepare the game environment as before
        mud = new MUD(new Map(), playerName);
        saveManager.newSaveGame(mud);
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
    private void startPremadeGame(String playerName, Stage gameStage,Map map) throws IOException {
        // Prepare the game environment as before
        mud = new MUD(map, playerName);
        saveManager.newSaveGame(mud);
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

    

    private void startCurrentGame(String playerName, Stage gameStage) {
        //currentProf.setGameInProgress(saveManager);
        mud = currentProf.getGameInProgress();
        mud.renderRooms();
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

    private void joinEndlessGame(Stage stage) {
        stage.close();
    }

    private void startEndlessGame(String playerName, Stage gameStage) throws IOException {
        endless = true;
        endlessMUD = new EndlessMUD(new Map(), playerName);
        endlessSaveManager.newSaveGame(endlessMUD);
        currentProf.startEndlessGame(endlessMUD);
        GridPane grid = new GridPane();
        displayTiles(grid, endlessMUD.getCurrentRoom());
        VBox box = new VBox();
        VBox keyDisplay = createKeyDisplay();

        Button backToProfileButton = new Button("Back to Profile");
        backToProfileButton.setOnAction(e -> {
            gameStage.close();

            showLoggedIn(new Stage());
        });

        // Add the grid (game environment) and the button to the VBox
        box.getChildren().addAll(backToProfileButton, grid);
        addEndlessControls(box, endlessMUD);
        box.getChildren().add(keyDisplay);
        // Adjust the scene and stage as before
        Scene gameScene = new Scene(box); // Adjust the size according to your needs
        gameStage.setScene(gameScene);
        gameStage.setTitle("Game: " + playerName); // Set a title for the window
        gameStage.show();
        
    }


    private void displayPremade(Stage gameStage) {
        GridPane grid = new GridPane();
        displayTiles(grid, maps.getMap(mapNum).getRooms().get(0));
        displayTilesRight(grid, maps.getMap(mapNum).getRooms().get(1));
        VBox box = new VBox();
        VBox keyDisplay = createKeyDisplay();

        Button backToProfileButton = new Button("Back to Start");
        Button nextButton = new Button("View Next Map");
        if(mapNum < 2){
            mapNum+=1;
        }else{
            mapNum = 0;
        }
        nextButton.setOnAction(e ->{
            displayPremade(gameStage);
        });
        backToProfileButton.setOnAction(e -> {
            gameStage.close();

            start(new Stage());
        });

        // Add the grid (game environment) and the button to the VBox
        box.getChildren().addAll(backToProfileButton, grid,nextButton);
        box.getChildren().add(keyDisplay);
        // Adjust the scene and stage as before
        Scene gameScene = new Scene(box); // Adjust the size according to your needs
        gameStage.setScene(gameScene);
        gameStage.setTitle("Game: " + mud.getName()); // Set a title for the window
        gameStage.show();
    }
    private void displayPremadeLoggedin(Stage gameStage) {
        GridPane grid = new GridPane();
        displayTiles(grid, maps.getMap(mapNum).getRooms().get(0));
        displayTilesRight(grid, maps.getMap(mapNum).getRooms().get(1));
        VBox box = new VBox();
        VBox keyDisplay = createKeyDisplay();
        int thisNum = mapNum;

        Button backToProfileButton = new Button("Back to Profile");
        Button nextButton = new Button("View Next Map");
        Button playButton = new Button("Play This Map");
        if(mapNum < 2){
            mapNum+=1;
        }else{
            mapNum = 0;
        }
        nextButton.setOnAction(e ->{
            displayPremadeLoggedin(gameStage);
        });
        backToProfileButton.setOnAction(e -> {
            gameStage.close();

            showLoggedIn(new Stage());
        });

        playButton.setOnAction(e ->{
            try {
                startPremadeGame(currentProf.getUsername(),gameStage,maps.getMap(thisNum));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        box.getChildren().addAll(backToProfileButton, grid,nextButton,playButton);
        box.getChildren().add(keyDisplay);
        // Adjust the scene and stage as before
        Scene gameScene = new Scene(box); // Adjust the size according to your needs
        gameStage.setScene(gameScene);
        gameStage.setTitle("Game: " + mud.getName()); // Set a title for the window
        gameStage.show();
    }

    private Node[] displayTiles(GridPane gridPane, Room room) {
        gridPane.getChildren().clear();
        ConcreteTile[][] tiles = room.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Rectangle rect = createTileRectangle(tiles[i][j]);
                gridPane.add(rect, j, i); // Note: (columnIndex, rowIndex)
            }
        }
        return null;
    }
    private Node[] displayTilesRight(GridPane gridPane, Room room) {

        
        ConcreteTile[][] tiles = room.getTiles();
        for (int j = 0; j < tiles[1].length+1; j++) {
            Rectangle rect = new Rectangle(5,5);
            rect.setFill(Color.WHITE);
            gridPane.add(rect, 11, j+tiles.length+3); // Note: (columnIndex, rowIndex)
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Rectangle rect = createTileRectangle(tiles[i][j]);
                gridPane.add(rect, j+tiles.length+3, i); // Note: (columnIndex, rowIndex)
            }
        }
        return null;
    }

    private VBox createKeyDisplay() {
        VBox keyDisplay = new VBox(5);
        keyDisplay.setPadding(new Insets(10, 0, 10, 0));

        Label title = new Label("Key:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
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
        keyDisplay.getChildren().addAll(title, characterLabel, npcLabel, chestLabel, exitLabel, trapLabel,
                obstacleLabel);

        return keyDisplay;
    }

    private VBox createTextBox() {
        VBox keyDisplay = new VBox(5);
        TextField field = new TextField();

        keyDisplay.getChildren().addAll();

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
            boolean gameOver = game.getGameOver();
            boolean gameWon = game.gameWon();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if(gameWon == true){
                messages = new Label("You won!");
                for(int stat : stats){
                    System.out.println(stat);
                }
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else if(gameOver == true){
                messages = new Label("Game over");
                for(int stat : stats){
                    System.out.println(stat);
                }
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(-1, 0);
                messages = new Label(cycle);
            }
            mudUpdated(mud);
        });
        downButton.setOnAction(e -> {
            boolean gameOver = game.getGameOver();
            boolean gameWon = game.gameWon();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if(gameWon == true){
                messages = new Label("You won!");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else if(gameOver == true){
                messages = new Label("Game over");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(1, 0);
                messages = new Label(cycle);
            }
            mudUpdated(mud);
        });
        leftButton.setOnAction(e -> {
            boolean gameOver = game.getGameOver();
            boolean gameWon = game.gameWon();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if(gameWon == true){
                messages = new Label("You won!");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else if(gameOver == true){
                messages = new Label("Game over");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(0, -1);
                messages = new Label(cycle);
            }
            mudUpdated(mud);
        });
        rightButton.setOnAction(e -> {
            boolean gameOver = game.getGameOver();
            boolean gameWon = game.gameWon();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if(gameWon == true){
                messages = new Label("You won!");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else if(gameOver == true){
                messages = new Label("Game over");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(0, 1);
                messages = new Label(cycle);
            }
            mudUpdated(mud);
        });

        // Add the movement controls GridPane to the bottom of the main layout
        mainLayout.getChildren().add(movementControls);
    }

    private void addEndlessControls(VBox mainLayout, EndlessMUD game) {
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
            boolean gameOver = game.getGameOver();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if(gameOver == true){
                messages = new Label("Game over");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(1, 0);
                messages = new Label(cycle);
            }
            endlessMudUpdated(endlessMUD);
        });
        downButton.setOnAction(e -> {
            boolean gameOver = game.getGameOver();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if(gameOver == true){
                messages = new Label("Game over");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(1, 0);
                messages = new Label(cycle);
            }
            endlessMudUpdated(endlessMUD);
        });
        leftButton.setOnAction(e -> {
            boolean gameOver = game.getGameOver();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if(gameOver == true){
                messages = new Label("Game over");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(0, -1);
                messages = new Label(cycle);
            }
            endlessMudUpdated(endlessMUD);
        });
        rightButton.setOnAction(e -> {
            boolean gameOver = game.getGameOver();
            String cycle = "It is currently " + game.getCycle();
            int[] stats = game.getPlayer().getStats();
            if (gameOver == true){
                messages = new Label("Game over");
                profileDAO.updateStats(game.getName(), stats[0], stats[1], stats[2], stats[3]);
            }
            else{
                game.movePlayer(0, 1);
                messages = new Label(cycle);
            }
            endlessMudUpdated(endlessMUD);
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
                TrapTile t = (TrapTile) (tile);
                if (t.getDiscovered()) {
                    rect.setFill(Color.ORANGE);
                } else {
                    rect.setFill(Color.WHITE);
                }

                break;

            case "OBSTACLE":
                rect.setFill(Color.BLACK);
                break;
            case "MERCHANT":
                rect.setFill(Color.DEEPPINK);
            case "SHRINE":
                rect.setFill(Color.CADETBLUE);
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
        GridPane grid = new GridPane();
        //System.out.println(board.getCurrentRoom());
        displayTiles(grid, board.getCurrentRoom());
        VBox box = new VBox();

        Button backToProfileButton = new Button("Back to Profile");
        backToProfileButton.setOnAction(e -> {
            currentStage.close();
            try {
                profileDAO.save();
                saveManager.updateSaveGame(board);
                saveManager.save();
            } catch (IOException a) {
            }
            ;
            showLoggedIn(new Stage());
        });
        box.getChildren().addAll(backToProfileButton, grid);
        addMovementControls(box, board);
        VBox keyDisplay = createKeyDisplay();
        box.getChildren().add(keyDisplay);
        box.getChildren().add(field);
        box.getChildren().add(messages);
        Scene gameScene = new Scene(box);
        currentStage.setScene(gameScene);
        mud = currentProf.getGameInProgress();
        currentStage.setTitle("Game: " + currentProf.getGameInProgress().getName()); // Set a title for the window
    }

    public void endlessMudUpdated(EndlessMUD board) {
        GridPane grid = new GridPane();
        //System.out.println(board.getCurrentRoom());
        displayTiles(grid, board.getCurrentRoom());
        VBox box = new VBox();

        Button backToProfileButton = new Button("Back to Profile");
        backToProfileButton.setOnAction(e -> {
            currentStage.close();
            try {
                profileDAO.save();
                endlessSaveManager.updateSaveGame(board);
                endlessSaveManager.save();
            } catch (IOException a) {
            }
            ;
            showLoggedIn(new Stage());
        });
        box.getChildren().addAll(backToProfileButton, grid);
        addEndlessControls(box, board);
        VBox keyDisplay = createKeyDisplay();
        box.getChildren().add(keyDisplay);
        box.getChildren().add(field);
        box.getChildren().add(messages);
        Scene gameScene = new Scene(box);
        currentStage.setScene(gameScene);
        endlessMUD = currentProf.getEndlessInProgress();
        currentStage.setTitle("Game: " + currentProf.getEndlessInProgress().getName()); // Set a title for the window
    }

    @Override
    public void textUpdated(String newText) {
        field.appendText(newText);
        messages = new Label(newText);
        System.out.println("adsfs");
        mudUpdated(mud);

    }

    public static void main(String[] args) {
        launch(args);
    }
}