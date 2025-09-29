package com.adamekka.bullet_hell;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public final class App extends Application {
    public static final void main(String[] args) { launch(args); }

    @Override
    public final void start(Stage primaryStage) {
        Group root = new Group();

        Canvas canvas = new Canvas(
            Config.Window.SIZE.getWidth(), Config.Window.SIZE.getHeight()
        );

        root.getChildren().add(canvas);

        Scene scene = new Scene(
            root, Config.Window.SIZE.getWidth(), Config.Window.SIZE.getHeight()
        );

        Input input = Input.getInstance();

        scene.setOnKeyPressed(input::handleKeyPressed);
        scene.setOnKeyReleased(input::handleKeyReleased);

        primaryStage.setTitle(Config.Window.TITLE);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        Renderer renderer = new Renderer(canvas);
        renderer.start();
    }
}
