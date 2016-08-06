package com.danidemi.obfuscatedids.spring.core;

public interface IdObfuscator {
    String obfuscate(long id);
    long decode(String obfuscatedId);
}
