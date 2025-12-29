package com.adamekka.bullet_hell;

import java.util.LinkedList;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;

public final class PlayerGun implements Gun {
    private static final PlayerGun instance = new PlayerGun();
    private PlayerGun() {}
    public static final PlayerGun getInstance() { return instance; }

    private final LinkedList<PlayerBullet> bullets = new LinkedList<>();
    private boolean shootingRight = true;

    private Dimension2D getBulletPosition() {
        shootingRight = !shootingRight;

        return new Dimension2D(
            Player.getInstance().position.getWidth()
                + (shootingRight ? 20 : -20),
            Player.getInstance().position.getHeight() - 10
        );
    }

    private double timeSinceLastShot = 0;
    private final double cooldown = 0.02;

    @Override
    public void shoot(double delta) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= cooldown) {
            bullets.add(new PlayerBullet(getBulletPosition()));
            timeSinceLastShot = 0;
        }
    }

    @Override
    public void simulate(double delta) {
        for (PlayerBullet bullet : bullets) {
            bullet.simulate(delta);

            if (bullet.position.getHeight() < -100) {
                bullets.remove(bullet);
                break;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        for (PlayerBullet bullet : bullets) {
            bullet.draw(gc, delta);
        }
    }
}
