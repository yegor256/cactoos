/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link SuffixOf}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SuffixOfTest {

    /**
     * Ensures that After is returning empty string if
     * there is no given boundary.
     */
    @Test
    public void returnsEmptyIfThereIsNoBoundary() {
        new Assertion<>(
            "Given string is not empty",
            new SuffixOf("Cactoos with description", "after"),
            new TextIs("")
        ).affirm();
    }

    /**
     * Ensures that After is returning empty string if
     * given boundary is equal to given string.
     */
    @Test
    public void returnsEmptyIfStringIsBoundary() {
        new Assertion<>(
            "Given string is not empty",
            new SuffixOf("Boundary", "Boundary"),
            new TextIs("")
        ).affirm();
    }

    /**
     * Ensures that After is returning string
     * after given boundary.
     */
    @Test
    public void returnsAfterBoundaryString() {
        new Assertion<>(
            "Given strings are not equal",
            new SuffixOf("Anti-pattern", "Anti-"),
            new TextIs("pattern")
        ).affirm();
    }
}
