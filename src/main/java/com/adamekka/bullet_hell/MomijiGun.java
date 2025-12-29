package com.adamekka.bullet_hell;

import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;

public final class MomijiGun implements Gun {
    private static final MomijiGun instance = new MomijiGun();
    private MomijiGun() {}
    public static final MomijiGun getInstance() { return instance; }

    private final LinkedList<MomijiBullet> bullets = new LinkedList<>();

    private double timeSinceLastShot = 0;
    private final double cooldown = 0.02;

    @Override
    public void shoot(double delta) {
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= cooldown) {
            bullets.add(new MomijiBullet(Momiji.getInstance().getPosition()));
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
}
