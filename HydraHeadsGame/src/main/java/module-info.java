module com.example.hydraheadsgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.hydraheadsgame to javafx.fxml;
    exports com.example.hydraheadsgame;
}