/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test Case for {@link Skipped}.
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SkippedTest {

    @Test
    public void skipIterable() {
        final String one = "one";
        final String two = "two";
        final String three = "three";
        final String four = "four";
        new Assertion<>(
            "Must skip elements in iterable",
            new Skipped<>(
                2,
                new IterableOf<>(one, two, three, four)
            ),
            new IsEqual<>(
                new IterableOf<>(
                    three,
                    four
                )
            )
        ).affirm();
    }

    @Test
    public void skipArray() {
        final String five = "five";
        final String six = "six";
        final String seven = "seven";
        final String eight = "eight";
        new Assertion<>(
            "Must skip elements in array",
            new Skipped<>(
                2,
                five, six, seven, eight
            ),
            new IsEqual<>(
                new IterableOf<>(
                    seven,
                    eight
                )
            )
        ).affirm();
    }

    @Test
    public void skipCollection() {
        final String nine = "nine";
        final String eleven = "eleven";
        final String twelve = "twelve";
        final String hundred = "hundred";
        new Assertion<>(
            "Must skip elements in collection",
            new Skipped<>(
                2,
                new ListOf<>(nine, eleven, twelve, hundred)
            ),
            new IsEqual<>(
                new IterableOf<>(twelve, hundred)
            )
        ).affirm();
    }

    @Test
    public void skippedAllElements() {
        new Assertion<>(
            "Must skip all elements",
            new Skipped<>(
                2,
                "Frodo", "Gandalf"
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    public void skippedMoreThanExists() {
        new Assertion<>(
            "Can't skip more than exists",
            new Skipped<>(
                Integer.MAX_VALUE,
                "Sauron", "Morgoth"
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void skippedNegativeSize() {
        final String varda = "varda";
        final String yavanna = "yavanna";
        final String nessa = "nessa";
        final String vaire = "vaire";
        new Assertion<>(
            "Must process negative skipped size",
            new Skipped<>(
                -1,
                varda, yavanna, nessa, vaire
            ),
            new IsEqual<>(
                new IterableOf<>(varda, yavanna, nessa, vaire)
            )
        ).affirm();
    }
}
