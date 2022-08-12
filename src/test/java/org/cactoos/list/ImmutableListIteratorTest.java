/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
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
