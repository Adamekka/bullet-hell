package com.adamekka.bullet_hell;

public interface Gun extends Drawable {
    void shoot(double delta);
    void simulate(double delta);
    void collide();
}
