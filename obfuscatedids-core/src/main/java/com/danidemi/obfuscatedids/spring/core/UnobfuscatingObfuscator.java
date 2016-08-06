package com.danidemi.obfuscatedids.spring.core;


public class UnobfuscatingObfuscator implements IdObfuscator {


    @Override
    public String obfuscate(long id) {
        return String.valueOf(id);
    }

    @Override
    public long decode(String obfuscatedId) {
        try {
            return Long.valueOf(obfuscatedId);
        } catch (NumberFormatException e) {
            throw new InvalidObfuscatedIdException(obfuscatedId);
        }
    }

}
