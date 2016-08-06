package com.danidemi.obfuscatedids.spring.core;

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