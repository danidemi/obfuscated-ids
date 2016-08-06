package com.danidemi.obfuscatedids.spring.core;


public class UnobfuscatingObfuscator implements IdObfuscator {


    @Override
    public String disguise(long id) {
        return String.valueOf(id);
    }

    @Override
    public long decode(String disguisedId) {
        try {
            return Long.valueOf(disguisedId);
        } catch (NumberFormatException e) {
            throw new InvalidObfuscatedIdException(disguisedId);
        }
    }

}
