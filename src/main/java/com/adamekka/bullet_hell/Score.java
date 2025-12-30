package com.adamekka.bullet_hell;

public final class Score {
    private static final Score instance = new Score();
    private Score() {}
    public static final Score getInstance() { return instance; }

    private int highScore = 0;
    private int score = 0;

    public void inc() {
        score += 1;
        if (score > highScore) {
            highScore = score;
            App.ui.setHiScore(highScore);
        }
        App.ui.setScore(score);
    }

    public void dec() {
        score -= 10;
        App.ui.setScore(score);
    }
}
