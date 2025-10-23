package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class Momiji implements Drawable {
    private static final Momiji instance = new Momiji();
    private Momiji() {}
    public static final Momiji getInstance() { return instance; }

    private Dimension2D position = new Dimension2D(
        Config.Window.SIZE.getWidth() / 2, Config.Window.SIZE.getHeight() / 4
    );

    // MARK: Drawable

    private final Image sprite
        = new Image(Momiji.class.getResourceAsStream("momiji.png"));

    private final Dimension2D spriteSize = new Dimension2D(64, 48);

    private int spriteIndex = 0;
    private double animationTimer = 0;

    @Override
    public final void draw(GraphicsContext gc, double delta) {
        animationTimer += delta;
        if (animationTimer >= 0.3) {
            spriteIndex++;

            if (spriteIndex > 3) {
                spriteIndex = 0;
            }

            animationTimer = 0;
        }

        gc.drawImage(
            sprite,
            spriteIndex * spriteSize.getWidth(),
            0,
            spriteSize.getWidth(),
            spriteSize.getHeight(),
            position.getWidth() - spriteSize.getWidth() / 2,
            position.getHeight() - spriteSize.getHeight() / 2,
            spriteSize.getWidth(),
            spriteSize.getHeight()
        );
    }
}
