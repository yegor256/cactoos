/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ImmutableListIterator}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods",
    "PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class ImmutableListIteratorTest {

    @Test
    void mustReturnPreviousIndex() {
        new Assertion<>(
            "Must return next previous index",
            new ImmutableListIterator<>(
                new ListOf<>(1).listIterator()
            ).previousIndex(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void mustReturnPreviousElement() {
        new Assertion<>(
            "Must return previous element",
            new ImmutableListIterator<>(
                new ListOf<>(3, 7).listIterator(1)
            ).previous(),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    void mustReturnNextIndex() {
        new Assertion<>(
            "Must return next index",
            new ImmutableListIterator<>(
                new ListOf<>(1).listIterator()
            ).nextIndex(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void mustReturnNextElement() {
        new Assertion<>(
            "Must return next element",
            new ImmutableListIterator<>(
                new ListOf<>(5, 11, 13).listIterator(1)
            ).next(),
            new IsEqual<>(11)
        ).affirm();
    }

    @Test
    void mustRaiseErrorOnListIteratorRemove() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void mustRaiseErrorOnListIteratorAdd() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void mustRaiseErrorOnListIteratorSet() {
        new Assertion<>(
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
        ).affirm();
    }
}
