package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;

public interface Character extends Drawable {
    Gun getGun();

    default void shoot(double delta) { getGun().shoot(delta); }

    void decHealth();
    Dimension2D getPosition();
}
