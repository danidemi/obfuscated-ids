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
import com.danidemi.obfuscatedids.spring.AutoObfuscatedId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rest {

    @Autowired
    private IdObfuscator obfuscator;

    @RequestMapping("/disguise/{number}")
    public String disguise(@PathVariable long number) {
        return obfuscator.disguise(number);
    }

    @RequestMapping("/decode/{disguised}")
    public long disguise(@PathVariable AutoObfuscatedId disguised) {
        return disguised.id();
    }

    public static class Result {
        String result;

        public Result(String result) {
            this.result = result;
        }
    }

}
