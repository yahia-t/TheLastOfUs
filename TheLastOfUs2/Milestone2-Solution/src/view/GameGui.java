package view;


import engine.*;
import exceptions.MovementException;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;
import model.characters.*;
import model.collectibles.*;
import model.world.*;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

import java.util.concurrent.atomic.AtomicReference;

public class GameGui extends Application {
    Stage window;
    int selectedHeroI;
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Last of Us");
        // C:\college\projectM3\TheLastOfUs\TheLastOfUs2\Milestone2-Solution\Heroes.csv    yahia
        // C:\Users\Admin\Desktop\Heroes.csv       mohand
        Game.loadHeroes("C:\\college\\projectM3\\TheLastOfUs\\TheLastOfUs2\\Milestone2-Solution\\Heroes.csv");


        HBox h = new HBox();
        ChoiceBox<String> heroChoice = new ChoiceBox<>();


        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            heroChoice.getItems().add(Game.availableHeroes.get(i).getName());
        }

        Button next = new Button("start");

        h.getChildren().addAll(heroChoice, next);
        heroChoice.setValue(Game.availableHeroes.get(0).getName());
        VBox infoOfSelection = new VBox();
        Hero selectedStarterHero1 = Game.availableHeroes.get(0);
        int selectedIndex = 0;
        Label infostart = new Label("Name: " + selectedStarterHero1.getName() + "\n"
                + "Max Health Points: " + selectedStarterHero1.getMaxHp() + "\n"
                + "Type: " + selectedStarterHero1.getClass().getSimpleName() + "\n"
                + "Attack Damage: " + selectedStarterHero1.getAttackDmg() + "\n"
                + "Max Acrions: " + selectedStarterHero1.getMaxActions());

        infoOfSelection.getChildren().addAll(h,infostart);
        heroChoice.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            
            int k;
            for (k = 0; k < Game.availableHeroes.size(); k++) {
                if (Game.availableHeroes.get(k).getName().equals(newvalue)) {
                    break;
                }
            }
            Hero selectedStarterHero = Game.availableHeroes.get(k);
            Label info = new Label("Name: " + selectedStarterHero.getName() + "\n"
                    + "Max Health Points: " + selectedStarterHero.getMaxHp() + "\n"
                    + "Type: " + selectedStarterHero.getClass().getSimpleName() + "\n"
                    + "Attack Damage: " + selectedStarterHero.getAttackDmg() + "\n"
                    + "Max Acrions: " + selectedStarterHero.getMaxActions());

            infoOfSelection.getChildren().add(1, info);
            if(infoOfSelection.getChildren().get(2) !=  null)
                infoOfSelection.getChildren().remove(2);
        });

        Scene window1 = new Scene(infoOfSelection, 200, 200);

        GridPane mainGrid = new GridPane();
        mainGrid.setGridLinesVisible(true);
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints column = new ColumnConstraints(60);
        RowConstraints row = new RowConstraints(35);


        for (int i = 0; i < 15; i++) {
            mainGrid.getColumnConstraints().add(i, column);
            mainGrid.getRowConstraints().add(i, row);
        }


        Button test = new Button("test");
        test.setOnAction(e -> ExceptionPopUp.display("Exception", "Bad Move"));
        test.setMaxSize(65, 35);
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
            selectedHeroI = 0;
            Game.startGame(Game.availableHeroes.get(k));

            updateMapGui(mainGrid);


            mainGrid.add(hehe,0,14);
            window.setScene(window2);
        });
        GridPane controls = new GridPane();



        Button special = new Button("Special");
        special.setOnAction(event -> {
            try {
                Game.heroes.get(selectedHeroI).useSpecial();
                updateMapGui(mainGrid);
            } catch (NoAvailableResourcesException e1) {
                ExceptionPopUp.display( "bad move", e1.getMessage());
            } catch (InvalidTargetException e1) {
                ExceptionPopUp.display( "bad move", e1.getMessage());
            }
        });


        Button cure = new Button("Cure");
        cure.setOnAction(event -> {
            try {
                Game.heroes.get(selectedHeroI).cure();
                updateMapGui(mainGrid);
            } catch (NoAvailableResourcesException e1) {
                ExceptionPopUp.display( "bad move", e1.getMessage());
            } catch (InvalidTargetException e1) {
                ExceptionPopUp.display( "bad move", e1.getMessage());
            } catch (NotEnoughActionsException e1) {
                ExceptionPopUp.display( "bad move", e1.getMessage());
            }
        });


        Button nextHero = new Button("Next");
        nextHero.setOnAction(e -> {
            if (selectedHeroI < Game.heroes.size())
                selectedHeroI++;
            else
                selectedHeroI = 0;
        });


        Button prevHero = new Button("Previous");
        prevHero.setOnAction(e -> {
            if (selectedHeroI > 0)
                selectedHeroI--;
            else
                selectedHeroI = Game.heroes.size() - 1 ;
        });


        Button attackbut = new Button("Attack");
        attackbut.setOnAction(e -> {
            try {
                Game.heroes.get(selectedHeroI).attack();
                updateMapGui(mainGrid);
            }catch (NotEnoughActionsException e1){
                ExceptionPopUp.display( "bad move", e1.getMessage());

            }catch (InvalidTargetException e1){
                ExceptionPopUp.display( "bad move", e1.getMessage());
            }
        });


        Button up = new Button("Up");
        up.setOnAction(event -> {
            try {
                Game.heroes.get(selectedHeroI).move(Direction.RIGHT);
                Label l = new Label(Game.heroes.get(selectedHeroI).getName());
                mainGrid.add(l , (int) Game.heroes.get(selectedHeroI).getLocation().getX(), 14 - (int) Game.heroes.get(selectedHeroI).getLocation().getY());
                updateMapGui(mainGrid);
            } catch (MovementException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            } catch (NotEnoughActionsException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            }
        });


        Button down = new Button("Down");
        down.setOnAction(event -> {
            try {
                Game.heroes.get(selectedHeroI).move(Direction.LEFT);
                Label l = new Label(Game.heroes.get(selectedHeroI).getName());
                mainGrid.add(l , (int) Game.heroes.get(selectedHeroI).getLocation().getX(), 14 - (int) Game.heroes.get(selectedHeroI).getLocation().getY());
                updateMapGui(mainGrid);
            } catch (MovementException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            } catch (NotEnoughActionsException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            }
        });
        Button left = new Button("Left");
        left.setOnAction(event -> {
            try {
                Game.heroes.get(selectedHeroI).move(Direction.DOWN);
                Label l = new Label(Game.heroes.get(selectedHeroI).getName());
                mainGrid.add(l , (int) Game.heroes.get(selectedHeroI).getLocation().getX(), 14 - (int) Game.heroes.get(selectedHeroI).getLocation().getY());
                updateMapGui(mainGrid);
            } catch (MovementException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            } catch (NotEnoughActionsException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            }
        });
        Button right = new Button("Right");
        right.setOnAction(event -> {
            try {
                int x = (int) Game.heroes.get(selectedHeroI).getLocation().getX();
                int y = (int) Game.heroes.get(selectedHeroI).getLocation().getX();
                Game.heroes.get(selectedHeroI).move(Direction.UP);
                Label l = new Label(Game.heroes.get(selectedHeroI).getName());
                mainGrid.getChildren().remove(l);
                mainGrid.add(l , (int) Game.heroes.get(selectedHeroI).getLocation().getX(), 14 - (int) Game.heroes.get(selectedHeroI).getLocation().getY());


                updateMapGui(mainGrid);
            } catch (MovementException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            } catch (NotEnoughActionsException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            }
        });
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

    private void updateMapGui(GridPane mainGrid) {
        for(int i = 0;i <Game.map.length;i++){
            for(int j = 0; j<Game.map[i].length;j++){
                int k = 14-j;
                if(Game.map[i][j].isVisible()){
                    if(Game.map[i][j] instanceof CharacterCell &&
                            ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie) {
                        Button zombInGrid = new Button("Zombie");
                        int finalI = i;
                        int finalJ = k;
                        zombInGrid.setOnAction(zomEv -> {
                            Game.heroes.get(selectedHeroI).setTarget(((CharacterCell)Game.map[finalI][finalJ]).getCharacter());
                            System.out.println(finalI + " " + finalJ);
                        });
                        mainGrid.add(zombInGrid, i,k);
                    }
                    else if(Game.map[i][j] instanceof CollectibleCell &&
                            ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply)
                        mainGrid.add(new Label("Supply"), i, k);
                    else if (Game.map[i][j] instanceof CollectibleCell &&
                            ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine)
                        mainGrid.add(new Label("Vaccine"), i, k);
                }
            }
        }
    }

    public static void main(String[] args) {
            launch(args); // Step 5
    }
}

