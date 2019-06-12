/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.Scalar;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Tests for {@link TextEnvelope}.
 * @since 0.32
 */
public final class TextEnvelopeTest {
    /**
     * Test for {@link TextEnvelope#asString()} method. Must assert that
     * the envelope asString value is equal to its string value.
     * @throws Exception Throws from asString.
     */
    @Test
    public void testAsString() throws Exception {
        final String text = "asString";
        MatcherAssert.assertThat(
            "Envelope value does not match String value",
            new TextEnvelopeDummy(text).asString(),
            new IsEqual<>(text)
        );
    }
    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is equal another text representing the same
     * value.
     */
    @Test
    public void testEquals() {
        final String text = "equals";
        MatcherAssert.assertThat(
            "Envelope does not match text representing the same value",
            new TextEnvelopeDummy(text),
            new IsEqual<>(new TextOf(text))
        );
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is equal another text representing the same
     * value (in this case a {@link Joined}).
     */
    @Test
    public void testEqualsOtherText() {
        MatcherAssert.assertThat(
            "Envelope does not match another text representing the same value",
            new TextEnvelopeDummy("isequaltoanothertext"),
            new IsEqual<>(
                new Joined("", "is", "equal", "to", "another", "text")
            )
        );
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is not equal another object not being a
     * instance of Text without failing
     */
    @Test
    public void testDoesNotEqualsNonTextObject() {
        MatcherAssert.assertThat(
            "Envelope does not match another object which is not a string",
            new TextEnvelopeDummy("is not equals to null"),
            new IsNot<>(
                new IsEqual<>(new Object())
            )
        );
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is not equal to null without failing
     */
    @Test
    @SuppressWarnings("PMD.EqualsNull")
    public void testDoesNotEqualsFalse() {
        MatcherAssert.assertThat(
            "Envelope does not equals null",
            new TextEnvelopeDummy("is not equals to not Text object")
                .equals(null),
            new IsEqual<>(false)
        );
    }

    /**
     * Test for {@link TextEnvelope#hashCode()} method. Must assert that
     * the {@link TextEnvelope} hashCode is equals to the hashCode of
     * the String it represents.
     */
    @Test
    public void testHashCode() {
        final String hash = "hashCode";
        MatcherAssert.assertThat(
            "Enveloped hashCode does not match its represented String hashcode",
            new TextEnvelopeDummy(hash).hashCode(),
            new IsEqual<>(hash.hashCode())
        );
    }

    /**
     * Dummy class for {@link TextEnvelope} testing.
     */
    private final class TextEnvelopeDummy extends TextEnvelope {

        /**
         * Ctor.
         *
         * @param scalar Text to be enveloped.
         */
        TextEnvelopeDummy(final Scalar<String> scalar) {
            super(scalar);
        }
        /**
         * Ctor.
         *
         * @param input The String
         */
        TextEnvelopeDummy(final String input) {
            this(input, StandardCharsets.UTF_8);
        }

        /**
         * Ctor.
         *
         * @param input The String
         * @param cset The Charset
         */
        TextEnvelopeDummy(final String input,
            final Charset cset) {
            this(() -> new String(input.getBytes(cset), cset));
        }
    }
}
