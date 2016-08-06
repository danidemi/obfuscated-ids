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

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NegativeIdsObfuscatorTest {

    @Captor
    ArgumentCaptor<Long> longArg;

    @Mock
    IdObfuscator delegate;

    NegativeIdsObfuscator sut;

    @Before
    public void setUp() {
        sut = new NegativeIdsObfuscator(delegate);
    }

    @Test
    public void shouldReportTheRightObfuscatedIdInCaseOfError() {

        given(delegate.decode("THE-WRONG-ID")).willThrow( new InvalidObfuscatedIdException("THE-WRONG-ID") );

        Assertions.assertThatThrownBy( ()->sut.decode("THE-WRONG-ID") ).hasMessageContaining( "THE-WRONG-ID" );
    }

    @Test
    public void shouldConvertNegativeInPositivesWithouthClashes() {

        // when
        // ...all numbers between -10000 and 10000 (0 excluded)
        // are obfuscated
        int numberOfTests = 10000;
        for(int i = 1; i<= numberOfTests; i++){
            sut.disguise(i);
            sut.disguise(-i);
        }

        // then
        // ...the delegate should be invoked for each number
        verify(delegate, times(numberOfTests*2)).disguise( longArg.capture() );

        List<Long> allObfuscatedIds = longArg.getAllValues();

        assertThat( "all values should be >= 0 but some where not.", allObfuscatedIds.stream().filter( n -> n<0 ).count(), is(0L));

        // ...the IDs that are actually obfuscated should be unique
        HashSet<Long> allUniqueObfuscatedIds = new HashSet<>(allObfuscatedIds);
        assertThat("The IDs that have been obfuscated should be unique, but they weren't.", allObfuscatedIds, hasSize( allUniqueObfuscatedIds.size() ) );

    }

    @Test
    public void shouldPassTheRightValue() {

        // when
        sut.disguise(0);
        sut.disguise(1);
        sut.disguise(-1);
        sut.disguise(2);
        sut.disguise(-2);
        sut.disguise(100);
        sut.disguise(-100);

        // then
        verify(delegate, times(7)).disguise(longArg.capture());

        assertThat(longArg.getAllValues().get(0), is(0L));
        assertThat(longArg.getAllValues().get(1), is(2L));
        assertThat(longArg.getAllValues().get(2), is(1L));
        assertThat(longArg.getAllValues().get(3), is(4L));
        assertThat(longArg.getAllValues().get(4), is(3L));
        assertThat(longArg.getAllValues().get(5), is(200L));
        assertThat(longArg.getAllValues().get(6), is(199L));

    }

}