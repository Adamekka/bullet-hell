package com.adamekka.bullet_hell;

public final record Angle(double radians) {
    public static Angle fromDegrees(double deg) {
        return new Angle(Math.toRadians(deg));
    }

    public double sin() {
        return Math.sin(radians);
    }

    public double cos() {
        return Math.cos(radians);
    }
}
