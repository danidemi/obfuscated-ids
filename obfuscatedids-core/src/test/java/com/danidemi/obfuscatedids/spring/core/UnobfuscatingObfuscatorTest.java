package com.danidemi.obfuscatedids.spring.core;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UnobfuscatingObfuscatorTest {

    UnobfuscatingObfuscator sut = new UnobfuscatingObfuscator();

    @Test public void shouldJustConvertInString() {

        for(long i=-1000; i<=2000; i++){

            String obfuscated = sut.disguise(i);

            assertThat( Long.parseLong(obfuscated), is(i) );
            assertThat( sut.decode(obfuscated), is(i));

        }

    }

    @Test public void shouldThrowException() {

        Assertions.assertThatThrownBy( () -> sut.decode("###") )
                .isInstanceOf(InvalidObfuscatedIdException.class)
                .hasMessageContaining("###");

    }

}