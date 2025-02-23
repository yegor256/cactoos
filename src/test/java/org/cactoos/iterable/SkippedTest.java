/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test Case for {@link Skipped}.
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SkippedTest {

    @Test
    void skipIterable() {
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
    void skipArray() {
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
    void skipCollection() {
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
    void skippedAllElements() {
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
    void skippedMoreThanExists() {
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
    void skippedNegativeSize() {
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
