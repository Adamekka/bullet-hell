package com.adamekka.bullet_hell;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public final class UIController {
    @FXML private Label hiScoreLabel;
    @FXML private Label scoreLabel;
    @FXML private Label playerLabel;

    public void setHiScore(int v) { hiScoreLabel.setText(Integer.toString(v)); }

    public void setScore(int v) { scoreLabel.setText(Integer.toString(v)); }

    public void setPlayer(int v) { playerLabel.setText(Integer.toString(v)); }
}
