package com.adamekka.bullet_hell;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public final class App extends Application {
    public static final void main(String[] args) { launch(args); }

    @Override
    public final void start(Stage primaryStage) {
        Group root = new Group();

        Media media
            = new Media(App.class.getResource("background.mp4").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(Config.Window.size.getWidth());
        mediaView.setFitHeight(Config.Window.size.getHeight());
        mediaView.setPreserveRatio(false);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.muteProperty().setValue(true);
        mediaPlayer.play();

        root.getChildren().add(mediaView);

        Canvas canvas = new Canvas(
            Config.Window.size.getWidth(), Config.Window.size.getHeight()
        );

        root.getChildren().add(canvas);

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
