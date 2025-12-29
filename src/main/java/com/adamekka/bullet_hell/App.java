package com.adamekka.bullet_hell;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public final class App extends Application {
    public static final void main(String[] args) { launch(args); }

    UIController ui;

    @Override
    public final void start(Stage primaryStage) {
        Parent parent;
        Canvas canvas;

        try {
            FXMLLoader loader
                = new FXMLLoader(App.class.getResource("application.fxml"));
            parent = loader.load();
            ui = loader.getController();

            Object node = loader.getNamespace().get("canvas");
            if (!(node instanceof Canvas)) {
                throw new IllegalStateException(
                    "application.fxml must define a Canvas with "
                    + "fx:id=\"canvas\""
                );
            }
            canvas = (Canvas)node;
        } catch (Exception e) {
            // Fallback
            e.printStackTrace();

            Group fallbackRoot = new Group();
            canvas = new Canvas(
                Config.Window.size.getWidth(), Config.Window.size.getHeight()
            );
            fallbackRoot.getChildren().add(canvas);
            parent = fallbackRoot;
        }

        Media media
            = new Media(App.class.getResource("background.mp4").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(Config.Canvas.size.getWidth());
        mediaView.setFitHeight(Config.Canvas.size.getHeight());
        mediaView.setPreserveRatio(false);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.muteProperty().setValue(true);
        mediaPlayer.play();

        Group root = new Group();
        root.getChildren().add(mediaView);
        root.getChildren().add(parent);

        Scene scene = new Scene(
            root, Config.Window.size.getWidth(), Config.Window.size.getHeight()
        );

        Input input = Input.getInstance();
        scene.setOnKeyPressed(input::handleKeyPressed);
        scene.setOnKeyReleased(input::handleKeyReleased);

        primaryStage.setTitle(Config.Window.title);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        Renderer renderer = new Renderer(canvas);
        renderer.start();
    }
}
