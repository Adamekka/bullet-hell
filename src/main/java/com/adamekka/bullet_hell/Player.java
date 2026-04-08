package com.adamekka.bullet_hell;

import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class Player implements Character {
    public PlayerGun gun;

    public Player() {
        SceneManager.ui.setPlayer(health);
    }

    public void createGun(Momiji momiji, Score score) {
        this.gun = new PlayerGun(this, momiji, score);
    }

    @Override
    public final Gun getGun() {
        return gun;
    }

    // MARK: Health

    private int health = 5;

    public final void decHealth() {
        if (health <= 0) {
            this.health = 0;
            SceneManager.ui.showLost();
            return;
        }

        this.health -= 1;
        if (SceneManager.ui != null) {
            Platform.runLater(() -> SceneManager.ui.setPlayer(health));
        }
    }

    // MARK: Movement

    private Dimension2D position = new Dimension2D(
        Config.Canvas.size.getWidth() / 2,
        Config.Canvas.size.getHeight() * 3 / 4
    );

    public final Dimension2D getPosition() {
        return position;
    }

    private final double speed = 200;

    private MovementDirection lastDirection = MovementDirection.STILL;
    private MovementDirection direction = MovementDirection.STILL;

    public final void still() {
        direction = MovementDirection.STILL;
    }

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
                Config.Canvas.size.getHeight(),
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
                Config.Canvas.size.getWidth(),
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
