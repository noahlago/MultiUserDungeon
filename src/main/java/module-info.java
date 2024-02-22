module unitXX {
    requires com.fasterxml.jackson.databind;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    
    exports model to com.fasterxml.jackson.databind;

    opens unitXX to javafx.fxml;
    exports unitXX;
}
