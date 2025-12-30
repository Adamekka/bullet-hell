package com.adamekka.bullet_hell;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Score {
    private static final Path highScorePath = Paths.get(
        System.getProperty("user.home"), ".bullet-hell", "highscore.txt"
    );

    private static final Score instance = new Score();
    private Score() {
        this.highScore = readHighScoreFromFile();

        SceneManager.ui.setHiScore(highScore);
        SceneManager.ui.setScore(score);
    }
    public static final Score getInstance() { return instance; }

    private int highScore = 0;
    private int score = 0;

    public int getHighScore() { return highScore; }
    public int getScore() { return score; }

    public void inc() {
        score += 1;

        if (score > highScore) {
            highScore = score;
            writeHighScoreToFile(highScore);

            SceneManager.ui.setHiScore(highScore);
        }

        SceneManager.ui.setScore(score);
    }

    public void dec() {
        score -= 10;
        SceneManager.ui.setScore(score);
    }

    private static int readHighScoreFromFile() {
        try {
            if (!Files.exists(highScorePath)) {
                return 0;
            }
            String s = Files.readString(highScorePath, StandardCharsets.UTF_8)
                           .trim();
            if (s.isEmpty())
                return 0;
            return Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static void writeHighScoreToFile(int value) {
        try {
            Path dir = highScorePath.getParent();
            if (dir != null) {
                Files.createDirectories(dir);
            }
            Files.writeString(
                highScorePath, Integer.toString(value), StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
