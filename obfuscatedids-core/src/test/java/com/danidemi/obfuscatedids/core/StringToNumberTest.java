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

package com.danidemi.obfuscatedids.core;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class StringToNumberTest {

    @Test
    public void shouldConvertAStringIntoANumber() {

        String original = "hello world";

        StringToNumber sut = new StringToNumber();
        BigInteger obfuscated = sut.toNumber(original);
        String decyphered = sut.unobfuscate( obfuscated );
        assertThat(original, not(equalTo(obfuscated)));
        assertThat(original, equalTo(decyphered));


    }

    private class StringToNumber {
        private String stringToBinary(String str, boolean pad ) {
            byte[] bytes = str.getBytes();
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes)
            {
                binary.append(Integer.toBinaryString((int) b));
                //if(pad) { binary.append(' '); }
            }
            return binary.toString();
        }

        public BigInteger toNumber(String original) {
            return new BigInteger(1, original.getBytes());
        }

        public String unobfuscate(BigInteger obfuscated) {
            return new String( obfuscated.toByteArray() );
        }
    }
}
