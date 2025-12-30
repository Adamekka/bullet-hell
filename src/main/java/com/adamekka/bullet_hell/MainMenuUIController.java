package com.adamekka.bullet_hell;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class MainMenuUIController {
    @FXML private Button play;
    @FXML private Button exit;

    @FXML
    public void onPlay() {
        SceneManager.startGame();
    }

    @FXML
    void onExit() {
        System.exit(0);
    }
}
