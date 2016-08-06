package com.danidemi.obfuscatedids.spring.core;

public class NegativeIdsObfuscator implements IdObfuscator {

    private final IdObfuscator delegate;

    public NegativeIdsObfuscator(IdObfuscator delegate) {
        this.delegate = delegate;
    }

    @Override
    public String disguise(long id) {
        long newId = id << 1;
        newId = newId < 0 ? Math.abs(newId)-1 : newId;
        return delegate.disguise(newId);
    }

    @Override
    public long decode(String disguisedId) {
        long decoded = delegate.decode(disguisedId);
        return decoded % 2 == 0 ? decoded >> 1 : -1 * ((decoded - 1) >> 1);
        //return decoded % 2 == 0 ? decoded >> 1 : -1 * ((decoded - 1) >> 1);
    }
}
