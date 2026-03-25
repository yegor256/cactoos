/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ImmutableListIterator}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
final class ImmutableListIteratorTest {

    @Test
    void mustReturnPreviousIndex() {
        MatcherAssert.assertThat(
            "Must return next previous index",
            new ImmutableListIterator<>(
                new ListOf<>(1).listIterator()
            ).previousIndex(),
            new IsEqual<>(-1)
        );
    }

    @Test
    void mustReturnPreviousElement() {
        MatcherAssert.assertThat(
            "Must return previous element",
            new ImmutableListIterator<>(
                new ListOf<>(3, 7).listIterator(1)
            ).previous(),
            new IsEqual<>(3)
        );
    }

    @Test
    void mustReturnNextIndex() {
        MatcherAssert.assertThat(
            "Must return next index",
            new ImmutableListIterator<>(
                new ListOf<>(1).listIterator()
            ).nextIndex(),
            new IsEqual<>(0)
        );
    }

    @Test
    void mustReturnNextElement() {
        MatcherAssert.assertThat(
            "Must return next element",
            new ImmutableListIterator<>(
                new ListOf<>(5, 11, 13).listIterator(1)
            ).next(),
            new IsEqual<>(11)
        );
    }

    @Test
    void mustRaiseErrorOnListIteratorRemove() {
        MatcherAssert.assertThat(
            "Must throw error if modified with remove operation",
            () -> {
                new ImmutableListIterator<>(
                    new ListOf<>(1, 2).listIterator()
                ).remove();
                return 0;
            },
            new Throws<>(
                "List Iterator is read-only and doesn't allow removing items",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void mustRaiseErrorOnListIteratorAdd() {
        MatcherAssert.assertThat(
            "Must throw error if modified with add operation",
            () -> {
                new ImmutableListIterator<>(
                    new ListOf<>(1, 2).listIterator()
                ).add(1);
                return 0;
            },
            new Throws<>(
                "List Iterator is read-only and doesn't allow adding items",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void mustRaiseErrorOnListIteratorSet() {
        MatcherAssert.assertThat(
            "Must throw error if modified with set operation",
            () -> {
                new ImmutableListIterator<>(
                    new ListOf<>(1, 2).listIterator()
                ).set(1);
                return 0;
            },
            new Throws<>(
                "List Iterator is read-only and doesn't allow rewriting items",
                UnsupportedOperationException.class
            )
        );
    }
}
