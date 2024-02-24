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
