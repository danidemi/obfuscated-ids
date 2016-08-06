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

import com.danidemi.obfuscatedids.spring.core.UnobfuscatingObfuscator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ObfuscatedIdSupportWithCustomFormatterTest.Config.class)
public class ObfuscatedIdSupportWithCustomFormatterTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void shouldWorkWithInitBinder() throws Exception {

        this.mvc.perform(get("/" + this.getClass().getSimpleName() + "/11235"))
                .andExpect(status().isOk())
                .andExpect(content().string("11235"));

    }

    @Configuration
    public static class Config {

        @RestController
        public static class Controller {

            @RequestMapping("/ObfuscatedIdSupportWithCustomFormatterTest/{obfuscatedid}")
            public String getCode(@PathVariable("obfuscatedid") ObfuscatedId obfuscatedId) {
                String obfuscatedId1 = obfuscatedId.toString();
                long decode = new UnobfuscatingObfuscator().decode(obfuscatedId1);
                return String.valueOf(decode);
            }

            @InitBinder
            protected void initBinder(WebDataBinder binder) {
                binder.addCustomFormatter(new ObfuscatedIdSupport());

            }

        }

    }

}