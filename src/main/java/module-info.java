module unitXX {
    requires com.fasterxml.jackson.databind;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.xml;
    
    exports model to com.fasterxml.jackson.databind;
    exports model.Tiles to com.fasterxml.jackson.databind;
    
    opens model.Tiles to com.fasterxml.jackson.databind;
    opens model to com.fasterxml.jackson.databind;

    opens unitXX to javafx.fxml;
    exports unitXX;
}
