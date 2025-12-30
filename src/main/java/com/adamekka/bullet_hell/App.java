package com.adamekka.bullet_hell;

import javafx.application.Application;
import javafx.stage.Stage;

public final class App extends Application {
    public static final void main(String[] args) { launch(args); }

    @Override
    public final void start(Stage primaryStage) {
        SceneManager.init(primaryStage);
    }
}
