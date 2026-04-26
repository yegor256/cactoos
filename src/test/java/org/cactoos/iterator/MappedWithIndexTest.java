/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link MappedWithIndex}.
 * @since 1.0.0
 */
final class MappedWithIndexTest {

    @Test
    void iteratesOver() {
        MatcherAssert.assertThat(
            "must map values of iterator",
            new IterableOf<>(
                new MappedWithIndex<>(
                    (item, index) -> new FormattedText(
                        "%1$s - %2$s",
                        index,
                        item
                    ),
                    new IterableOf<>("1", "2", "0").iterator()
                )
            ),
            new HasValues<>(
                new TextOf("0 - 1"),
                new TextOf("1 - 2"),
                new TextOf("2 - 0")
            )
        );
    }

    @Test
    void failsIfIteratorExhausted() {
        final Iterator<Text> iterator = new MappedWithIndex<>(
            (item, index) -> new FormattedText(
                "%1$s X %2$s",
                index,
                item
            ),
            new IterableOf<>("1").iterator()
        );
        iterator.next();
        MatcherAssert.assertThat(
            "must throw NSEE",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void removingElementsFromIterator() {
        final Iterator<Text> iterator = new MappedWithIndex<>(
            (item, index) -> new FormattedText(
                "%1$s : %2$s",
                index,
                item
            ),
            new ArrayList<>(Arrays.asList("1", "2", "3")).iterator()
        );
        iterator.next();
        iterator.remove();
        MatcherAssert.assertThat(
            "must map values of changed iterator",
            new IterableOf<>(
                iterator
            ),
            new HasValues<>(
                new TextOf("1 : 2"),
                new TextOf("2 : 3")
            )
        );
    }
}
