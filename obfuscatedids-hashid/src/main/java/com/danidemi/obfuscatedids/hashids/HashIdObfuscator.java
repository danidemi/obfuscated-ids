/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2016-2016  Daniele Demichelis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package com.danidemi.obfuscatedids.hashids;


import com.danidemi.obfuscatedids.core.IdObfuscator;
import com.danidemi.obfuscatedids.core.InvalidObfuscatedIdException;
import com.danidemi.obfuscatedids.core.UnsupportedIdException;
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
    public String disguise(long id) {

        if(id<0) throw new UnsupportedIdException(id);

        return hashids.encode( id );
    }

    @Override
    public long decode(String disguisedId) {

        long[] decodeds = hashids.decode(disguisedId);

        if(decodeds == null || decodeds.length != 1){
            throw new InvalidObfuscatedIdException( format("Illegal ID '%s'.", disguisedId) );
        }

        return decodeds[0];
    }

}
