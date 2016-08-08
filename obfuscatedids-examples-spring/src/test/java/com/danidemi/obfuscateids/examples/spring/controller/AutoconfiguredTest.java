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

package com.danidemi.obfuscateids.examples.spring.controller;

import com.danidemi.obfuscatedids.core.IdObfuscator;
import com.danidemi.obfuscatedids.hashids.HashIdObfuscator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Rest.class)
public class AutoconfiguredTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testDisguise() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/disguise/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("aQloZYwVmGM"));
    }

    @Test
    public void testDecrypt() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/decode/aQloZYwVmGM"))
                .andExpect(status().isOk())
                .andExpect(content().string("123"));
    }

    @TestConfiguration
    public static class Config {
        @Bean
        IdObfuscator obfuscator() {
            return new HashIdObfuscator();
        }
    }

}