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
package org.cactoos.set;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link SetOf}.
 * @since 0.49.2
 * @checkstyle MagicNumber (500 line)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SetOfTest {

    @Test
    public void behaveAsSetWithOriginalDuplicationsInTheTail() {
        new Assertion<>(
            "Must keep unique integer numbers",
            new SetOf<>(1, 2, 2),
            new AllOf<>(
                new IterableOf<Matcher<? super SetOf<Integer>>>(
                    new HasSize(2),
                    new IsCollectionContaining<>(new IsEqual<>(1)),
                    new IsCollectionContaining<>(new IsEqual<>(2))
                )
            )
        ).affirm();
    }

    @Test
    public void behaveAsSetWithOriginalDuplicationsInTheHead() {
        new Assertion<>(
            "Must keep unique integer numbers",
            new SetOf<>(1, 1, 2, 3),
            new AllOf<>(
                new IterableOf<Matcher<? super SetOf<Integer>>>(
                    new HasSize(3),
                    new IsCollectionContaining<>(new IsEqual<>(1)),
                    new IsCollectionContaining<>(new IsEqual<>(2)),
                    new IsCollectionContaining<>(new IsEqual<>(3))
                )
            )
        ).affirm();
    }

    @Test
    public void behaveAsSetWithOriginalDuplicationsInTheMiddle() {
        new Assertion<>(
            "Must keep unique integer numbers",
            new SetOf<>(1, 2, 2, 3),
            new AllOf<>(
                new IterableOf<Matcher<? super SetOf<Integer>>>(
                    new HasSize(3),
                    new IsCollectionContaining<>(new IsEqual<>(1)),
                    new IsCollectionContaining<>(new IsEqual<>(2)),
                    new IsCollectionContaining<>(new IsEqual<>(3))
                )
            )
        ).affirm();
    }

    @Test
    public void behaveAsSetMergedCollectionsOfLiteralsWithDuplicates() {
        new Assertion<>(
            "Must keep unique string literals",
            new SetOf<String>(
                new Joined<String>(
                    new IterableOf<>("cc", "ff"),
                    new IterableOf<>("aa", "bb", "cc", "dd")
                )
            ),
            new AllOf<>(
                new IterableOf<Matcher<? super SetOf<String>>>(
                    new HasSize(5),
                    new IsCollectionContaining<>(new IsEqual<>("aa")),
                    new IsCollectionContaining<>(new IsEqual<>("bb")),
                    new IsCollectionContaining<>(new IsEqual<>("cc")),
                    new IsCollectionContaining<>(new IsEqual<>("dd")),
                    new IsCollectionContaining<>(new IsEqual<>("ff"))
                )
            )
        ).affirm();
    }

    @Test
    public void behaveAsSetWithOriginalDuplicationsOfCharsInTheMiddle() {
        new Assertion<>(
            "Must keep unique characters",
            new SetOf<>('a', 'b', 'b', 'c', 'a', 'b', 'd'),
            new AllOf<>(
                new IterableOf<Matcher<? super SetOf<Character>>>(
                    new HasSize(4),
                    new IsCollectionContaining<>(new IsEqual<>('a')),
                    new IsCollectionContaining<>(new IsEqual<>('b')),
                    new IsCollectionContaining<>(new IsEqual<>('c')),
                    new IsCollectionContaining<>(new IsEqual<>('d'))
                )
            )
        ).affirm();
    }

    @Test
    public void behaveAsSetWithOriginalDuplicationsOfDoublesInTheMiddle() {
        new Assertion<>(
            "Must keep unique double numbers",
            new SetOf<>(1.5d, 2.4d, 1.5d, 2.4d, 2.4d, 1.5d),
            new AllOf<>(
                new IterableOf<Matcher<? super SetOf<Double>>>(
                    new HasSize(2),
                    new IsCollectionContaining<>(new IsEqual<>(1.5d)),
                    new IsCollectionContaining<>(new IsEqual<>(2.4d))
                )
            )
        ).affirm();
    }

    @Test
    public void behaveAsSetWithOriginalDuplicationsOfTextsInTheMiddle() {
        new Assertion<>(
            "Must keep unique TextOf objects",
            new SetOf<>(
                new TextOf("12345"),
                new TextOf("67890"),
                new TextOf("12345"),
                new TextOf("00000")
            ),
            new AllOf<>(
                new IterableOf<Matcher<? super SetOf<TextOf>>>(
                    new HasSize(3),
                    new IsCollectionContaining<>(
                        new IsEqual<>(new TextOf("12345"))
                    ),
                    new IsCollectionContaining<>(
                        new IsEqual<>(new TextOf("67890"))
                    ),
                    new IsCollectionContaining<>(
                        new IsEqual<>(new TextOf("00000"))
                    )
                )
            )
        ).affirm();
    }
}
