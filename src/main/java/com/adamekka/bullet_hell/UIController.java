package com.adamekka.bullet_hell;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public final class UIController {
    @FXML private Label hiScoreLabel;
    @FXML private Label scoreLabel;
    @FXML private Label playerLabel;

    @FXML private ProgressBar enemyHealth;

    @FXML private Button difficulty;

    @FXML private Label winLabel;
    @FXML private Label lostLabel;

    @FXML
    public void setHiScore(int v) {
        hiScoreLabel.setText(Integer.toString(v));
    }

    @FXML
    public void setScore(int v) {
        scoreLabel.setText(Integer.toString(v));
    }

    @FXML
    public void setPlayer(int v) {
        playerLabel.setText(Integer.toString(v));
    }

    @FXML
    public void setEnemyHealth(int v) {
        enemyHealth.setProgress(
            Math.min(1.0, Math.max(0.0, v / Momiji.max_health))
        );
    }

    @FXML
    public void nextDifficulty() {
        Difficulty current = Config.Game.difficulty;
        Difficulty next = current.next();
        difficulty.setText(next.name());
        Config.Game.difficulty = next;
    }

    @FXML
    public void showWin() {
        winLabel.setVisible(true);
        SceneManager.renderer.stop();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> { SceneManager.showMainMenu(); });
        pause.play();
    }

    @FXML
    public void showLost() {
        lostLabel.setVisible(true);
        SceneManager.renderer.stop();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> { SceneManager.showMainMenu(); });
        pause.play();
    }

    @FXML
    public void resetWinLost() {
        winLabel.setVisible(false);
        lostLabel.setVisible(false);
    }
}
