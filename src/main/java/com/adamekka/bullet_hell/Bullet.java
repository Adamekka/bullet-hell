package com.adamekka.bullet_hell;

public interface Bullet extends Drawable {
    void simulate(double delta);
    boolean collide(Character character, Score score);
}
