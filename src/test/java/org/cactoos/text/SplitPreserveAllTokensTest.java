/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * A test class for {@link SplitPreserveAllTokens}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class SplitPreserveAllTokensTest {

    /**
     * Test method for {@link SplitPreserveAllTokens#SplitPreserveAllTokens(Text)}.
     * <p>
     */
    @Test
    void splitForEmptyString() {
        final SplitPreserveAllTokens texts = new SplitPreserveAllTokens(new TextOf(""));
        new Assertion<>(
            "Must split empty string",
            texts,
            new IsEqual<>(new IterableOf<>(new TextOf("")))
        ).affirm();
    }

    /**
     * Test method for {@link SplitPreserveAllTokens#SplitPreserveAllTokens(Text)}.
     * <p>
     * This test verifies the splitting behavior of
     * {@link SplitPreserveAllTokens} class when the input string contains a single space.
     * The expected result is an iterable containing the substrings "abc" and "def".
     */
    @Test
    void splitForOneSpace() {
        final SplitPreserveAllTokens texts = new SplitPreserveAllTokens(new TextOf("abc def"));
        new Assertion<>(
            "Must split string with one space",
            texts,
            new IsEqual<>(new IterableOf<>(new TextOf("abc"), new TextOf("def")))
        ).affirm();
    }

    /**
     * Test method for {@link SplitPreserveAllTokens#SplitPreserveAllTokens(Text)}.
     * <p>
     * This test verifies the splitting behavior of
     * {@link SplitPreserveAllTokens} class when the input string contains two consecutive spaces.
     * The expected result is an iterable containing the substrings "abc", empty string, and "def".
     */
    @Test
    void splitForTwoSpaces() {
        final SplitPreserveAllTokens texts = new SplitPreserveAllTokens(new TextOf("abc  def"));
        new Assertion<>(
            "Must split string with two spaces",
            texts,
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("abc"), new TextOf(""), new TextOf("def")
                ))
        ).affirm();
    }

    /**
     * Test method for {@link SplitPreserveAllTokens#SplitPreserveAllTokens(Text)}.
     * <p>
     * This test verifies the splitting behavior of {@link SplitPreserveAllTokens} class
     * when the input string starts with a space.
     */
    @Test
    void splitForLeadingSpaces() {
        final SplitPreserveAllTokens texts = new SplitPreserveAllTokens(new TextOf(" abc"));
        new Assertion<>(
            "Must split string with leading spaces",
            texts,
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf(""), new TextOf("abc")
                ))
        ).affirm();
    }

    /**
     * Test method for {@link SplitPreserveAllTokens#SplitPreserveAllTokens(Text)}.
     * <p>
     * This test verifies the splitting behavior of {@link SplitPreserveAllTokens} class
     * when the input string both starts and ends with a space.
     */
    @Test
    void splitForTrailingSpaces() {
        final SplitPreserveAllTokens texts = new SplitPreserveAllTokens(new TextOf(" abc "));
        new Assertion<>(
            "Must split string with trailing spaces",
            texts,
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf(""), new TextOf("abc"), new TextOf("")
                ))
        ).affirm();
    }
}
