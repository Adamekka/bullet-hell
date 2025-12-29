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

    Renderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    @Override
    public final void handle(long now) {
        double delta = lastFrame == 0 ? 0 : (now - lastFrame) / 1_000_000_000D;
        lastFrame = now;

        Input.getInstance().update(delta);

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Player.getInstance().draw(gc, delta);

        PlayerGun.getInstance().draw(gc, delta);
        PlayerGun.getInstance().simulate(delta);

        Momiji.getInstance().draw(gc, delta);
        Momiji.getInstance().shoot(delta);

        MomijiGun.getInstance().draw(gc, delta);
        MomijiGun.getInstance().simulate(delta);

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
