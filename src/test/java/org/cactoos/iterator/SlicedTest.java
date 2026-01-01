/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Constant;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test for {@link Sliced}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SlicedTest {

    @Test
    void sliceTheMiddle() {
        new Assertion<>(
            "Must return sliced iterator",
            new IterableOf<>(
                new Sliced<>(
                    3,
                    2,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    4, 5
                )
            )
        ).affirm();
    }

    @Test
    void sliceTheHead() {
        new Assertion<>(
            "Must return iterator with the head elements",
            new IterableOf<>(
                new Sliced<>(
                    0,
                    5,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    1, 2, 3, 4, 5
                )
            )
        ).affirm();
    }

    @Test
    void sliceTheWholeTail() {
        new Assertion<>(
            "Must return the iterator of tail elements",
            new IterableOf<>(
                new Sliced<>(
                    5,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    6, 7, 8, 9, 0
                )
            )
        ).affirm();
    }

    @Test
    void failSlicing() {
        new Assertion<>(
            "Must fail on slicing",
            () -> new Constant<>(
                new Sliced<>(
                    0, 0,
                    new IteratorOf<>(
                        1
                    )
                ).next()
            ).value(),
            new Throws<>(
                "The iterator doesn't have items any more",
                NoSuchElementException.class
            )
        ).affirm();
    }

    /**
     * Tests that hasNext() calls the underlying iterator only once.
     */
    @Test
    void hasNextCallsOriginOnce() {
        final List<String> list = new LinkedList<>();
        list.add("1");
        final AtomicInteger counter = new AtomicInteger(0);
        final Iterator<String> hooked = new HookedIterator<>(
            counter::incrementAndGet,
            list.iterator()
        );
        final Iterator<String> sliced = new Sliced<>(0, hooked);
        sliced.hasNext();
        new Assertion<>(
            "Must call hasNext on underlying iterator only once",
            counter.get(),
            new IsEqual<>(1)
        ).affirm();
    }

    /**
     * Iterator that counts calls to hasNext and next.
     * @param <T> Type of items
     * @since 1.0
     */
    private static final class HookedIterator<T> implements Iterator<T> {

        /**
         * Hook to run on each method call.
         */
        private final Runnable hook;

        /**
         * Original iterator.
         */
        private final Iterator<T> origin;

        /**
         * Ctor.
         * @param hook Hook to run
         * @param origin Original iterator
         */
        HookedIterator(final Runnable hook, final Iterator<T> origin) {
            this.hook = hook;
            this.origin = origin;
        }

        @Override
        public boolean hasNext() {
            this.hook.run();
            return this.origin.hasNext();
        }

        @Override
        public T next() {
            this.hook.run();
            return this.origin.next();
        }
    }
}
