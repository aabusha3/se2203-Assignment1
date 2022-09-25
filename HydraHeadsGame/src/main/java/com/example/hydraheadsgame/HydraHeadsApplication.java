package com.example.hydraheadsgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HydraHeadsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HydraHeadsApplication.class.getResource("HydraHeads-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 850);
        stage.setTitle("Hydra Head HackFest!");
        stage.getIcons().add(new Image("file:src/main/resources/com/example/hydraheadsgame/HydraIcon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}