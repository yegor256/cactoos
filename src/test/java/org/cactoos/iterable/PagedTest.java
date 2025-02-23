/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Scalar;
import org.cactoos.iterator.IteratorOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.ScalarOf;
import org.cactoos.scalar.Ternary;
import org.hamcrest.collection.IsIterableWithSize;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Paged}.
 * @since 0.47
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
final class PagedTest {

    @Test
    @SuppressWarnings("unchecked")
    void containAllPagedContentInOrder() {
        final Iterable<String> first = new IterableOf<>("one", "two");
        final Iterable<String> second = new IterableOf<>("three", "four");
        final Iterable<String> third = new IterableOf<>("five");
        final Iterator<Iterable<String>> pages = new IteratorOf<>(
            first, second, third
        );
        new Assertion<Iterable<String>>(
            "must have all page values",
            new Paged<>(
                pages.next(),
                page -> new Ternary<>(
                    pages::hasNext,
                    pages::next,
                    (Scalar<Iterable<String>>) IterableOf::new
                ).value()
            ),
            new IsEqual<>(new Joined<>(first, second, third))
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void reportTotalPagedLength() throws AssertionError, Exception {
        final Iterable<String> first = new IterableOf<>("A", "six");
        final Iterable<String> second = new IterableOf<>("word", "long");
        final Iterable<String> third = new IterableOf<>("sentence");
        final Iterable<Iterable<String>> service = new IterableOf<>(
            first, second, third
        );
        final Iterator<Iterable<String>> pages = service.iterator();
        new Assertion<Iterable<String>>(
            "length must be equal to total number of elements",
            new Paged<>(
                pages.next(),
                page -> new Ternary<>(
                    pages::hasNext,
                    pages::next,
                    (Scalar<Iterable<String>>) IterableOf::new
                ).value()
            ),
            new IsIterableWithSize<>(
                new IsEqual<>(
                    new LengthOf(
                        new Joined<>(first, second, third)
                    ).value().intValue()
                )
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void throwsNoSuchElement() {
        final Iterable<Iterable<String>> service = new IterableOf<>();
        final Iterator<Iterable<String>> pages = service.iterator();
        new Assertion<Scalar<String>>(
            "must throw an exception when first iterator is empty",
            new ScalarOf<>(
                () -> new Paged<>(
                    pages.next(),
                    page -> new Ternary<>(
                        pages::hasNext,
                        pages::next,
                        (Scalar<Iterable<String>>) IterableOf::new
                    ).value()
                ).iterator().next()
            ),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

}
