/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Fallback;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.And;
import org.cactoos.scalar.ScalarOf;
import org.cactoos.scalar.ScalarWithFallback;
import org.cactoos.scalar.Ternary;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Paged}.
 * @since 0.47
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class PagedTest {

    @Test
    @SuppressWarnings("unchecked")
    void containAllPagedContentInOrder() throws Exception {
        final Iterator<Iterator<String>> pages = new IteratorOf<>(
            new IteratorOf<>("one", "two"),
            new IteratorOf<>("three", "four"),
            new IteratorOf<>("five")
        );
        final Paged<String> paged = new Paged<>(
            pages.next(),
            page -> new Ternary<>(
                pages::hasNext,
                pages::next,
                () -> new IteratorOf<String>()
            ).value()
        );
        new Assertion<>(
            "must have all page values in order",
            new ScalarWithFallback<>(
                new And(
                    value -> {
                        value.forEachRemaining(m -> { });
                        return true;
                    },
                    new Matched<>(
                        String::equals,
                        paged,
                        new IteratorOf<>("one", "two", "three", "four", "five")
                    )
                ),
                new IterableOf<>(
                    new Fallback.From<>(
                        IllegalStateException.class,
                        ex -> false
                    )
                )
            ).value(),
            new IsTrue()
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void reportTotalPagedLength() {
        final Iterator<String> first = new IteratorOf<>("A", "six");
        final Iterator<String> second = new IteratorOf<>("word", "long");
        final Iterator<String> third = new IteratorOf<>("sentence");
        final Iterator<Iterator<String>> pages = new IteratorOf<>(
            first, second, third
        );
        final Paged<String> paged = new Paged<>(
            pages.next(),
            page -> new Ternary<>(
                pages::hasNext,
                pages::next,
                () -> new IteratorOf<String>()
            ).value()
        );
        int size = 0;
        while (paged.hasNext()) {
            size += 1;
            paged.next();
        }
        new Assertion<>(
            "length must be equal to total number of elements",
            size,
            new IsEqual<>(5)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void throwsNoSuchElement() {
        final Iterator<Iterator<String>> pages = new IteratorOf<>();
        new Assertion<Scalar<String>>(
            "must throw an exception when first iterator is empty",
            new ScalarOf<>(
                () -> new Paged<>(
                    pages.next(),
                    page -> new Ternary<>(
                        pages::hasNext,
                        pages::next,
                        () -> new IteratorOf<String>()
                    ).value()
                ).next()
            ),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
