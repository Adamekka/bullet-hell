package com.adamekka.bullet_hell;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;

public final class MomijiGun implements Gun {
    private final WeakReference<Player> player;
    private final WeakReference<Momiji> momiji;
    private final WeakReference<Score> score;

    public MomijiGun(Player player, Momiji momiji, Score score) {
        this.player = new WeakReference<>(player);
        this.momiji = new WeakReference<>(momiji);
        this.score = new WeakReference<>(score);
    }

    private final LinkedList<MomijiBullet> bullets = new LinkedList<>();

    private double timeSinceLastShot = 0;

    @Override
    public void shoot(double delta) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= Config.Game.difficulty.getCooldown()) {
            if (momiji == null || momiji.get() == null) {
                return;
            }

            bullets.add(new MomijiBullet(momiji.get().getPosition()));
            timeSinceLastShot = 0;
        }
    }

    @Override
    public void simulate(double delta) {
        for (MomijiBullet bullet : bullets) {
            bullet.simulate(delta);
        }

        while (bullets.peek() != null
               && bullets.peek().position.getHeight()
                      > Config.Canvas.size.getHeight() + 100) {
            bullets.remove();
        }
    }

    @Override
    public void draw(GraphicsContext gc, double delta) {
        for (MomijiBullet bullet : bullets) {
            bullet.draw(gc, delta);
        }
    }

    @Override
    public void collide() {
        for (Iterator<MomijiBullet> it = bullets.iterator(); it.hasNext();) {
            MomijiBullet bullet = it.next();
            if (bullet.collide(player.get(), score.get())) {
                it.remove();
            }
        }
    }
}
