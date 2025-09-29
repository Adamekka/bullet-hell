package com.adamekka.bullet_hell;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {
    void draw(GraphicsContext gc, double delta);
}
