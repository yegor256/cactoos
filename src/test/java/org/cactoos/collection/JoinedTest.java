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
package org.cactoos.collection;

import java.util.Collections;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Joined}.
 *
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @todo #898:15min Replace Collections.emptyList() with the {@link ListOf}
 *  That's because we want to get rid of the static methods.
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class JoinedTest {

    @Test
    public void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a collection",
            new Joined<Integer>(
                new IterableOf<>(1, -1, 2, 0),
                new IterableOf<>(1, -1, 2, 0),
                new IterableOf<>(1, -1, 2, 0)
            ),
            new BehavesAsCollection<>(-1)
        ).affirm();
    }

    @Test
    public void size() {
        new Assertion<>(
            "Must have correct size",
            new Joined<String>(
                new IterableOf<>("hello", "world", "друг"),
                new IterableOf<>("how", "are", "you"),
                new IterableOf<>("what's", "up")
            ),
            new IsCollectionWithSize<>(new IsEqual<>(8))
        ).affirm();
    }

    @Test
    public void sizeEmptyReturnZero() {
        new Assertion<>(
            "Size must be 0",
            new Joined<String>(
                Collections.emptyList()
            ),
            new IsCollectionWithSize<>(new IsEqual<>(0))
        ).affirm();
    }

    @Test
    public void withItemsNotEmpty() {
        new Assertion<>(
            "Must be not empty",
            new Joined<String>(
                new IterableOf<>("1", "2"),
                new IterableOf<>("3", "4")
            ),
            new IsNot<>(new IsEmptyCollection<>())
        ).affirm();
    }

    @Test
    public void withoutItemsIsEmpty() {
        new Assertion<>(
            "Must be empty",
            new Joined<String>(
                Collections.emptyList()
            ),
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test()
    public void testAdd() {
        final Joined<Integer> joined = new Joined<>(
            new IterableOf<Iterable<Integer>>(
                new IterableOf<>(1, 2),
                new IterableOf<>(3, 4),
                new IterableOf<>(5, 6)
            )
        );
        joined.add(7);
        new Assertion<>(
            "Must contain added element",
            joined,
            new IsEqual<>(
                new ListOf<>(1, 2, 3, 4, 5, 6, 7)
            )
        ).affirm();
    }

    @Test()
    public void testRemove() {
        final Joined<String> joined = new Joined<>(
            new IterableOf<Iterable<String>>(
                new IterableOf<>("w", "a"),
                new IterableOf<>("b", "c")
            )
        );
        joined.remove("a");
        new Assertion<>(
            "Must not contain removed element",
            joined,
            new IsEqual<>(
                new ListOf<>("w", "b", "c")
            )
        ).affirm();
    }

    @Test()
    public void testAddAll() {
        final Joined<Integer> joined = new Joined<>(
            new IterableOf<Iterable<Integer>>(
                new IterableOf<>(111),
                new IterableOf<>(222),
                new IterableOf<>(333)
            )
        );
        joined.addAll(new ListOf<>(444, 555));
        new Assertion<>(
            "Must contain all added elements",
            joined,
            new IsEqual<>(
                new ListOf<>(111, 222, 333, 444, 555)
            )
        ).affirm();
    }

    @Test()
    public void testRemoveAll() {
        final Joined<Integer> joined = new Joined<>(
            new IterableOf<Iterable<Integer>>(
                new IterableOf<>(111, 222),
                new IterableOf<>(444)
            )
        );
        joined.removeAll(new ListOf<>(222, 444));
        new Assertion<>(
            "Must not contain all removed element",
            joined,
            new IsEqual<>(
                new ListOf<>(111)
            )
        ).affirm();
    }

    @Test()
    public void testRetainAll() {
        final Joined<Integer> joined = new Joined<>(
            new IterableOf<Iterable<Integer>>(
                new IterableOf<>(22),
                new IterableOf<>(11, 33)
            )
        );
        joined.retainAll(new ListOf<>(66, 11, 33));
        new Assertion<>(
            "Must contain all retained element",
            joined,
            new IsEqual<>(
                new ListOf<>(11, 33)
            )
        ).affirm();
    }

    @Test()
    public void testClear() {
        final Joined<Integer> joined = new Joined<>(
            new IterableOf<Iterable<Integer>>(
                new IterableOf<>(10),
                new IterableOf<>(20)
            )
        );
        joined.clear();
        new Assertion<>(
            "Must be empty after clear",
            joined,
            new IsEmptyCollection<>()
        ).affirm();
    }
}
