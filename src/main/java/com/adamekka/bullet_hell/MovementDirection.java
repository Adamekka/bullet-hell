package com.adamekka.bullet_hell;

public enum MovementDirection {
    STILL(0),
    LEFT(1),
    RIGHT(2);

    private final int value;

    MovementDirection(int value) { this.value = value; }

    public final int getValue() { return value; }
}
