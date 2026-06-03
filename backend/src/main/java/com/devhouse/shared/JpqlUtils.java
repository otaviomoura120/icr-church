package com.devhouse.shared;

public final class JpqlUtils {

    private JpqlUtils() {}

    public static String escapeLike(String value) {
        if (value == null) return null;
        return value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}
