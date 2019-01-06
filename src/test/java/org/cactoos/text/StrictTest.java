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
package org.cactoos.text;

import java.util.regex.Pattern;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link Strict}.
 *
 * @since 0.49.2
 * @checkstyle MagicNumber (500 line)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class StrictTest {

    /**
     * Ensures that Strict is failing on a negative predicate result.
     * @throws Exception If fails
     */
    @Test(expected = Exception.class)
    public void failsIfPredicateIsNegative() throws Exception {
        new Strict(s -> false, new TextOf("text")).asString();
    }

    /**
     * Ensures that Strict is returning unchanged string on a positive
     * predicate result.
     * @throws Exception If fails
     */
    @Test
    public void returnsUnchangedIfPredicateIsPositive() throws Exception {
        MatcherAssert.assertThat(
            "Given strings are not equal",
            new Strict(s -> true, new TextOf("text")).asString(),
            new IsEqual<>("text")
        );
    }

    /**
     * Ensures that Strict is failing on a text not matching a pattern.
     * @throws Exception If fails
     */
    @Test(expected = Exception.class)
    public void failsIfNotMatchedWithPattern() throws Exception {
        new Strict(
            Pattern.compile("^[a-zA-Z]+$"),
            new TextOf("text12")
        ).asString();
    }

    /**
     * Ensures that Strict is returning unchanged string
     * on a matched with pattern string.
     * @throws Exception If fails
     */
    @Test
    public void returnsUnchangedIfMatchedWithPattern() throws Exception {
        MatcherAssert.assertThat(
            "Given strings are not equal",
            new Strict(
                Pattern.compile("^[a-zA-Z0-9]+$"),
                new TextOf("text1")
            ).asString(),
            new IsEqual<>("text1")
        );
    }
}
