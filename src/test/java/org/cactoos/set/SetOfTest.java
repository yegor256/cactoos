/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link SetOf}.
 * @since 0.49.2
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class SetOfTest {

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsInTheTail() {
        new Assertion<>(
            "Must keep unique integer numbers",
            new SetOf<>(1, 2, 2),
            new AllOf<>(
                new HasSize(2),
                new HasValues<>(1, 2)
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsInTheHead() {
        new Assertion<>(
            "Must keep unique integer numbers",
            new SetOf<>(1, 1, 2, 3),
            new AllOf<>(
                new HasSize(3),
                new HasValues<>(1, 2, 3)
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsInTheMiddle() {
        new Assertion<>(
            "Must keep unique integer numbers",
            new SetOf<>(1, 2, 2, 3),
            new AllOf<>(
                new HasSize(3),
                new HasValues<>(1, 2, 3)
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetMergedCollectionsOfLiteralsWithDuplicates() {
        new Assertion<>(
            "Must keep unique string literals",
            new SetOf<>(
                new Joined<String>(
                    new IterableOf<>("cc", "ff"),
                    new IterableOf<>("aa", "bb", "cc", "dd")
                )
            ),
            new AllOf<>(
                new HasSize(5),
                new HasValues<>("aa", "bb", "cc", "dd", "ff")
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsOfCharsInTheMiddle() {
        new Assertion<>(
            "Must keep unique characters",
            new SetOf<>('a', 'b', 'b', 'c', 'a', 'b', 'd'),
            new AllOf<>(
                new HasSize(4),
                new HasValues<>('a', 'b', 'c', 'd')
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsOfDoublesInTheMiddle() {
        new Assertion<>(
            "Must keep unique double numbers",
            new SetOf<>(1.5d, 2.4d, 1.5d, 2.4d, 2.4d, 1.5d),
            new AllOf<>(
                new HasSize(2),
                new HasValues<>(1.5d, 2.4d)
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void behaveAsSetWithOriginalDuplicationsOfTextsInTheMiddle() {
        new Assertion<SetOf<Text>>(
            "Must keep unique TextOf objects",
            new SetOf<>(
                new TextOf("12345"),
                new TextOf("67890"),
                new TextOf("12345"),
                new TextOf("00000")
            ),
            new AllOf<>(
                new HasSize(3),
                new HasValues<>(
                    new TextOf("12345"),
                    new TextOf("67890"),
                    new TextOf("00000")
                )
            )
        ).affirm();
    }
}
