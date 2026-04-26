/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Sticky}.
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
        MatcherAssert.assertThat(
            String.format(
                "Must ignore the changes in the underlying iterable, size=%d",
                size.get()
            ),
            new LengthOf(list).value(),
            new IsEqual<>(new LengthOf(list).value())
        );
    }

    @Test
    void isEmptyOnEmpty() {
        MatcherAssert.assertThat(
            "Must be empty",
            new Sticky<>(),
            new IsEmptyIterable<>()
        );
    }

    @Test
    void equalsIterable() {
        MatcherAssert.assertThat(
            "Must be equals to equivalent iterable",
            new Sticky<>(1, 2),
            new IsEqual<>(new IterableOf<>(1, 2))
        );
    }
}
