/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collections;
import java.util.List;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ListOf}.
 *
 * @since 0.1
 */
final class ListOfTest {

    @Test
    void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a list",
            new ListOf<>(1, 2),
            new BehavesAsList<>(2)
        ).affirm();
    }

    @Test
    void elementAtIndexTest() {
        final int num = 345;
        new Assertion<>(
            "Can't convert an iterable to a list",
            new ListOf<>(-1, num, 0, 1).get(1),
            new IsEqual<>(num)
        ).affirm();
    }

    @Test
    void sizeTest() {
        final int size = 42;
        new Assertion<>(
            "Can't build a list with a certain size",
            new ListOf<>(
                Collections.nCopies(size, 0)
            ),
            new HasSize(size)
        ).affirm();
    }

    @Test
    void emptyTest() {
        new Assertion<>(
            "Can't convert an empty iterable to an empty list",
            new ListOf<>(
                new IterableOf<>()
            ),
            new HasSize(0)
        ).affirm();
    }

    @Test
    void lowBoundTest() {
        new Assertion<>(
            "Exception is expected for negative index",
            () -> new ListOf<>(Collections.nCopies(10, 0)).get(-1),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void highBoundTest() {
        new Assertion<>(
            "Exception is expected for index larger then size",
            () -> new ListOf<>(Collections.nCopies(10, 0)).get(11),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void makesListFromMappedIterable() {
        new Assertion<>(
            "Can't turn a mapped iterable into a list",
            new ListOf<>(
                new Mapped<>(
                    i -> i + 1,
                    new IterableOf<>(1, -1, 0, 1)
                )
            ),
            new HasSize(4)
        ).affirm();
    }

    @Test
    void makesListFromMappedIterableOnSecondAccess() {
        final List<Integer> list = new ListOf<>(
            new Mapped<>(
                i -> i + 1,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        list.iterator().next();
        new Assertion<>(
            "Can't turn a mapped iterable into a list on second access",
            list,
            new HasSize(4)
        ).affirm();
    }

    @Test
    void equalsComparesContentBothListEnvelopes() {
        new Assertion<>(
            "Can't compare using equals.",
            new ListOf<>(1, 2),
            new IsEqual<>(new ListOf<>(1, 2))
        ).affirm();
    }

    @Test
    void equalsComparesContentListEnvelopeWithNormalList() {
        new Assertion<>(
            "Can't compare using equals.",
            new ListOf<>(1, 2),
            new IsEqual<>(new ListOf<>(1, 2))
        ).affirm();
    }

    @Test
    void equalsComparesEmptyLists() {
        new Assertion<>(
            "Can't compare using equals.",
            new ListOf<>(),
            new IsEqual<>(new ListOf<>())
        ).affirm();
    }

    @Test
    void printsListContent() {
        new Assertion<>(
            "Can't print content using toString.",
            new ListOf<>(1, 2).toString(),
            new IsEqual<>("[1, 2]")
        ).affirm();
    }

    @Test
    void hashCodesListContent() {
        new Assertion<>(
            "Can't create hashcode.",
            new ListOf<>(1, 2).hashCode(),
            new IsEqual<>(new ListOf<>(1, 2).hashCode())
        ).affirm();
    }
}
