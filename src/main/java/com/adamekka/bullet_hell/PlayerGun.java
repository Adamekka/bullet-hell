package com.adamekka.bullet_hell;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;

public final class PlayerGun implements Gun {
    private final WeakReference<Player> player;
    private final WeakReference<Momiji> momiji;
    private final WeakReference<Score> score;

    public PlayerGun(Player player, Momiji momiji, Score score) {
        this.player = new WeakReference<>(player);
        this.momiji = new WeakReference<>(momiji);
        this.score = new WeakReference<>(score);
    }

    private final LinkedList<PlayerBullet> bullets = new LinkedList<>();

    private boolean shootingRight = true;

    private Dimension2D getBulletPosition() {
        shootingRight = !shootingRight;

        return new Dimension2D(
            player.get().getPosition().getWidth() + (shootingRight ? 20 : -20),
            player.get().getPosition().getHeight() - 10
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
        }

        while (bullets.peek() != null
               && bullets.peek().position.getHeight() < -100) {
            bullets.remove();
        }
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        for (PlayerBullet bullet : bullets) {
            bullet.draw(gc, delta);
        }
    }

    @Override
    public void collide() {
        for (Iterator<PlayerBullet> it = bullets.iterator(); it.hasNext();) {
            PlayerBullet bullet = it.next();
            if (bullet.collide(momiji.get(), score.get())) {
                it.remove();
            }
        }
    }
}
