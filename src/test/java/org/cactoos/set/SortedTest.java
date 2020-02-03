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
package org.cactoos.set;

import java.util.Comparator;
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sorted}.
 * @since 1.0.0
 * @checkstyle MagicNumber (500 line)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SortedTest {

    @Test
    public void mustSortIntegerArrayAsSetInAscendingOrder() {
        new Assertion<>(
            "Must keep unique integer numbers sorted",
            new Sorted<>(
                Integer::compareTo,
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(1),
                    new IsEqual<>(2),
                    new IsEqual<>(3)
                )
            )
        ).affirm();
    }

    @Test
    public void mustSortIntegerIterableAsSetInDescendingOrder() {
        new Assertion<>(
            "Must keep unique integer numbers sorted in descending order",
            new Sorted<>(
                Comparator.reverseOrder(),
                2, 1, 3, 2, 1
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Integer>>(
                    new IsEqual<>(3),
                    new IsEqual<>(2),
                    new IsEqual<>(1)
                )
            )
        ).affirm();
    }

    @Test
    public void mustSortTextIterableAsSetUsingCustomCOmparator() {
        new Assertion<>(
            "Must keep unique integer numbers sorted in descending order",
            new Sorted<>(
                (first, second) -> {
                    final String left = new UncheckedText(first).asString();
                    final String right = new UncheckedText(second).asString();
                    return left.compareTo(right);
                },
                new TextOf("cd"),
                new TextOf("ab"),
                new TextOf("gh"),
                new TextOf("ef")
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Text>>(
                    new IsEqual<>(new TextOf("ab")),
                    new IsEqual<>(new TextOf("cd")),
                    new IsEqual<>(new TextOf("ef")),
                    new IsEqual<>(new TextOf("gh"))
                )
            )
        ).affirm();
    }

    @Test
    public void mustNotBeEqualToSortedSet() {
        new Assertion<>(
            "Sorted set must not be equal to the tested collection",
            new Sorted<>(
                Integer::compareTo,
                2, 1, 3, 2, 1
            ),
            new IsNot<>(
                new IsIterableContainingInOrder<>(
                    new ListOf<Matcher<? super Integer>>(
                        new IsEqual<>(1),
                        new IsEqual<>(3),
                        new IsEqual<>(2)
                    )
                ))
        ).affirm();
    }
}
