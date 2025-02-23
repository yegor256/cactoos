/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link ListIteratorNoNulls}.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ListIteratorNoNullsTest {

    @Test
    void mustThrowsErrorIfListIteratorNextValueIsNull() {
        new Assertion<>(
            "must throw error next item is null",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(null, 2, 3).listIterator()
                ).next();
                return 0;
            },
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void mustThrowsErrorIfListIteratorPreviousValueIsNull() {
        new Assertion<>(
            "must throw error if previous value is null",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(
                        null, 2, 3
                    ).listIterator(1)
                ).previous();
                return 0;
            },
            new Throws<>(
                "Previous item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void mustAddToListIterator() {
        new Assertion<>(
            "must add to list iterator",
            () -> {
                final List<Integer> list = new ArrayList<>(2);
                list.add(1);
                list.add(2);
                final ListIterator<Integer> iterator = new ListIteratorNoNulls<>(
                    list.listIterator()
                );
                iterator.next();
                iterator.add(4);
                return iterator.previous();
            },
            new HasValue<>(4)
        ).affirm();
    }

    @Test
    void mustRemoveFromListIterator() {
        new Assertion<>(
            "must remove element from list iterator",
            () -> {
                final List<Integer> list = new ArrayList<>(2);
                list.add(1);
                list.add(2);
                final ListIterator<Integer> iterator = new ListIteratorNoNulls<>(
                    list.listIterator()
                );
                iterator.next();
                iterator.remove();
                return iterator.previous();
            },
            new Throws<>(
                NoSuchElementException.class
            )
        ).affirm();
    }

    @Test
    void mustSetValueListIterator() {
        new Assertion<>(
            "must set element into list iterator",
            () -> {
                final List<Integer> list = new ArrayList<>(2);
                list.add(1);
                list.add(2);
                final ListIterator<Integer> iterator = new ListIteratorNoNulls<>(
                    list.listIterator()
                );
                iterator.next();
                iterator.set(4);
                return iterator.previous();
            },
            new HasValue<>(4)
        ).affirm();
    }

    @Test
    void mustThrowsErrorIfAddANullItem() {
        new Assertion<>(
            "must throw error if add a null item",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(1, 2, 3).listIterator()
                ).add(null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #add(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void mustThrowsErrorIfSetANullItem() {
        new Assertion<>(
            "must throw error if set a null item",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(1, 2, 3).listIterator()
                ).set(null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #set(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }
}
