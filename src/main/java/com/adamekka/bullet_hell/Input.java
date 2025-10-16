package com.adamekka.bullet_hell;

import java.util.HashSet;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public final class Input {
    private static final Input instance = new Input();
    private Input() {}
    public static final Input getInstance() { return instance; }

    private final HashSet<KeyCode> pressedKeys = new HashSet<>();

    public final void handleKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    public final void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    public final void update(double delta) {
        if (pressedKeys.isEmpty()) {
            Player.getInstance().still();
            return;
        }

        if (pressedKeys.contains(KeyCode.W) || pressedKeys.contains(KeyCode.UP))
            Player.getInstance().moveUp(delta);

        if (pressedKeys.contains(KeyCode.S)
            || pressedKeys.contains(KeyCode.DOWN))
            Player.getInstance().moveDown(delta);

        if (pressedKeys.contains(KeyCode.A)
            || pressedKeys.contains(KeyCode.LEFT))
            Player.getInstance().moveLeft(delta);

        if (pressedKeys.contains(KeyCode.D)
            || pressedKeys.contains(KeyCode.RIGHT))
            Player.getInstance().moveRight(delta);

        if (pressedKeys.contains(KeyCode.J)
            || pressedKeys.contains(KeyCode.Z)) {
            Player.getInstance().shoot(delta);
        }
    }
}
