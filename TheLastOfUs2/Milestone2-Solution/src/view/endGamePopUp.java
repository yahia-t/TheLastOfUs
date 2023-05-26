package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class endGamePopUp {
    public static void display(Stage prim,String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label l = new Label();
        l.setText(message);
        l.setTextFill(Paint.valueOf("#FF0000"));

        window.setOnCloseRequest(event -> prim.close());

        Button button = new Button("close");
        button.setOnAction(e -> {
            window.close();
            prim.close();

        });



        VBox v = new VBox(10);
        v.getChildren().addAll(l, button);
        v.setAlignment(Pos.TOP_CENTER);

        Scene s = new Scene(v);
        window.setScene(s);
        window.showAndWait();
}
}
