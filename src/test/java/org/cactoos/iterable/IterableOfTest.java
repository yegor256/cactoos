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
package org.cactoos.iterable;

import java.util.Iterator;
import org.cactoos.iterator.IteratorOf;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.Ternary;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.collection.IsIterableWithSize;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link IterableOf}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class IterableOfTest {

    @Test
    public void convertsScalarsToIterable() {
        MatcherAssert.assertThat(
            "Can't convert scalars to iterable",
            new LengthOf(
                new IterableOf<>(
                    "a", "b", "c"
                )
            ).intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }

    @Test
    public void convertsArrayOfIntsToIterable() {
        MatcherAssert.assertThat(
            "Can't convert int scalars to iterable",
            new IterableOf<>(1, 2, 0, 2),
            Matchers.hasItem(0)
        );
    }

    @Test
    public void convertsObjectsToIterable() {
        MatcherAssert.assertThat(
            "Can't convert objects to iterable",
            new LengthOf(
                new IterableOf<>(
                    new TextOf("a"), new TextOf("b"), new TextOf("c")
                )
            ).intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }

    // @todo #971:30min Implement equals() and hashCode() on IterableEnvelope.
    //  Then, refactor this test so that only
    //  `new IsEqual<>(new Joined<>(first, second, third))` is required as a
    //  matcher.
    @Test
    @SuppressWarnings({"unchecked", "PMD.AvoidDuplicateLiterals"})
    public void containAllPagedContentInOrder() throws Exception {
        final Iterable<String> first = new IterableOf<>("one", "two");
        final Iterable<String> second = new IterableOf<>("three", "four");
        final Iterable<String> third = new IterableOf<>("five");
        final Iterable<Iterable<String>> service = new IterableOf<>(
            first, second, third
        );
        final Iterator<Iterable<String>> pages = service.iterator();
        MatcherAssert.assertThat(
            "Must have all page values",
            new IterableOf<>(
                () -> pages.next().iterator(),
                page -> new Ternary<>(
                    () -> pages.hasNext(),
                    () -> pages.next().iterator(),
                    () -> new IteratorOf<String>()
                ).value()
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<>(
                    new IsEqual<>("one"),
                    new IsEqual<>("two"),
                    new IsEqual<>("three"),
                    new IsEqual<>("four"),
                    new IsEqual<>("five")
                )
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void reportTotalPagedLength() throws Exception {
        final Iterable<String> first = new IterableOf<>("A", "five");
        final Iterable<String> second = new IterableOf<>("word", "long");
        final Iterable<String> third = new IterableOf<>("sentence");
        final Iterable<Iterable<String>> service = new IterableOf<>(
            first, second, third
        );
        final Iterator<Iterable<String>> pages = service.iterator();
        MatcherAssert.assertThat(
            "Length must be equal to total number of elements",
            new IterableOf<>(
                () -> pages.next().iterator(),
                page -> new Ternary<>(
                    () -> pages.hasNext(),
                    () -> pages.next().iterator(),
                    () -> new IteratorOf<String>()
                ).value()
            ),
            new IsIterableWithSize<>(
                new IsEqual<>(
                    new LengthOf(
                        new Joined<>(first, second, third)
                    ).intValue()
                )
            )
        );
    }
}
