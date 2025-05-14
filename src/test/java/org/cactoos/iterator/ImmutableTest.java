/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.cactoos.text.Randomized;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link Immutable}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ImmutableTest {

    @Test
    void doesNotAllowRemovingOfElements() {
        final List<String> list = new LinkedList<>();
        list.add("one");
        final Iterator<String> immutable = new Immutable<>(list.iterator());
        immutable.next();
        new Assertion<>(
            "Can't remove from unmutable",
            () -> {
                immutable.remove();
                return 1;
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void decoratesNext() {
        final int value = new Random().nextInt();
        final Iterator<Integer> immutable = new Immutable<>(
            new IteratorOf<>(value)
        );
        new Assertion<>(
            "next must return first value from iterator",
            immutable.next(),
            new IsEqual<>(value)
        ).affirm();
    }

    @Test
    void decoratesHasNext() {
        final int value = new Random().nextInt();
        final Iterator<Integer> immutable = new Immutable<>(
            new IteratorOf<>(value)
        );
        new Assertion<>(
            "hasNext must return true for not traversed iterator",
            immutable.hasNext(),
            new IsEqual<>(true)
        ).affirm();
        immutable.next();
        new Assertion<>(
            "hasNext must return false for already traversed iterator",
            immutable.hasNext(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void decoratesToString() throws Exception {
        final String string = new Randomized().asString();
        final Iterator<Object> iterator = new Iterator<Object>() {
            public Object next() {
                return new Object();
            }

            public boolean hasNext() {
                return false;
            }

            public String toString() {
                return string;
            }
        };
        final Iterator<Object> immutable = new Immutable<>(iterator);
        new Assertion<>(
            "must delegate toString to decorated iterator",
            new TextOf(immutable.toString()),
            new IsText(iterator.toString())
        ).affirm();
    }
}
