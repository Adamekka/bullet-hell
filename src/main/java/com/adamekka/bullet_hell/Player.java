package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class Player implements Drawable {
    private static final Player instance = new Player();
    private Player() {}
    public static final Player getInstance() { return instance; }

    public void shoot(double delta) { PlayerGun.getInstance().shoot(delta); }

    // MARK: Movement

    public Dimension2D position = new Dimension2D(
        Config.Window.size.getWidth() / 2,
        Config.Window.size.getHeight() * 3 / 4
    );

    private final double speed = 200;

    private MovementDirection lastDirection = MovementDirection.STILL;
    private MovementDirection direction = MovementDirection.STILL;

    public final void still() { direction = MovementDirection.STILL; }

    public final void moveUp(double delta) {
        direction = MovementDirection.STILL;
        position = new Dimension2D(
            position.getWidth(),
            Math.max(0, position.getHeight() - speed * delta)
        );
    }

    public final void moveDown(double delta) {
        direction = MovementDirection.STILL;
        position = new Dimension2D(
            position.getWidth(),
            Math.min(
                Config.Window.size.getHeight(),
                position.getHeight() + speed * delta
            )
        );
    }

    public final void moveLeft(double delta) {
        direction = MovementDirection.LEFT;
        position = new Dimension2D(
            Math.max(0, position.getWidth() - speed * delta),
            position.getHeight()
        );
    }

    public final void moveRight(double delta) {
        direction = MovementDirection.RIGHT;
        position = new Dimension2D(
            Math.min(
                Config.Window.size.getWidth(),
                position.getWidth() + speed * delta
            ),
            position.getHeight()
        );
    }

    // MARK: Drawable

    private final Image sprite
        = new Image(Player.class.getResourceAsStream("reimu.png"));

    private final Dimension2D spriteSize = new Dimension2D(32, 48);

    private int spriteIndex = 0;
    private double animationTimer = 0;

    @Override
    public final void draw(GraphicsContext gc, double delta) {
        if (lastDirection == MovementDirection.STILL
            && (direction == MovementDirection.LEFT
                || direction == MovementDirection.RIGHT)) {
            spriteIndex = 0;
        }

        animationTimer += delta;
        if (animationTimer >= 0.1) {
            spriteIndex++;

            if (direction == MovementDirection.STILL) {
                if (spriteIndex > 7) {
                    spriteIndex = 0;
                }
            } else {
                if (spriteIndex > 7) {
                    spriteIndex = 5;
                }
            }

            animationTimer = 0;
        }

        lastDirection = direction;

        gc.drawImage(
            sprite,
            spriteIndex * spriteSize.getWidth(),
            direction.getValue() * spriteSize.getHeight(),
            spriteSize.getWidth(),
            spriteSize.getHeight(),
            position.getWidth() - spriteSize.getWidth() / 2,
            position.getHeight() - spriteSize.getHeight() / 2,
            spriteSize.getWidth(),
            spriteSize.getHeight()
        );
    }
}
