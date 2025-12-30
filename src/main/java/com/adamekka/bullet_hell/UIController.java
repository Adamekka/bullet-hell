package com.adamekka.bullet_hell;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public final class UIController {
    @FXML private Label hiScoreLabel;
    @FXML private Label scoreLabel;
    @FXML private Label playerLabel;
    @FXML private ProgressBar enemyHealth;

    public void setHiScore(int v) { hiScoreLabel.setText(Integer.toString(v)); }

    public void setScore(int v) { scoreLabel.setText(Integer.toString(v)); }

    public void setPlayer(int v) { playerLabel.setText(Integer.toString(v)); }

    public void setEnemyHealth(int v) {
        enemyHealth.setProgress(
            Math.min(1.0, Math.max(0.0, v / Momiji.max_health))
        );
    }
}
