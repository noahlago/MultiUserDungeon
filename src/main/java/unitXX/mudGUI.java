package unitXX;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.MUD;
import model.mudObserver;
import model.persistence.GameFileDAO;

public class mudGUI extends Application{
    GameFileDAO saveManager = new GameFileDAO();
    @Override
    public void start(Stage stage){
        VBox vbox = new VBox();
        Text t = new Text();
        t.setText("Multi User Dungeon");
        Button login = new Button("Log In");
        Button viewMaps = new Button("View Maps");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //press stuff
            }
        });
        viewMaps.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //press stuff
            }
        });
        vbox.getChildren().add(t);
        vbox.getChildren().add(login);
        vbox.getChildren().add(viewMaps);
        stage.setScene(new Scene(vbox));
        stage.setTitle("MUD Game");
        stage.show();
    }

    public void handle(ActionEvent event){

    }

    public static void main(String[] args){
        launch(args);
    }
}

// class GUIupdater implements mudObserver{
//     private  mudGUI gui;

//     public GUIupdater(MUD game){
//         gui.moveCharacter(mud.)
//     }
// }