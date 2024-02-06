module unitXX {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens unitXX to javafx.fxml;
    exports unitXX;
}
