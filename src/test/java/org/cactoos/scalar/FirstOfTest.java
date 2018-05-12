/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.scalar;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.IterableOfInts;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Tests for {@link FirstOf}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class FirstOfTest {

    @Test
    public void returnsMatchingValue() {
        final int value = 1;
        MatcherAssert.assertThat(
            "Didn't return the only matching element",
            new FirstOf<>(
                i -> i >= value,
                new IterableOfInts(0, value),
                () -> -1
            ),
            new ScalarHasValue<>(value)
        );
    }

    @Test
    public void returnsFirstValueForMultipleMatchingOnes() {
        final String value = "1";
        MatcherAssert.assertThat(
            "Didn't return first matching element",
            new FirstOf<>(
                i -> !i.isEmpty(),
                new IterableOf<>("1", "2"),
                () -> ""
            ),
            new ScalarHasValue<>(value)
        );
    }

    @Test
    public void returnsFallbackIfNothingMatches() {
        final String value = "abc";
        MatcherAssert.assertThat(
            "Didn't return fallback",
            new FirstOf<>(
                i -> i.length() > 2,
                new IterableOf<>("ab", "cd"),
                () -> value
            ),
            new ScalarHasValue<>(value)
        );
    }

}
