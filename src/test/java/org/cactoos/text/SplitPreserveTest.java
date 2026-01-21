/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import java.util.ArrayList;
import java.util.List;
import org.cactoos.Text;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link SplitPreserveAllTokens}.
 *
 * <p>This class tests the "preserve all tokens" splitting behavior,
 * which differs from Java's {@link String#split(String)} by preserving
 * trailing empty tokens and ensuring that N delimiters always produce
 * N+1 tokens.</p>
 *
 * @since 0.0
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class SplitPreserveTest {

    // ===== Basic splitting tests =====

    @Test
    void splitsSimpleCommaSeparatedValues() {
        new Assertion<>(
            "Must split simple CSV correctly",
            this.toList(new SplitPreserveAllTokens("a,b,c", ",")),
            new IsEqual<>(java.util.Arrays.asList("a", "b", "c"))
        ).affirm();
    }

    @Test
    void splitsWithSpaceDelimiter() {
        new Assertion<>(
            "Must split by space correctly",
            this.toList(new SplitPreserveAllTokens("hello world", " ")),
            new IsEqual<>(java.util.Arrays.asList("hello", "world"))
        ).affirm();
    }

    @Test
    void splitsWithMultiCharDelimiter() {
        new Assertion<>(
            "Must split by multi-char delimiter",
            this.toList(new SplitPreserveAllTokens("a::b::c", "::")),
            new IsEqual<>(java.util.Arrays.asList("a", "b", "c"))
        ).affirm();
    }

    // ===== Adjacent delimiter tests (empty tokens in middle) =====

    @Test
    void preservesEmptyTokenBetweenAdjacentDelimiters() {
        new Assertion<>(
            "Must preserve empty token between adjacent delimiters",
            this.toList(new SplitPreserveAllTokens("a,,b", ",")),
            new IsEqual<>(java.util.Arrays.asList("a", "", "b"))
        ).affirm();
    }

    @Test
    void preservesMultipleEmptyTokens() {
        new Assertion<>(
            "Must preserve multiple empty tokens",
            this.toList(new SplitPreserveAllTokens("a,,,b", ",")),
            new IsEqual<>(java.util.Arrays.asList("a", "", "", "b"))
        ).affirm();
    }

    // ===== Trailing delimiter tests =====

    @Test
    void preservesTrailingEmptyToken() {
        new Assertion<>(
            "Must preserve trailing empty token after delimiter",
            this.toList(new SplitPreserveAllTokens("a,b,", ",")),
            new IsEqual<>(java.util.Arrays.asList("a", "b", ""))
        ).affirm();
    }

    @Test
    void preservesMultipleTrailingEmptyTokens() {
        new Assertion<>(
            "Must preserve multiple trailing empty tokens",
            this.toList(new SplitPreserveAllTokens("a,,", ",")),
            new IsEqual<>(java.util.Arrays.asList("a", "", ""))
        ).affirm();
    }

    // ===== Leading delimiter tests =====

    @Test
    void preservesLeadingEmptyToken() {
        new Assertion<>(
            "Must preserve leading empty token before delimiter",
            this.toList(new SplitPreserveAllTokens(",a,b", ",")),
            new IsEqual<>(java.util.Arrays.asList("", "a", "b"))
        ).affirm();
    }

    @Test
    void preservesMultipleLeadingEmptyTokens() {
        new Assertion<>(
            "Must preserve multiple leading empty tokens",
            this.toList(new SplitPreserveAllTokens(",,a", ",")),
            new IsEqual<>(java.util.Arrays.asList("", "", "a"))
        ).affirm();
    }

    // ===== Combined leading and trailing =====

    @Test
    void preservesBothLeadingAndTrailingEmptyTokens() {
        new Assertion<>(
            "Must preserve both leading and trailing empty tokens",
            this.toList(new SplitPreserveAllTokens(",a,", ",")),
            new IsEqual<>(java.util.Arrays.asList("", "a", ""))
        ).affirm();
    }

    @Test
    void preservesWithSpaceDelimiterLeadingTrailing() {
        new Assertion<>(
            "Must preserve leading/trailing with space delimiter",
            this.toList(new SplitPreserveAllTokens(" hello ", " ")),
            new IsEqual<>(java.util.Arrays.asList("", "hello", ""))
        ).affirm();
    }

    // ===== Only delimiters =====

    @Test
    void handlesStringOfOnlyOneDelimiter() {
        new Assertion<>(
            "Single delimiter must produce two empty tokens",
            this.toList(new SplitPreserveAllTokens(",", ",")),
            new IsEqual<>(java.util.Arrays.asList("", ""))
        ).affirm();
    }

    @Test
    void handlesStringOfOnlyTwoDelimiters() {
        new Assertion<>(
            "Two delimiters must produce three empty tokens",
            this.toList(new SplitPreserveAllTokens(",,", ",")),
            new IsEqual<>(java.util.Arrays.asList("", "", ""))
        ).affirm();
    }

    @Test
    void handlesStringOfOnlyThreeDelimiters() {
        new Assertion<>(
            "Three delimiters must produce four empty tokens",
            this.toList(new SplitPreserveAllTokens(",,,", ",")),
            new IsEqual<>(java.util.Arrays.asList("", "", "", ""))
        ).affirm();
    }

    @Test
    void handlesStringOfOnlySpaces() {
        new Assertion<>(
            "Two spaces must produce three empty tokens",
            this.toList(new SplitPreserveAllTokens("  ", " ")),
            new IsEqual<>(java.util.Arrays.asList("", "", ""))
        ).affirm();
    }

    // ===== Edge cases =====

    @Test
    void handlesEmptyString() {
        new Assertion<>(
            "Empty string must produce single empty token",
            this.toList(new SplitPreserveAllTokens("", ",")),
            new IsEqual<>(java.util.Arrays.asList(""))
        ).affirm();
    }

    @Test
    void handlesStringWithNoDelimiter() {
        new Assertion<>(
            "String without delimiter must return single token",
            this.toList(new SplitPreserveAllTokens("abc", ",")),
            new IsEqual<>(java.util.Arrays.asList("abc"))
        ).affirm();
    }

    @Test
    void handlesDelimiterSameAsContent() {
        new Assertion<>(
            "Content same as delimiter must produce correct tokens",
            this.toList(new SplitPreserveAllTokens("aaa", "a")),
            new IsEqual<>(java.util.Arrays.asList("", "", "", ""))
        ).affirm();
    }

    @Test
    void handlesDelimiterLongerThanSingleChar() {
        new Assertion<>(
            "Multi-char delimiter at edges must work",
            this.toList(new SplitPreserveAllTokens("abab", "ab")),
            new IsEqual<>(java.util.Arrays.asList("", "", ""))
        ).affirm();
    }

    // ===== Whitespace handling =====

    @Test
    void handlesTabDelimiter() {
        new Assertion<>(
            "Must split by tab correctly",
            this.toList(new SplitPreserveAllTokens("a\t\tb", "\t")),
            new IsEqual<>(java.util.Arrays.asList("a", "", "b"))
        ).affirm();
    }

    @Test
    void handlesMixedContent() {
        new Assertion<>(
            "Must handle mixed content with spaces",
            this.toList(new SplitPreserveAllTokens("lol\\  / dude", " ")),
            new IsEqual<>(java.util.Arrays.asList("lol\\", "", "/", "dude"))
        ).affirm();
    }

    // ===== Limit functionality =====

    @Test
    void limitsNumberOfTokens() {
        new Assertion<>(
            "Must limit to specified number of tokens",
            this.toList(new SplitPreserveAllTokens("a,b,c,d", ",", 2)),
            new IsEqual<>(java.util.Arrays.asList("a", "b"))
        ).affirm();
    }

    @Test
    void limitWithEmptyTokens() {
        new Assertion<>(
            "Limit must work with empty tokens",
            this.toList(new SplitPreserveAllTokens("a,,b,,c", ",", 3)),
            new IsEqual<>(java.util.Arrays.asList("a", "", "b"))
        ).affirm();
    }

    @Test
    void limitWithOnlyDelimiters() {
        new Assertion<>(
            "Limit must work with only delimiters",
            this.toList(new SplitPreserveAllTokens(",,,", ",", 2)),
            new IsEqual<>(java.util.Arrays.asList("", ""))
        ).affirm();
    }

    @Test
    void limitZeroReturnsAllTokens() {
        new Assertion<>(
            "Limit 0 must return all tokens",
            this.toList(new SplitPreserveAllTokens("a,b,c", ",", 0)),
            new IsEqual<>(java.util.Arrays.asList("a", "b", "c"))
        ).affirm();
    }

    @Test
    void limitGreaterThanTokenCountReturnsAll() {
        new Assertion<>(
            "Limit greater than token count must return all",
            this.toList(new SplitPreserveAllTokens("a,b", ",", 10)),
            new IsEqual<>(java.util.Arrays.asList("a", "b"))
        ).affirm();
    }

    // ===== Text object input =====

    @Test
    void acceptsTextObjects() {
        new Assertion<>(
            "Must accept Text objects",
            this.toList(
                new SplitPreserveAllTokens(
                    new TextOf("x,y,z"),
                    new TextOf(",")
                )
            ),
            new IsEqual<>(java.util.Arrays.asList("x", "y", "z"))
        ).affirm();
    }

    @Test
    void acceptsTextObjectsWithLimit() {
        new Assertion<>(
            "Must accept Text objects with limit",
            this.toList(
                new SplitPreserveAllTokens(
                    new TextOf("a,b,c,d"),
                    new TextOf(","),
                    2
                )
            ),
            new IsEqual<>(java.util.Arrays.asList("a", "b"))
        ).affirm();
    }

    // ===== Comparison with String.split() behavior =====

    @Test
    void differsFromStringSplitOnTrailingDelimiter() {
        // String.split(",") would return ["a", "b"] - losing trailing empty
        new Assertion<>(
            "Must differ from String.split by preserving trailing empty",
            this.toList(new SplitPreserveAllTokens("a,b,", ",")),
            Matchers.not(
                new IsEqual<>(
                    java.util.Arrays.asList("a,b,".split(","))
                )
            )
        ).affirm();
    }

    @Test
    void differsFromStringSplitOnOnlyDelimiters() {
        // String.split(",") would return [] - empty array
        new Assertion<>(
            "Must differ from String.split on only-delimiter string",
            this.toList(new SplitPreserveAllTokens(",,", ",")).size(),
            Matchers.greaterThan(0)
        ).affirm();
    }

    // ===== Default delimiter (space) =====

    @Test
    void usesSpaceAsDefaultDelimiter() {
        new Assertion<>(
            "Must use space as default delimiter",
            this.toList(new SplitPreserveAllTokens("a b c")),
            new IsEqual<>(java.util.Arrays.asList("a", "b", "c"))
        ).affirm();
    }

    @Test
    void defaultDelimiterPreservesEmptyTokens() {
        new Assertion<>(
            "Default space delimiter must preserve empty tokens",
            this.toList(new SplitPreserveAllTokens(" hello  world ")),
            new IsEqual<>(java.util.Arrays.asList("", "hello", "", "world", ""))
        ).affirm();
    }

    // ===== Real-world scenarios =====

    @Test
    void handlesCsvWithEmptyFields() {
        new Assertion<>(
            "Must handle CSV with empty fields",
            this.toList(new SplitPreserveAllTokens("John,,Smith,,", ",")),
            new IsEqual<>(
                java.util.Arrays.asList("John", "", "Smith", "", "")
            )
        ).affirm();
    }

    @Test
    void handlesTsvWithEmptyFields() {
        new Assertion<>(
            "Must handle TSV with empty fields",
            this.toList(new SplitPreserveAllTokens("A\t\tB\tC\t", "\t")),
            new IsEqual<>(
                java.util.Arrays.asList("A", "", "B", "C", "")
            )
        ).affirm();
    }

    /**
     * Helper method to convert Iterable of Text to List of String.
     * @param iterable The iterable to convert
     * @return List of strings
     */
    private List<String> toList(final Iterable<Text> iterable) {
        final List<String> result = new ArrayList<>();
        for (final Text text : iterable) {
            try {
                result.add(text.asString());
            } catch (final Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        return result;
    }
}
