package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;

public interface Enemy extends Character {
    Gun getGun();

    default void shoot(double delta) { getGun().shoot(delta); }

    void simulate(double delta);
    Dimension2D getPosition();
}
