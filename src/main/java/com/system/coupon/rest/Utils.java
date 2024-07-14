package com.system.coupon.rest;

import java.util.UUID;

public class Utils {
    private static final int LENGTH_TOKEN = 15;

    public static String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, LENGTH_TOKEN);
    }

    public static void logError(Exception e, String methodName) {
        System.err.println("An error occurred in " + methodName + e.getMessage());
    }
}
