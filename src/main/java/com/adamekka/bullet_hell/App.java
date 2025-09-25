package com.adamekka.bullet_hell;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) { launch(args); }

    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        Canvas canvas = new Canvas(
            Config.Window.SIZE.getWidth(), Config.Window.SIZE.getHeight()
        );

        Scene scene = new Scene(
            root, Config.Window.SIZE.getWidth(), Config.Window.SIZE.getHeight()
        );

        primaryStage.setTitle(Config.Window.TITLE);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }
}
