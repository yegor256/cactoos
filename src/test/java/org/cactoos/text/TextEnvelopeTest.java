/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Tests for {@link TextEnvelope}.
 * @since 0.32
 */
final class TextEnvelopeTest {
    /**
     * Test for {@link TextEnvelope#asString()} method. Must assert that
     * the envelope asString value is equal to its string value.
     */
    @Test
    void testAsString() {
        final String text = "asString";
        new Assertion<>(
            "Envelope value does not match String value",
            new TextEnvelopeDummy(text),
            new IsText(text)
        ).affirm();
    }

    /**
     * Dummy class for {@link TextEnvelope} testing.
     * @since 0.32
     */
    private final class TextEnvelopeDummy extends TextEnvelope {
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

        /**
         * Ctor.
         *
         * @param scalar Text to be enveloped.
         */
        TextEnvelopeDummy(final Scalar<String> scalar) {
            super(new TextOf(scalar));
        }
    }
}
