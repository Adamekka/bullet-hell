package com.adamekka.bullet_hell;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public final class Renderer extends AnimationTimer {
    private final Canvas canvas;
    private final GraphicsContext gc;

    private long lastFrame = 0;

    private final Player player;
    private final Momiji momiji;

    public final Input input;

    private final Score score;

    Renderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        this.player = new Player();
        this.momiji = new Momiji();

        this.score = new Score();

        this.player.createGun(momiji, score);
        this.momiji.createGun(player, score);

        this.input = new Input(player);
    }

    @Override
    public final void handle(long now) {
        double delta = lastFrame == 0 ? 0 : (now - lastFrame) / 1_000_000_000D;
        lastFrame = now;

        input.update(delta);

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        player.draw(gc, delta);

        player.gun.draw(gc, delta);
        player.gun.simulate(delta);
        player.gun.collide();

        momiji.draw(gc, delta);
        momiji.shoot(delta);
        momiji.simulate(delta);

        momiji.gun.draw(gc, delta);
        momiji.gun.simulate(delta);
        momiji.gun.collide();

        drawFps(delta);
    }

    private final void drawFps(double delta) {
        int fps = calcFps(delta);

        gc.setFont(new Font("UbuntuMono Nerd Font", 30));
        gc.setFill(Color.BLACK);
        gc.fillText(String.format("FPS: %d", fps), 10, canvas.getHeight() - 10);
    }

    private double fpsSum = 0;
    private double fpsCount = 0;
    private int averageFps = 0;

    private final int calcFps(double delta) {
        fpsSum += 1 / delta;
        fpsCount += 1;

        if (fpsCount >= 100) {
            averageFps = (int)(fpsSum / fpsCount);
            fpsSum = 0;
            fpsCount = 0;
        }

        return averageFps;
    }
}
