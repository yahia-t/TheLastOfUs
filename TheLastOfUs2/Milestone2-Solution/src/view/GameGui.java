package view;
import javafx.application.*;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javax.swing.*;

public class GameGui extends Application {
        public void start(Stage primaryStage) throws Exception {
// Step 1
            Group root = new Group();
            Label l = new Label("Hello World!");
            root.getChildren().add(l);
            Scene s = new Scene(root, 1000, 800); // Step 2
            primaryStage.setScene(s); // Step 3
            primaryStage.setTitle("First FX Application");
            primaryStage.show();
        } // Step 4

    public static void main(String[] args) {
        launch(args); // Step 5
    }
}

