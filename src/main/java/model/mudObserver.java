package model;

import javafx.scene.layout.VBox;

public interface mudObserver {
    public void mudUpdated(MUD game);
    public void textUpdated(String newText);
}
