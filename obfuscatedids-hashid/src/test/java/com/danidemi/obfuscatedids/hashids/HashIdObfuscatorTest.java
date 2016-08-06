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

import com.danidemi.obfuscatedids.core.InvalidObfuscatedIdException;
import com.danidemi.obfuscatedids.core.UnsupportedIdException;
import org.hashids.Hashids;
import org.junit.Before;
import org.junit.Test;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class HashIdObfuscatorTest {

    HashIdObfuscator sut;

    @Before
    public void setUpObfuscator() {
        sut = new HashIdObfuscator( new Hashids() );
    }

    @Test
    public void shouldRefuseUnsupportedIds() {

        assertThatThrownBy( () -> new HashIdObfuscator().disguise(-1) )
            .isInstanceOf( UnsupportedIdException.class )
            .hasMessageContaining("-1");

    }

    @Test
    public void shouldUseADefaultReasonableSetUpWithEmptyConstructor() {

        String aTypicalYouTubeHash = "2BHah7n8ziI";
        assertThat( new HashIdObfuscator().disguise(123345).length(), is(aTypicalYouTubeHash.length()) );

    }

    @Test
    public void shouldThrowExceptionIfCannotBeObfuscated(){

        assertThatThrownBy(() -> sut.decode( "ILLEGAL-OBFUSCATED-ID" ) )
                .hasMessageContaining("Illegal")
                .hasMessageContaining("ILLEGAL-OBFUSCATED-ID")
                .isInstanceOf(InvalidObfuscatedIdException.class);

    }

    @Test
    public void shouldDeobfuscateAPreviouslyObfuscatedId() {

        for(long i = 0; i<10000; i = i+7 ) {

            String obfuscated = sut.disguise(i);
            long decoded = sut.decode(obfuscated);

            assertThat(obfuscated, not( equalTo( valueOf(i) )));
            assertThat(i + " was obfuscated, " + decoded + " was deobfuscated.",  decoded, equalTo( i ));
        }

    }

}