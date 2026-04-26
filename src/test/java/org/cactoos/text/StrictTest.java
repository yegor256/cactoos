/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.regex.Pattern;
import org.cactoos.func.FuncOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Strict}.
 *
 * @since 1.0
 */
final class StrictTest {

    /**
     * Ensures that Strict accepts a CharSequence predicate.
     */
    @Test
    void acceptsCharSequencePredicate() {
        MatcherAssert.assertThat(
            "Must be equal strings",
            new Strict(
                new FuncOf<>(seq -> seq.length() > 3),
                new TextOf("sequence")
            ),
            new IsText("sequence")
        );
    }

    @Test
    void failsIfPredicateIsNegative() {
        MatcherAssert.assertThat(
            "Must throw IllegalArgumentException",
            () -> new Strict(s -> false, new TextOf("text")).asString(),
            new Throws<>(
                "String 'text' does not match a given predicate",
                IllegalArgumentException.class
            )
        );
    }

    /**
     * Ensures that Strict is returning unchanged string on a positive
     * predicate result.
     */
    @Test
    void returnsUnchangedIfPredicateIsPositive() {
        MatcherAssert.assertThat(
            "Given strings are not equal",
            new Strict(s -> true, new TextOf("text")),
            new IsText("text")
        );
    }

    @Test
    void failsIfNotMatchedWithPattern() {
        MatcherAssert.assertThat(
            "Must throw IllegalArgumentException",
            () -> new Strict(
                Pattern.compile("^[a-zA-Z]+$"),
                new TextOf("text12")
            ).asString(),
            new Throws<>(
                "String 'text12' does not match a given predicate",
                IllegalArgumentException.class
            )
        );
    }

    /**
     * Ensures that Strict is returning unchanged string
     * on a matched with pattern string.
     */
    @Test
    void returnsUnchangedIfMatchedWithPattern() {
        MatcherAssert.assertThat(
            "Given strings are not equal",
            new Strict(
                Pattern.compile("^[a-zA-Z0-9]+$"),
                new TextOf("text1")
            ),
            new IsText("text1")
        );
    }
}
