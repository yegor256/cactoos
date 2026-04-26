/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.cactoos.text.Randomized;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link Immutable}.
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
        MatcherAssert.assertThat(
            "can't remove from unmutable",
            () -> {
                immutable.remove();
                return 1;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void decoratesNext() {
        final int value = new Random().nextInt();
        MatcherAssert.assertThat(
            "next must return first value from iterator",
            new Immutable<>(new IteratorOf<>(value)).next(),
            new IsEqual<>(value)
        );
    }

    @Test
    void hasNextReturnsTrueForNotTraversedIterator() {
        MatcherAssert.assertThat(
            "hasNext must return true for not traversed iterator",
            new Immutable<>(new IteratorOf<>(new Random().nextInt())).hasNext(),
            new IsEqual<>(true)
        );
    }

    @Test
    void hasNextReturnsFalseForTraversedIterator() {
        final Iterator<Integer> immutable = new Immutable<>(
            new IteratorOf<>(new Random().nextInt())
        );
        immutable.next();
        MatcherAssert.assertThat(
            "hasNext must return false for already traversed iterator",
            immutable.hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void decoratesToString() throws Exception {
        final String string = new Randomized().asString();
        final Iterator<Object> iterator = new Iterator<Object>() {
            @Override
            public Object next() {
                return new Object();
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public String toString() {
                return string;
            }
        };
        MatcherAssert.assertThat(
            String.format("must delegate toString to decorated iterator, string=%s", string),
            new TextOf(new Immutable<>(iterator).toString()),
            new IsText(iterator.toString())
        );
    }
}
