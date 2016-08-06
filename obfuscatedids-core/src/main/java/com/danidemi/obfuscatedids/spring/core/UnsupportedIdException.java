package com.danidemi.obfuscatedids.spring.core;

import static java.lang.String.format;

public class UnsupportedIdException extends RuntimeException {

    public UnsupportedIdException(long unsupportedId) {
        super(format("Obfuscation of ID '%s' not supported.", unsupportedId));
    }

}
