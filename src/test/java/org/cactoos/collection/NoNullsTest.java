/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.ArrayList;
import java.util.Collection;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.object.HasToString;
import org.hamcrest.text.StringContainsInOrder;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class NoNullsTest {

    @Test
    void throwsErrorIfNullInToArray() {
        new Assertion<>(
            "Must throw exception",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).toArray(),
            new Throws<>(
                "Item #1 of #toArray() is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfNullInToArrayWithArg() {
        new Assertion<>(
            "Must throw exception for the item#1",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).toArray(new Object[3]),
            new Throws<>(
                "Item #1 of #toArray(array) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfNullInContainsArg() {
        new Assertion<>(
            "Must throw exception for #contains(null)",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(null),
            new Throws<>(
                "Argument of #contains(T) is NULL",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void testSuccessNotNullArg() {
        new Assertion<>(
            "Must contain not null argument",
            new NoNulls<>(
                new ListOf<>(1)
            ).contains(1),
            new IsTrue()
        ).affirm();
    }

    @Test
    void testSuccessAddAll() {
        final Collection<Integer> nonulls = new NoNulls<>(new ArrayList<>(0));
        nonulls.addAll(new ListOf<>(1, 2));
        new Assertion<>(
            "Must add all",
            nonulls,
            new IsEqual<>(
                new ListOf<>(1, 2)
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfNullInAddAll() {
        final Collection<Integer> nonulls = new NoNulls<>(new ArrayList<>(0));
        new Assertion<>(
            "Must throw exception for nullable #addAll() parameter collection",
            () -> nonulls.addAll(new ListOf<>(1, 2, null)),
            new Throws<>(
                "Item #2 of #toArray() is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void behavesAsCollection() {
        new Assertion<>(
            "Must behave as a no-null collection",
            new NoNulls<>(
                new ListOf<>(1)
            ),
            new BehavesAsCollection<>(1)
        ).affirm();
    }

    @Test
    void hasToString() {
        new Assertion<>(
            "Must have correct NoNulls#toString() and contain value of its elements",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ),
            new HasToString<>(
                new StringContainsInOrder(new IterableOf<>("1", "2", "3"))
            )
        ).affirm();
    }

    @Test
    void emptyWhenCleared() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        col.clear();
        new Assertion<>(
            "Must be empty after NoNulls#clear() is called",
            col,
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test
    void whenItemRemoved() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        col.remove(1);
        new Assertion<>(
            "Must have item removed from no-null collection",
            col,
            new IsEqual<>(new ListOf<>(2, 3))
        ).affirm();
    }

    @Test
    void whenItemAdded() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2)
        );
        col.add(3);
        new Assertion<>(
            "Must have item added to no-null collection",
            col,
            new IsEqual<>(new ListOf<>(1, 2, 3))
        ).affirm();
    }

    @Test
    void throwsAtAttemptToAddNull() {
        new Assertion<>(
            "Must throw exception when add NULL",
            () -> new NoNulls<>(
                new ListOf<>(1, 3)
            ).add(null),
            new Throws<>(
                "Item of #add(T) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void removesAll() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        col.removeAll(new ListOf<>(1, null));
        new Assertion<>(
            "Must have an item removed",
            col,
            new HasValues<>(2, 3)
        ).affirm();
    }

    @Test
    void retainsAll() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        new Assertion<>(
            "NoNulls#retainAll(...) must return false for the same elements",
            col.retainAll(new ListOf<>(1, 2, 3)),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    void retainsAllWithNulls() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        new Assertion<>(
            "NoNulls#retailAll(..) must return true for different elements",
            col.retainAll(new ListOf<>(1, null, 3)),
            new IsTrue()
        ).affirm();
    }

    @Test
    void throwsAtAttemptToRemoveNull() {
        new Assertion<>(
            "Must throw exception when removing NULL for NoNull collection",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).remove(null),
            new Throws<>(
                "Item of #remove(T) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void hashCodeIsTheSame() {
        final Collection<Integer> items = new ListOf<>(1, 2);
        new Assertion<>(
            "Must have the same hashCode",
            new NoNulls<>(items).hashCode(),
            new IsEqual<>(
                items.hashCode()
            )
        ).affirm();
    }

    @Test
    void shouldBeEmpty() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>()
        );
        new Assertion<>(
            "Must be empty if created from an empty list",
            col.isEmpty(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void addsToEmptyCollection() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>()
        );
        col.add(1);
        new Assertion<>(
            "Must not be empty after an item was added",
            col.isEmpty(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

}
