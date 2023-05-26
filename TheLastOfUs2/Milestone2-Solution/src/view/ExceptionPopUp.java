package view;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class ExceptionPopUp {
    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setResizable(false);

        Label l = new Label();
        l.setText(message);
        l.setTextFill(Paint.valueOf("#FF0000"));
        Button button = new Button("Okay");
        button.setOnAction(e -> window.close());

        VBox v = new VBox(10);
        v.getChildren().addAll(l, button);
        v.setAlignment(Pos.TOP_CENTER);

        Scene s = new Scene(v);
        window.setScene(s);
        window.showAndWait();

    }
}
