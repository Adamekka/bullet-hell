package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;

public interface Enemy {
    void shoot(double delta);
    Dimension2D getPosition();
}
