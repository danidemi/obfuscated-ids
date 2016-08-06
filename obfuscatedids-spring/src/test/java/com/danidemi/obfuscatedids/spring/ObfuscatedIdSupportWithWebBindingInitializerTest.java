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

package com.danidemi.obfuscatedids.spring;

import com.danidemi.obfuscatedids.core.UnobfuscatingObfuscator;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ObfuscatedIdSupportWithWebBindingInitializerTest.Config.class)
public class ObfuscatedIdSupportWithWebBindingInitializerTest {

    @Autowired
    MockMvc mvc;

    @org.junit.Test
    public void shouldWorkWithWebBindingInitializer() throws Exception {

        this.mvc.perform(get("/test/11235/hello-world"))
                .andExpect(status().isOk())
                .andExpect(content().string("11235#hello-world"));

    }


    @Configuration
    public static class Config {

        @RestController
        public static class Controller {

            @RequestMapping("/test/{obfuscatedid}/{message}")
            public String getCode(@PathVariable("obfuscatedid") ObfuscatedId obfuscatedId, @PathVariable("message") String message) {
                String obfuscatedId1 = obfuscatedId.toString();
                long decode = new UnobfuscatingObfuscator().decode(obfuscatedId1);
                return String.valueOf(decode) + "#" + message;
            }

        }

    }

    public static class ObfuscatedIdBindingInitializer implements WebBindingInitializer {

        public void initBinder(WebDataBinder binder, WebRequest request) {
            binder.registerCustomEditor(ObfuscatedId.class, new ObfuscatedIdSupport());
        }

    }

}
