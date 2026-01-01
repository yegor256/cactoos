/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link IteratorOfBytes}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class IteratorOfBytesTest {

    @Test
    void canBeConstructedFromString() {
        final Iterator<Byte> itr = new IteratorOfBytes(
            "F"
        );
        new Assertion<>(
            "Must have 1 element",
            new ListOf<Object>(
                itr.next(),
                itr.hasNext()
            ),
            new HasValues<>(
                (byte) 'F', false
            )
        ).affirm();
    }

    @Test
    void canBeConstructedFromText() {
        final Iterator<Byte> itr = new IteratorOfBytes(
            new TextOf("ABC")
        );
        new Assertion<>(
            "Must have 3 elements",
            new ListOf<Object>(
                itr.next(),
                itr.next(),
                itr.next(),
                itr.hasNext()
            ),
            new HasValues<>(
                (byte) 'A', (byte) 'B', (byte) 'C', false
            )
        ).affirm();
    }

    @Test
    void emptyIteratorDoesNotHaveNext() {
        new Assertion<>(
            "hasNext is true for empty iterator.",
            new IteratorOfBytes().hasNext(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void emptyIteratorThrowsException() {
        new Assertion<>(
            "Exception is expected on iterating empty bytes.",
            () -> new IteratorOfBytes().next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        new Assertion<>(
            "hasNext is true for fully traversed iterator.",
            this.iteratorWithFetchedElements().hasNext(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        new Assertion<>(
            "Exception is expected for fully traversed iterator.",
            () -> this.iteratorWithFetchedElements().next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    private IteratorOfBytes iteratorWithFetchedElements() {
        final IteratorOfBytes iterator =
            new IteratorOfBytes((byte) 1, (byte) 2, (byte) 3);
        iterator.next();
        iterator.next();
        iterator.next();
        return iterator;
    }
}
