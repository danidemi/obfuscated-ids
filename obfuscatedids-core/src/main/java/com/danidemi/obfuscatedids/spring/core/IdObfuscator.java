package com.danidemi.obfuscatedids.spring.core;

public interface IdObfuscator {
    String disguise(long id);
    long decode(String disguisedId);
}
