package com.adamekka.bullet_hell;

import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class Momiji implements Enemy {
    public Dimension2D position = new Dimension2D(0, 0);

    public MomijiGun gun;

    public void createGun(Player player, Score score) {
        this.gun = new MomijiGun(player, this, score);
    }

    // MARK: Health

    public static final double max_health = 500;
    private int health = 500;

    public final void decHealth() {
        if (health <= 0) {
            this.health = 0;
            SceneManager.ui.showWin();
            return;
        }

        this.health -= 1;
        if (SceneManager.ui != null) {
            Platform.runLater(() -> SceneManager.ui.setEnemyHealth(health));
        }
    }

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

    // MARK: Enemy

    @Override
    public final Gun getGun() {
        return gun;
    }

    private double orbitAngle = 0;

    @Override
    public final void simulate(double delta) {
        final double radius = 2;
        final double angularSpeed = 5;

        orbitAngle += angularSpeed * delta;

        double centerX = Config.Canvas.size.getWidth() / 2;
        double centerY = Config.Canvas.size.getHeight() / 4;

        position = new Dimension2D(
            centerX + Math.cos(orbitAngle) * radius,
            centerY + Math.sin(orbitAngle) * radius
        );
    }

    @Override
    public final Dimension2D getPosition() {
        return position;
    }
}
