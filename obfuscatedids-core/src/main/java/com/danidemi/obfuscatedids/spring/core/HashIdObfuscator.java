package com.danidemi.obfuscatedids.spring.core;


import org.hashids.Hashids;

import static java.lang.String.format;

public class HashIdObfuscator implements IdObfuscator {

    private final Hashids hashids;

    public HashIdObfuscator(Hashids hashids) {
        this.hashids = hashids;
    }

    public HashIdObfuscator() {
        this.hashids = new Hashids("xnzmQJi0dZwNuXZbML7nCsKwZ1Cn1rppPlJm7xDob4koRTRsyLRO5dV7g8r5NMUlKf8tIspy3dbWuzPt", 11);
    }

    @Override
    public String obfuscate(long id) {

        if(id<0) throw new UnsupportedIdException(id);

        return hashids.encode( id );
    }

    @Override
    public long decode(String obfuscatedId) {

        long[] decodeds = hashids.decode(obfuscatedId);

        if(decodeds == null || decodeds.length != 1){
            throw new InvalidObfuscatedIdException( format("Illegal ID '%s'.", obfuscatedId) );
        }

        return decodeds[0];
    }

}
