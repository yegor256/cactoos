/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link IteratorOfChars}.
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfCharsTest {

    @Test
    void canBeConstructedFromText() {
        MatcherAssert.assertThat(
            "Iterator must contain all characters of the string",
            new IterableOf<>(
                new IteratorOfChars(
                    new TextOf("abc")
                )
            ),
            new HasValues<>('a', 'b', 'c')
        );
    }

    @Test
    void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for empty iterator",
            new IteratorOfChars().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void emptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected on iterating empty string",
            () -> new IteratorOfChars().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOfChars iterator = new IteratorOfChars(
            'a', 'b', 'c'
        );
        iterator.next();
        iterator.next();
        iterator.next();
        MatcherAssert.assertThat(
            "hasNext is true for already traversed iterator",
            iterator.hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        final IteratorOfChars iterator = new IteratorOfChars(
            'a'
        );
        iterator.next();
        MatcherAssert.assertThat(
            "can't move to second character",
            () -> iterator.next(),
            new Throws<>(NoSuchElementException.class)
        );
    }
}
