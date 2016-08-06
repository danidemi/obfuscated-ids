package com.danidemi.obfuscatedids.spring.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InvalidObfuscatedIdExceptionTest {

    @Test
    public void shouldContainTheWrongCode() {
        assertThat( new InvalidObfuscatedIdException("-WRONG-ID-").getMessage(), is("Invalid ID '-WRONG-ID-'") );
    }

    @Test
    public void shouldNotContainTheWrongCodeIfNotProvided() {
        assertThat( new InvalidObfuscatedIdException(null).getMessage(), is("Invalid ID") );
    }

}