package com.example.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class GrotteApplication extends Application {

    private Integer rows = 5;
    private Integer columns = 5;

    @Override
    public void start(Stage primaryStage) throws IOException {
//        Group root = new Group();
        GridPane gridpane = new GridPane();
        gridpane.getStyleClass().add("game-grid");

        // set constraints
        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(40);
            gridpane.getColumnConstraints().add(column);
        }
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(40);
            gridpane.getRowConstraints().add(row);
        }

        // set style grid
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
//                Pane pane = new Pane();
//                pane.getStyleClass().add("game-grid-cell");
//                if (i == 0) {
//                    pane.getStyleClass().add("first-column");
//                }
//                if (j == 0) {
//                    pane.getStyleClass().add("first-row");
//                }
//
//                gridpane.add(pane, i, j);
            }
        }

        // set scene
        Scene scene = new Scene(gridpane, (columns * 40) + 100, (rows * 40) + 100, Color.WHITE);
        scene.getStylesheets().add(GrotteApplication.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        // infinite loop
        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(5),
                        event -> {
                            System.out.println("this is called every 5 seconds on UI thread");
                            int waterDropSpawnColumn = waterDropSpawnColumn();
                            System.out.println(waterDropSpawnColumn);

                            for (int j = 0; j < rows; j++) {
                                for (int i = 0; i < columns; i++) {
                                    Pane pane = new Pane();

                                    // spawn/fall water drop
                                    if (j == 0) {
//                                        Circle circle = new Circle(40, 40f, 7);
//                                        circle.setFill(Color.RED);
//                                        gridpane.add(circle, i, j);
                                        if (i == waterDropSpawnColumn) {
                                            System.out.println("test");
                                            Rectangle rectangle = new Rectangle(40, 40);
                                            rectangle.setStroke(Paint.valueOf("white"));
                                            rectangle.setFill(Paint.valueOf("steelblue"));

                                            pane.getChildren().add(rectangle);
                                            gridpane.add(pane, i, 0);

                                            if(getNodeFromGridPane(gridpane, i, 0) != null) {

                                            }

                                            break;
                                        }
                                    }
                                }
                            }
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    public int waterDropSpawnColumn() {
        Random rand = new Random();
        int randomNum = rand.nextInt(((columns - 1) - 0) + 1) + 0;

        System.out.println(randomNum);
        return randomNum;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (node instanceof Node && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                result = node;
                System.out.print(node);
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        launch();
    }
}