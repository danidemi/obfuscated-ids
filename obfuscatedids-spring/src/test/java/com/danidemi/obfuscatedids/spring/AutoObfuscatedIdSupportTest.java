package com.danidemi.obfuscatedids.spring;


import com.danidemi.obfuscatedids.core.IdObfuscator;
import org.junit.Test;
import org.springframework.web.bind.WebDataBinder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AutoObfuscatedIdSupportTest {

    @Test
    public void shouldAutoregister() {

        // given
        WebDataBinder binder = mock(WebDataBinder.class);
        IdObfuscator obfuscator = mock(IdObfuscator.class);

        // when
        AutoObfuscatedIdSupport.registerCustomEditor(binder, obfuscator);

        // then
        verify(binder).registerCustomEditor(
                eq(AutoObfuscatedId.class),
                argThat( instanceOf(AutoObfuscatedIdSupport.class) )
        );
    }



}