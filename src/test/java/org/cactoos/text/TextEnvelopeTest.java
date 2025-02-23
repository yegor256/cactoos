/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
