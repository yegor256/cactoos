/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NumberOf}.
 *
 * @since 1.0.0
 */
final class NumberOfTest {

    @Test
    void parsesFloat() {
        MatcherAssert.assertThat(
            "Must parse float number",
            new NumberOf("1656.894").floatValue(),
            new IsNumber(1656.894F)
        );
    }

    @Test
    void failsIfTextDoesNotRepresentAFloat() {
        MatcherAssert.assertThat(
            "Must fail parsing random text as float",
            () -> new NumberOf("abcfds").floatValue(),
            new Throws<>(RuntimeException.class)
        );
    }

    @Test
    void parsesLong() {
        MatcherAssert.assertThat(
            "Must parse long number",
            new NumberOf("186789235425346").longValue(),
            new IsNumber(186_789_235_425_346L)
        );
    }

    @Test
    void failsIfTextDoesNotRepresentALong() {
        MatcherAssert.assertThat(
            "Must fail parsing random text as long",
            () -> new NumberOf("abcddd").longValue(),
            new Throws<>(RuntimeException.class)
        );
    }

    @Test
    void parsesInteger() {
        MatcherAssert.assertThat(
            "Must parse integer number",
            new NumberOf("1867892354").intValue(),
            new IsNumber(1_867_892_354)
        );
    }

    @Test
    void failsIfTextDoesNotRepresentAnInt() {
        MatcherAssert.assertThat(
            "Must fail parsing random text as int",
            () -> new NumberOf("abc fdsf").intValue(),
            new Throws<>(RuntimeException.class)
        );
    }

    @Test
    void parsesDouble() {
        MatcherAssert.assertThat(
            "Must parse double number",
            new NumberOf("185.65156465123").doubleValue(),
            new IsNumber(185.651_564_651_23)
        );
    }

    @Test
    void failsIfTextDoesNotRepresentADouble() {
        MatcherAssert.assertThat(
            "Must fail parsing random text as double",
            () -> new NumberOf("abfdsc").doubleValue(),
            new Throws<>(RuntimeException.class)
        );
    }

    @Test
    void parsesValueInt() {
        MatcherAssert.assertThat(
            "Must parse into int",
            new NumberOf("185").intValue(),
            new IsNumber(185)
        );
    }

    @Test
    void parsesValueIntFromText() {
        MatcherAssert.assertThat(
            "Must parse from text",
            new NumberOf(new TextOf("186")).intValue(),
            new IsNumber(186)
        );
    }
}
