/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.func;

import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link TriFuncSplitPreserve}.
 *
 * <p>This class tests the low-level splitting function that preserves
 * all tokens, including empty ones created by adjacent delimiters.</p>
 *
 * @since 0.0
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class TriFuncSplitPreserveTest {

    @Test
    void splitsSimpleString() {
        new Assertion<>(
            "Must split simple string",
            new TriFuncSplitPreserve().apply("a,b,c", ",", 0),
            new IsEqual<>(Arrays.asList("a", "b", "c"))
        ).affirm();
    }

    @Test
    void preservesEmptyTokenInMiddle() {
        new Assertion<>(
            "Must preserve empty token between adjacent delimiters",
            new TriFuncSplitPreserve().apply("a,,b", ",", 0),
            new IsEqual<>(Arrays.asList("a", "", "b"))
        ).affirm();
    }

    @Test
    void preservesTrailingEmptyToken() {
        new Assertion<>(
            "Must preserve trailing empty token",
            new TriFuncSplitPreserve().apply("a,b,", ",", 0),
            new IsEqual<>(Arrays.asList("a", "b", ""))
        ).affirm();
    }

    @Test
    void preservesLeadingEmptyToken() {
        new Assertion<>(
            "Must preserve leading empty token",
            new TriFuncSplitPreserve().apply(",a,b", ",", 0),
            new IsEqual<>(Arrays.asList("", "a", "b"))
        ).affirm();
    }

    @Test
    void handlesOnlyDelimiters() {
        new Assertion<>(
            "Two delimiters must produce three empty tokens",
            new TriFuncSplitPreserve().apply(",,", ",", 0),
            new IsEqual<>(Arrays.asList("", "", ""))
        ).affirm();
    }

    @Test
    void handlesSingleDelimiter() {
        new Assertion<>(
            "Single delimiter must produce two empty tokens",
            new TriFuncSplitPreserve().apply(",", ",", 0),
            new IsEqual<>(Arrays.asList("", ""))
        ).affirm();
    }

    @Test
    void handlesEmptyString() {
        new Assertion<>(
            "Empty string must produce single empty token",
            new TriFuncSplitPreserve().apply("", ",", 0),
            new IsEqual<>(Arrays.asList(""))
        ).affirm();
    }

    @Test
    void handlesNoDelimiter() {
        new Assertion<>(
            "String without delimiter must return single token",
            new TriFuncSplitPreserve().apply("abc", ",", 0),
            new IsEqual<>(Arrays.asList("abc"))
        ).affirm();
    }

    @Test
    void handlesRepeatedDelimiterAsContent() {
        new Assertion<>(
            "Content same as delimiter produces correct tokens",
            new TriFuncSplitPreserve().apply("aaa", "a", 0),
            new IsEqual<>(Arrays.asList("", "", "", ""))
        ).affirm();
    }

    @Test
    void handlesMultiCharDelimiter() {
        new Assertion<>(
            "Multi-char delimiter must work",
            new TriFuncSplitPreserve().apply("a::b::c", "::", 0),
            new IsEqual<>(Arrays.asList("a", "b", "c"))
        ).affirm();
    }

    @Test
    void limitsTokenCount() {
        new Assertion<>(
            "Must limit token count",
            new TriFuncSplitPreserve().apply("a,b,c,d", ",", 2),
            new IsEqual<>(Arrays.asList("a", "b"))
        ).affirm();
    }

    @Test
    void limitWithEmptyTokens() {
        new Assertion<>(
            "Limit must count empty tokens",
            new TriFuncSplitPreserve().apply(",,,", ",", 2),
            new IsEqual<>(Arrays.asList("", ""))
        ).affirm();
    }

    @Test
    void limitZeroReturnsAll() {
        new Assertion<>(
            "Limit 0 must return all tokens",
            new TriFuncSplitPreserve().apply("a,b,c", ",", 0),
            new IsEqual<>(Arrays.asList("a", "b", "c"))
        ).affirm();
    }

    @Test
    void handlesSpaceDelimiter() {
        new Assertion<>(
            "Must handle space delimiter",
            new TriFuncSplitPreserve().apply(" hello ", " ", 0),
            new IsEqual<>(Arrays.asList("", "hello", ""))
        ).affirm();
    }

    @Test
    void handlesTabDelimiter() {
        new Assertion<>(
            "Must handle tab delimiter",
            new TriFuncSplitPreserve().apply("a\t\tb", "\t", 0),
            new IsEqual<>(Arrays.asList("a", "", "b"))
        ).affirm();
    }

    @Test
    void handlesEmptyDelimiter() {
        final Collection<String> result =
            new TriFuncSplitPreserve().apply("abc", "", 0);
        new Assertion<>(
            "Empty delimiter must return original string",
            result.size(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void handlesCsvScenario() {
        new Assertion<>(
            "Must handle CSV with empty fields",
            new TriFuncSplitPreserve().apply("John,,Smith,,", ",", 0),
            new IsEqual<>(Arrays.asList("John", "", "Smith", "", ""))
        ).affirm();
    }
}
