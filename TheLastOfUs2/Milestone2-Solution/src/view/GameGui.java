package view;


import engine.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.scene.*;
import model.characters.*;
import model.collectibles.*;
import model.world.*;


import java.util.*;

public class GameGui extends Application {
    Stage window;
    Hero selectedHero;
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Last of Us");

        Game.loadHeroes("C:\\Users\\Admin\\Desktop\\Heroes.csv");


        HBox h = new HBox();
        ChoiceBox<String> heroChoice = new ChoiceBox<>();


        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            heroChoice.getItems().add(Game.availableHeroes.get(i).getName());
        }

        Button next = new Button("start");

        h.getChildren().addAll(heroChoice, next);
        heroChoice.setValue(Game.availableHeroes.get(0).getName());
        Scene window1 = new Scene(h, 200, 200);


        GridPane mainGrid = new GridPane();
        mainGrid.setGridLinesVisible(true);
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints column = new ColumnConstraints(50);
        RowConstraints row = new RowConstraints(35);


        for (int i = 0; i < 15; i++) {
            mainGrid.getColumnConstraints().add(i, column);
            mainGrid.getRowConstraints().add(i, row);
        }


        Button test = new Button("test");
        test.setOnAction(e -> ExceptionPopUp.display("Exception", "Bad Move"));
        test.setMaxSize(50, 35);
        mainGrid.add(test, 14, 14);


        BorderPane mainBorder = new BorderPane();
        mainBorder.setCenter(mainGrid);
        Scene window2 = new Scene(mainBorder, 1280, 720);



        next.setOnAction(e -> {
            int k;
            for (k = 0; k < Game.availableHeroes.size(); k++) {
                if (Game.availableHeroes.get(k).getName().equals(heroChoice.getValue())) {
                    break;
                }
            }
            Label hehe = new Label(Game.availableHeroes.get(k).getName());
            selectedHero = Game.availableHeroes.get(k);
            Game.startGame(Game.availableHeroes.get(k));

            for(int i = 0;i <Game.map.length;i++){
                for(int j = 0; j<Game.map[i].length;j++){
                    if(Game.map[i][j].isVisible()){
                        if(Game.map[i][j] instanceof CharacterCell &&
                                ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie) {
                                Button zombInGrid = new Button("Zombie");
                            int finalI = i;
                            int finalJ = j;
                            zombInGrid.setOnAction(zomEv -> {
                                selectedHero.setTarget(((CharacterCell)Game.map[finalI][finalJ]).getCharacter());
                                System.out.println(finalI + " " + finalJ);
                            });
                                mainGrid.add(zombInGrid, i, j);
                        }
                        else if(Game.map[i][j] instanceof CollectibleCell &&
                                ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply)
                            mainGrid.add(new Label("Supply"), i, j);
                        else if (Game.map[i][j] instanceof CollectibleCell &&
                                ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine)
                            mainGrid.add(new Label("Vaccine"), i, j);
                    }
                }
            }


            mainGrid.add(hehe,0,0);
            window.setScene(window2);
        });
        GridPane controls = new GridPane();
        Button special = new Button("Special");
        Button cure = new Button("Cure");
        Button nextHero = new Button("Next");
        Button prevHero = new Button("Previous");
        Button attackbut = new Button("Attack");
        Button up = new Button("Up");
        Button down = new Button("Down");
        Button left = new Button("Left");
        Button right = new Button("Right");
        ColumnConstraints column2 = new ColumnConstraints(320);
        RowConstraints row2 = new RowConstraints(195);
        controls.setGridLinesVisible(true);
        for (int i = 0; i < 4; i++) {
            controls.getColumnConstraints().add(i, column2);
            controls.getRowConstraints().add(i, row2);
        }
        mainBorder.setBottom(controls);
        VBox chooseHero = new VBox();
        chooseHero.getChildren().addAll(prevHero,nextHero);
        chooseHero.setAlignment(Pos.CENTER);
        controls.add(chooseHero , 0, 0);
        VBox chooseCollectible = new VBox();
        chooseCollectible.getChildren().addAll(special, cure);
        chooseCollectible.setAlignment(Pos.CENTER);
        controls.add(chooseCollectible , 1, 0);
        VBox atack = new VBox();
        atack.getChildren().add(attackbut);
        atack.setAlignment(Pos.CENTER);
        controls.add(atack, 2, 0);
        GridPane movement = new GridPane();
        movement.add(up, 1,0);
        movement.add(down, 1,2);
        movement.add(left, 0,1);
        movement.add(right, 2,1);
        movement.setAlignment(Pos.CENTER);
        controls.add(movement, 3,0);









        window.setScene(window1);
        window.show();
    }

    public static void main(String[] args) {
            launch(args); // Step 5
    }
}

