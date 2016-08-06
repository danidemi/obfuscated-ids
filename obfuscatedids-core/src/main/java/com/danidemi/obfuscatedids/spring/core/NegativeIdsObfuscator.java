package com.danidemi.obfuscatedids.spring.core;

public class NegativeIdsObfuscator implements IdObfuscator {

    private final IdObfuscator delegate;

    public NegativeIdsObfuscator(IdObfuscator delegate) {
        this.delegate = delegate;
    }

    @Override
    public String obfuscate(long id) {
        long newId = id << 1;
        newId = newId < 0 ? Math.abs(newId)-1 : newId;
        return delegate.obfuscate(newId);
    }

    @Override
    public long decode(String obfuscatedId) {
        long decoded = delegate.decode(obfuscatedId);
        return decoded % 2 == 0 ? decoded >> 1 : -1 * ((decoded - 1) >> 1);
        //return decoded % 2 == 0 ? decoded >> 1 : -1 * ((decoded - 1) >> 1);
    }
}
