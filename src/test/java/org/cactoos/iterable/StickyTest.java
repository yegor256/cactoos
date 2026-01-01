/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sticky}.
 *
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class StickyTest {

    @Test
    void ignoresChangesInIterable() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final Iterable<Integer> list = new Sticky<>(
            new ListOf<>(
                () -> Collections.nCopies(size.incrementAndGet(), 0).iterator()
            )
        );
        new Assertion<>(
            "Must ignore the changes in the underlying iterable",
            new LengthOf(list).value(),
            new IsEqual<>(new LengthOf(list).value())
        ).affirm();
    }

    @Test
    void testEmpty() {
        new Assertion<>(
            "Must be empty",
            new Sticky<>(),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void testEqualsIterable() {
        new Assertion<>(
            "Must be equals to equivalent iterable",
            new Sticky<>(1, 2),
            new IsEqual<>(new IterableOf<>(1, 2))
        ).affirm();
    }
}
