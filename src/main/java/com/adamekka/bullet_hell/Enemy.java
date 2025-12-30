package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;

public interface Enemy extends Character {
    void shoot(double delta);
    void simulate(double delta);
    Dimension2D getPosition();
}
