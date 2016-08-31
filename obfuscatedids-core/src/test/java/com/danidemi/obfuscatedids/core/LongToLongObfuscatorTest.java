package com.danidemi.obfuscatedids.core;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class LongToLongObfuscatorTest {

    public static class IdObfuscatorLong {

        public long disguise(String seed, long clearnumber) {
            return clearnumber ^ ( longFromString(seed) & ~Long.MIN_VALUE );
        }

        public long longFromString(String seed) {
            byte[] bytes = seed.getBytes();

            ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
            bb.put(bytes);
            bb.flip();
            return bb.getLong();
        }

    }

    @Test
    public void shouldDecryptADisguisedId() {

        // given
        IdObfuscatorLong sut = new IdObfuscatorLong();

        // when
        String seed = "Kraz?!23";
        long original = -5858;

        // then
        assertThat( original, equalTo( sut.disguise(seed, sut.disguise(seed, original)) ) );

    }

}
