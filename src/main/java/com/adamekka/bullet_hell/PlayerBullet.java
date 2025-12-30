package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class PlayerBullet implements Bullet {
    public PlayerBullet(Dimension2D position) { this.position = position; }

    public Dimension2D position;

    // MARK: Bullet

    private static final double SPEED = 1000;

    @Override
    public void simulate(double delta) {
        position = new Dimension2D(
            position.getWidth(), position.getHeight() - SPEED * delta
        );
    }

    // MARK: Drawable

    private final Image sprite = new Image(
        PlayerBullet.class.getResourceAsStream("player-bullet.png")
    );

    private final Dimension2D spriteSize = new Dimension2D(16, 64);

    @Override
    public void draw(GraphicsContext g, double delta) {
        g.drawImage(
            sprite,
            position.getWidth() - spriteSize.getWidth() / 2,
            position.getHeight() - spriteSize.getHeight() / 2,
            spriteSize.getWidth(),
            spriteSize.getHeight()
        );
    }

    // MARK: Collide
    @Override

    public boolean collide(Character enemy, Score score) {
        double dx = position.getWidth() - enemy.getPosition().getWidth();
        double dy = position.getHeight() - enemy.getPosition().getHeight();

        if (dx * dx + dy * dy < 32 * 32) {
            enemy.decHealth();
            score.inc();
            return true;
        }

        return false;
    }
}
