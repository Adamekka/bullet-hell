package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;

public interface Character extends Drawable {
    void decHealth();
    Dimension2D getPosition();
}
