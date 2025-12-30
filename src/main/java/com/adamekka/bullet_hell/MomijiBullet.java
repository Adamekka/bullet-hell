package com.adamekka.bullet_hell;

import java.util.Random;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class MomijiBullet implements Bullet {
    public MomijiBullet(Dimension2D position) {
        this.position
            = new Dimension2D(position.getWidth(), position.getHeight() + 24);
    }

    public Dimension2D position;

    private final Random random = new Random();

    // MARK: Bullet

    private final double speed = 200;

    private final Angle angle = Angle.fromDegrees(random.nextDouble(45, 136));

    @Override
    public void simulate(double delta) {
        position = new Dimension2D(
            position.getWidth() + speed * delta * angle.cos(),
            position.getHeight() + speed * delta * angle.sin()
        );
    }

    // MARK: Drawable

    private final Image sprite = new Image(
        MomijiBullet.class.getResourceAsStream("momiji-bullet.png")
    );

    private final Dimension2D spriteSize = new Dimension2D(16, 16);

    private final int spriteXIndex = random.nextInt(0, 16);
    private final int spriteYIndex = 3;

    @Override
    public void draw(GraphicsContext g, double delta) {
        g.drawImage(
            sprite,
            spriteXIndex * spriteSize.getWidth(),
            spriteYIndex * spriteSize.getHeight(),
            spriteSize.getWidth(),
            spriteSize.getHeight(),
            position.getWidth() - spriteSize.getWidth() / 2,
            position.getHeight() - spriteSize.getHeight() / 2,
            spriteSize.getWidth(),
            spriteSize.getHeight()
        );
    }

    // MARK: Collide

    @Override
    public boolean collide(Character character, Score score) {
        double dx = position.getWidth() - character.getPosition().getWidth();
        double dy = position.getHeight() - character.getPosition().getHeight();

        if (dx * dx + dy * dy < 4 * 4) {
            character.decHealth();
            score.dec();
            return true;
        }

        return false;
    }
}
