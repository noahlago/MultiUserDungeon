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
import model.mudObserver;
import model.Tiles.ConcreteTile;
import model.persistence.GameFileDAO;

public class mudGUI extends Application {
    GameFileDAO saveManager = new GameFileDAO();
    MUD mud = new MUD(new Map(),"NEw Game");

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
        popupContent.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordField);

       
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
        login.setOnAction(event);
        viewMaps.setOnAction(mapEvent);
        vbox.getChildren().add(t);
        vbox.getChildren().add(login);
        vbox.getChildren().add(viewMaps);
        stage.setScene(new Scene(vbox));
        stage.setTitle("MUD Game");
        stage.show();
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