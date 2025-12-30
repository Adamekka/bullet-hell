package com.adamekka.bullet_hell;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public final class Input {
    private WeakReference<Player> player;

    private final HashSet<KeyCode> pressedKeys = new HashSet<>();

    public Input(Player player) { this.player = new WeakReference<>(player); }

    public final void handleKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    public final void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    public final void clear() { pressedKeys.clear(); }

    public final void update(double delta) {
        boolean moving = false;

        if (pressedKeys.contains(KeyCode.Q)) {
            SceneManager.showMainMenu();
            return;
        }

        if (player == null || player.get() == null) {
            return;
        }

        if (pressedKeys.contains(KeyCode.W)
            || pressedKeys.contains(KeyCode.UP)) {
            player.get().moveUp(delta);
            moving = true;
        }

        if (pressedKeys.contains(KeyCode.S)
            || pressedKeys.contains(KeyCode.DOWN)) {
            player.get().moveDown(delta);
            moving = true;
        }

        if (pressedKeys.contains(KeyCode.A)
            || pressedKeys.contains(KeyCode.LEFT)) {
            player.get().moveLeft(delta);
            moving = true;
        }

        if (pressedKeys.contains(KeyCode.D)
            || pressedKeys.contains(KeyCode.RIGHT)) {
            player.get().moveRight(delta);
            moving = true;
        }

        if (!moving) {
            player.get().still();
        }

        if (pressedKeys.contains(KeyCode.J)
            || pressedKeys.contains(KeyCode.Z)) {
            player.get().shoot(delta);
        }
    }
}
