package com.adamekka.bullet_hell;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public final class SceneManager {
    public static enum SceneType {
        MAIN_MENU,
        GAME,
    }

    private static Stage primaryStage;

    // Main Menu
    private static Parent mainMenuParent;
    private static Canvas mainMenuCanvas;

    // Game
    public static UIController ui;
    private static Parent gameParent;
    private static Canvas gameCanvas;
    public static Renderer renderer;
    private static MediaPlayer gameMediaPlayer;

    private SceneManager() {}

    public static void init(Stage stage) {
        primaryStage = stage;
        switchTo(SceneType.MAIN_MENU);
    }

    public static void switchTo(SceneType type) {
        switch (type) {
            case MAIN_MENU -> showMainMenu();
            case GAME -> startGame();
        }
    }

    public static void showMainMenu() {
        if (renderer != null) {
            renderer.stop();
            renderer = null;
        }
        if (gameMediaPlayer != null) {
            gameMediaPlayer.stop();
            gameMediaPlayer = null;
        }
        Input.getInstance().clear();

        // Load main menu FXML (or fallback)
        try {
            FXMLLoader mainMenuLoader = new FXMLLoader(
                SceneManager.class.getResource("main-menu.fxml")
            );
            mainMenuParent = mainMenuLoader.load();

            Object node = mainMenuLoader.getNamespace().get("canvas");
            if (!(node instanceof Canvas)) {
                throw new IllegalStateException(
                    "main-menu.fxml must define a Canvas with fx:id=\"canvas\""
                );
            }
            mainMenuCanvas = (Canvas)node;
        } catch (Exception e) {
            e.printStackTrace();
            Group fallbackRoot = new Group();
            mainMenuCanvas = new Canvas(
                Config.Window.size.getWidth(), Config.Window.size.getHeight()
            );
            fallbackRoot.getChildren().add(mainMenuCanvas);
            mainMenuParent = fallbackRoot;
        }

        Group root = new Group();
        root.getChildren().add(mainMenuParent);

        Scene scene = new Scene(
            root, Config.Window.size.getWidth(), Config.Window.size.getHeight()
        );

        primaryStage.setTitle(Config.Window.title);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }

    public static void startGame() {
        if (primaryStage == null)
            return;

        Input.getInstance().clear();

        if (renderer != null) {
            renderer.stop();
            renderer = null;
        }

        if (gameMediaPlayer != null) {
            gameMediaPlayer.stop();
            gameMediaPlayer = null;
        }

        // Load game UI from FXML (or fallback)
        try {
            FXMLLoader loader = new FXMLLoader(
                SceneManager.class.getResource("application.fxml")
            );
            gameParent = loader.load();
            ui = loader.getController();

            Object node = loader.getNamespace().get("canvas");
            if (!(node instanceof Canvas)) {
                throw new IllegalStateException(
                    "application.fxml must define a Canvas with "
                    + "fx:id=\"canvas\""
                );
            }
            gameCanvas = (Canvas)node;
        } catch (Exception e) {
            e.printStackTrace();
            Group fallbackRoot = new Group();
            gameCanvas = new Canvas(
                Config.Window.size.getWidth(), Config.Window.size.getHeight()
            );
            fallbackRoot.getChildren().add(gameCanvas);
            gameParent = fallbackRoot;
        }

        // Background video behind everything
        Media media = new Media(
            SceneManager.class.getResource("background.mp4").toString()
        );
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
        root.getChildren().add(gameParent);

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

        // Load persisted high score
        Score.getInstance();

        // Start renderer once per game start
        renderer = new Renderer(gameCanvas);
        renderer.start();
    }
}
