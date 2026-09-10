/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.Arrays;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TriFuncSplitPreserve}.
 * @since 0.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class TriFuncSplitPreserveTest {

    @Test
    void splitsSimpleString() {
        MatcherAssert.assertThat(
            "Must split simple string",
            new TriFuncSplitPreserve().apply("a,b,c", ",", 0),
            new IsEqual<>(Arrays.asList("a", "b", "c"))
        );
    }

    @Test
    void preservesEmptyTokenInMiddle() {
        MatcherAssert.assertThat(
            "Must preserve empty token between adjacent delimiters",
            new TriFuncSplitPreserve().apply("a,,b", ",", 0),
            new IsEqual<>(Arrays.asList("a", "", "b"))
        );
    }

    @Test
    void preservesTrailingEmptyToken() {
        MatcherAssert.assertThat(
            "Must preserve trailing empty token",
            new TriFuncSplitPreserve().apply("a,b,", ",", 0),
            new IsEqual<>(Arrays.asList("a", "b", ""))
        );
    }

    @Test
    void preservesLeadingEmptyToken() {
        MatcherAssert.assertThat(
            "Must preserve leading empty token",
            new TriFuncSplitPreserve().apply(",a,b", ",", 0),
            new IsEqual<>(Arrays.asList("", "a", "b"))
        );
    }

    @Test
    void preservesOnlyDelimiters() {
        MatcherAssert.assertThat(
            "Must preserve tokens for only delimiters",
            new TriFuncSplitPreserve().apply(",,", ",", 0),
            new IsEqual<>(Arrays.asList("", "", ""))
        );
    }

    @Test
    void preservesSingleDelimiter() {
        MatcherAssert.assertThat(
            "Must preserve tokens for single delimiter",
            new TriFuncSplitPreserve().apply(",", ",", 0),
            new IsEqual<>(Arrays.asList("", ""))
        );
    }

    @Test
    void returnsEmptyTokenForEmptyInput() {
        MatcherAssert.assertThat(
            "Must return one empty token for empty input",
            new TriFuncSplitPreserve().apply("", ",", 0),
            new IsEqual<>(Collections.singletonList(""))
        );
    }

    @Test
    void returnsWholeStringWithoutDelimiter() {
        MatcherAssert.assertThat(
            "Must return whole string when delimiter is missing",
            new TriFuncSplitPreserve().apply("abc", ",", 0),
            new IsEqual<>(Collections.singletonList("abc"))
        );
    }

    @Test
    void respectsPositiveLimit() {
        MatcherAssert.assertThat(
            "Must respect positive limit",
            new TriFuncSplitPreserve().apply("a,b,c,d", ",", 2),
            new IsEqual<>(Arrays.asList("a", "b"))
        );
    }

    @Test
    void splitsByMultiCharDelimiter() {
        MatcherAssert.assertThat(
            "Must split by multi-char delimiter",
            new TriFuncSplitPreserve().apply("a::b::c", "::", 0),
            new IsEqual<>(Arrays.asList("a", "b", "c"))
        );
    }

    @Test
    void splitsBySpace() {
        MatcherAssert.assertThat(
            "Must split by space preserving edges",
            new TriFuncSplitPreserve().apply(" hello  world ", " ", 0),
            new IsEqual<>(Arrays.asList("", "hello", "", "world", ""))
        );
    }
}
