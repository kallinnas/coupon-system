package com.system.coupon.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
//@Builder
@AllArgsConstructor
public class Token {
    private String token = "";
    private static final int LENGTH_TOKEN = 15;

    public static String generateToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, Token.LENGTH_TOKEN);
    }
}

