package com.adamekka.bullet_hell;

import javafx.geometry.Dimension2D;

public final class Config {
    public final static class Game {
        public static Difficulty difficulty = Difficulty.EASY;
    }
    public final static class Canvas {
        static final Dimension2D size = new Dimension2D(600, 600);
    }
    public final static class Window {
        static final String title = "Bullet Hell";
        static final Dimension2D size = new Dimension2D(800, 600);
    }
}
