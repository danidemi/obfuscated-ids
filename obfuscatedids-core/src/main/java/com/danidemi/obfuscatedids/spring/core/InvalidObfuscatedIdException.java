package com.danidemi.obfuscatedids.spring.core;

import static java.lang.String.format;

public class InvalidObfuscatedIdException extends RuntimeException {

    public InvalidObfuscatedIdException(String obfuscatedId) {
        super( obfuscatedId != null ? format("Invalid ID '%s'", obfuscatedId) : "Invalid ID");
    }
}
