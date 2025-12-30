package com.adamekka.bullet_hell;

public enum Difficulty {
    EASY(0.04),
    MEDIUM(0.02),
    HARD(0.01),
    LUNATIC(0.005);

    private final double level;

    Difficulty(double level) { this.level = level; }

    public double getLevel() { return level; }

    public Difficulty next() {
        return switch (this) {
            case EASY -> MEDIUM;
            case MEDIUM -> HARD;
            case HARD -> LUNATIC;
            case LUNATIC -> EASY;
        };
    }
}
