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

        VBox allH = new VBox();


        Button test = new Button("test");
        test.setOnAction(e -> ExceptionPopUp.display("Exception", "Bad Move"));
        test.setMaxSize(65, 35);
        //mainGrid.add(test, 14, 14);


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

            mainBorder.getChildren().remove(mainGrid);
            GridPane newgr = updateMapGui();
            mainBorder.setCenter(newgr);


            newgr.add(hehe,0,14);
            window.setScene(window2);

            mainBorder.getChildren().remove(allH);

            VBox nallH = new VBox();
            nallH.getChildren().addAll(setSelectHero() , setRemHero());
            mainBorder.setRight(nallH);


        });
        GridPane controls = new GridPane();



        Button special = new Button("Special");
        special.setOnAction(event -> {
            try {
                Game.heroes.get(selectedHeroI).useSpecial();
                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);
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
                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);
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

            mainBorder.getChildren().remove(allH);
            VBox nallH = new VBox();
            nallH.getChildren().addAll(setSelectHero() , setRemHero());
            mainBorder.setRight(nallH);
        });


        Button prevHero = new Button("Previous");
        prevHero.setOnAction(e -> {
            if (selectedHeroI > 0)
                selectedHeroI--;
            else
                selectedHeroI = Game.heroes.size() - 1 ;

            mainBorder.getChildren().remove(allH);
            VBox nallH = new VBox();
            nallH.getChildren().addAll(setSelectHero() , setRemHero());
            mainBorder.setRight(nallH);
        });


        Button attackbut = new Button("Attack");
        attackbut.setOnAction(e -> {
            try {
                Game.heroes.get(selectedHeroI).attack();
                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);

                mainBorder.getChildren().remove(allH);
                VBox nallH = new VBox();
                if (setRemHero() != null)
                    nallH.getChildren().addAll(setSelectHero() , setRemHero());
                else
                    nallH.getChildren().add(setSelectHero() );
                mainBorder.setRight(nallH);

            }catch (NotEnoughActionsException e1){
                ExceptionPopUp.display( "bad move", e1.getMessage());

            }catch (InvalidTargetException e1){
                ExceptionPopUp.display( "bad move", e1.getMessage());
            }
        });

        Button endtur = new Button("endturn");
        endtur.setOnAction(event -> {
            try {
                Game.endTurn();
                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);

                mainBorder.getChildren().remove(allH);
                VBox nallH = new VBox();
                if (setRemHero() != null)
                    nallH.getChildren().addAll(setSelectHero() , setRemHero());
                else
                    nallH.getChildren().add(setSelectHero() );

                mainBorder.setRight(nallH);


            } catch (NotEnoughActionsException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            } catch (InvalidTargetException e) {
                ExceptionPopUp.display( "bad move", e.getMessage());
            }
        });
        Button up = new Button("Up");
        up.setOnAction(event -> {
            try {
                Game.heroes.get(selectedHeroI).move(Direction.RIGHT);

                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);

                mainBorder.getChildren().remove(allH);
                VBox nallH = new VBox();
                if (setRemHero() != null)
                    nallH.getChildren().addAll(setSelectHero() , setRemHero());
                else
                    nallH.getChildren().add(setSelectHero() );
                mainBorder.setRight(nallH);

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


                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);

                mainBorder.getChildren().remove(allH);
                VBox nallH = new VBox();
                if (setRemHero() != null)
                    nallH.getChildren().addAll(setSelectHero() , setRemHero());
                else
                    nallH.getChildren().add(setSelectHero() );
                mainBorder.setRight(nallH);


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

                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);

                mainBorder.getChildren().remove(allH);
                VBox nallH = new VBox();
                if (setRemHero() != null)
                    nallH.getChildren().addAll(setSelectHero() , setRemHero());
                else
                    nallH.getChildren().add(setSelectHero() );
                mainBorder.setRight(nallH);


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

                mainBorder.getChildren().remove(mainGrid);
                GridPane newgr = updateMapGui();
                mainBorder.setCenter(newgr);

                mainBorder.getChildren().remove(allH);
                VBox nallH = new VBox();
                if (setRemHero() != null)
                    nallH.getChildren().addAll(setSelectHero() , setRemHero());
                else
                    nallH.getChildren().add(setSelectHero() );
                mainBorder.setRight(nallH);



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
        atack.getChildren().add(endtur);
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
    private VBox setRemHero() {
        VBox allH = new VBox();

        for (int i = 0; i < Game.heroes.size() ; i++) {
            if( i != selectedHeroI){
            HBox targetHero = new HBox();
            Label herosA = new Label("Name: " + Game.heroes.get(i).getName() + "\n"
                    + "Current Health Points: " + Game.heroes.get(i).getCurrentHp() + "\n"
                    + "Type: " + Game.heroes.get(i).getClass().getSimpleName() + "\n"
                    + "Attack Damage: " + Game.heroes.get(i).getAttackDmg() + "\n"
                    + "Available Actions: " + Game.heroes.get(i).getActionsAvailable() + "\n"
                    );
            targetHero.getChildren().add(herosA);
            Button setTarget = new Button("Target for heal");
            int finalI = i;
            setTarget.setOnAction(event -> {
                Game.heroes.get(selectedHeroI).setTarget(Game.heroes.get(finalI));
            });
            targetHero.getChildren().add(setTarget);
            allH.getChildren().add(targetHero);
        }}

        return allH;
    }
    private VBox setSelectHero() {
        VBox allH = new VBox();
        Label herosA = new Label("Name: " + Game.heroes.get(selectedHeroI).getName() + "\n"
                + "Current Health Points: " + Game.heroes.get(selectedHeroI).getCurrentHp() + "\n"
                + "Type: " + Game.heroes.get(selectedHeroI).getClass().getSimpleName() + "\n"
                + "Attack Damage: " + Game.heroes.get(selectedHeroI).getAttackDmg() + "\n"
                + "Available Acrions: " + Game.heroes.get(selectedHeroI).getActionsAvailable() + "\n"
                + "Supply Count: " + Game.heroes.get(selectedHeroI).getSupplyInventory().size()+ "\n"
                + "Vaccine Count: " + Game.heroes.get(selectedHeroI).getVaccineInventory().size() );
        allH.setPadding(new Insets(20, 20,20,20));
        allH.getChildren().add(herosA);
        return allH;
    }

    private GridPane updateMapGui() {
        GridPane mainGrid = new GridPane();
        mainGrid.setGridLinesVisible(true);
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints column = new ColumnConstraints(60);
        RowConstraints row = new RowConstraints(35);

        for (int i = 0; i < Game.heroes.size() ; i++) {
            Label l = new Label(Game.heroes.get(i).getName());
            mainGrid.add(l , (int) Game.heroes.get(i).getLocation().getX(), 14 - (int) Game.heroes.get(i).getLocation().getY());

        }
        for (int i = 0; i < 15; i++) {
            mainGrid.getColumnConstraints().add(i, column);
            mainGrid.getRowConstraints().add(i, row);
        }
        int k;
        for(int i = 0;i <Game.map.length;i++){
            for(int j = 0; j<Game.map[i].length;j++){
                k = 14-j;
                if(Game.map[i][j].isVisible()){
                    if(Game.map[i][j] instanceof CharacterCell &&
                            ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie) {
                        Button zombInGrid = new Button("Zombie");
                        int finalI = i;
                        int finalJ = k;
                        zombInGrid.setOnAction(zomEv -> {
                            Game.heroes.get(selectedHeroI).setTarget(((CharacterCell)Game.map[finalI][14 - finalJ]).getCharacter());
                            System.out.println(finalI + " " + finalJ);
                        });
                        mainGrid.add(zombInGrid, i,k);
                    }
                    else if(Game.map[i][j] instanceof CollectibleCell &&
                            ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){

                            Label l = new Label("Supply");
                            mainGrid.add(l, i, k);}

                    else if (Game.map[i][j] instanceof CollectibleCell &&
                            ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
                        Label l = new Label("Vaccine");
                        mainGrid.add(l, i, k);}
                    else if(Game.map[i][j] instanceof CharacterCell &&
                            ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero) ;

                    else
                        mainGrid.add(new Label("empty"), i ,k);
                    }
                    else
                        mainGrid.add(new Label(), i ,k);


                }
            }
        return mainGrid;
        }


    private static Node getNodeByIndex(GridPane gridPane, int rowIndex, int columnIndex) {
        for (Node node : gridPane.getChildren()) {

            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == columnIndex) {
                return node;
            }

        }
        return null;
    }

    public static void main(String[] args) {
            launch(args); // Step 5
    }
}

