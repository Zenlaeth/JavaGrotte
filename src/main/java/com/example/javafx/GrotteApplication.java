package com.example.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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

    private Integer rows = 20;
    private Integer rowsSize = rows - 1;
    private Integer columns = 20;
    private Integer columnsSize = columns - 1;

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
                new KeyFrame(Duration.seconds(0.2),
                        event -> {
                            // spawn/fall water drop
                            int waterDropSpawnColumn = waterDropSpawnColumn();

                            for (int y = 0; y < columns; y++) {
                                if (y == waterDropSpawnColumn) {
                                    // base rec
                                    waterDropAnimation(0, y, gridpane);

                                    // construct stalactite
                                    for (int x = 0; x < rows; x++) {
                                        Node upperNode = getNodeFromGridPane(gridpane, y, x - 1);
                                        Rectangle upperRec = (Rectangle) upperNode;

                                        if(upperRec != null && upperRec.getFill().equals(Color.GREY)) {
                                            waterDropAnimation(x, y, gridpane);
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
        int randomNum = rand.nextInt(((columnsSize) - 0) + 1) + 0;

        return randomNum;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (node instanceof Node && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                result = node;
                break;
            }
        }
        return result;
    }

    private void createRectangle(Color color, Integer column, Integer row, GridPane gridPane) {
        Rectangle rec = new Rectangle(40, 40);
        rec.setStroke(Paint.valueOf("white"));
        rec.setFill(Paint.valueOf(color.toString()));

        gridPane.add(rec, column, row);
    }

    private void waterDropAnimation(Integer x, Integer y, GridPane gridpane) {
        Node node = getNodeFromGridPane(gridpane, y, x);
        Rectangle rec = (Rectangle) node;

        if(rec == null) {
            createRectangle(Color.GREEN, y, x, gridpane);
        } else {
            if(rec.getFill().equals(Color.GREEN)) {
                rec.setFill(Color.YELLOW);
            } else if (rec.getFill().equals(Color.YELLOW)) {
                rec.setFill(Color.GREY);

                // water drop fall
                createRectangle(Color.GREY, y, rowsSize - x, gridpane);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}