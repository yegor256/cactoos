/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.ArrayList;
import java.util.Collection;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.object.HasToString;
import org.hamcrest.text.StringContainsInOrder;
import org.junit.jupiter.api.Test;
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
        MatcherAssert.assertThat(
            "Must throw exception",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).toArray(),
            new Throws<>(
                "Item #1 of #toArray() is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void throwsErrorIfNullInToArrayWithArg() {
        MatcherAssert.assertThat(
            "Must throw exception for the item#1",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).toArray(new Object[0]),
            new Throws<>(
                "Item #1 of #toArray(array) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void throwsErrorIfNullInContainsArg() {
        MatcherAssert.assertThat(
            "Must throw exception for #contains(null)",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(null),
            new Throws<>(
                "Argument of #contains(T) is NULL",
                IllegalArgumentException.class
            )
        );
    }

    @Test
    void testSuccessNotNullArg() {
        MatcherAssert.assertThat(
            "Must contain not null argument",
            new NoNulls<>(
                new ListOf<>(1)
            ).contains(1),
            new IsTrue()
        );
    }

    @Test
    void testSuccessAddAll() {
        final Collection<Integer> nonulls = new NoNulls<>(new ArrayList<>(0));
        nonulls.addAll(new ListOf<>(1, 2));
        MatcherAssert.assertThat(
            "Must add all",
            nonulls,
            new IsEqual<>(
                new ListOf<>(1, 2)
            )
        );
    }

    @Test
    void throwsErrorIfNullInAddAll() {
        MatcherAssert.assertThat(
            "Must throw exception for nullable #addAll() parameter collection",
            () -> new NoNulls<>(new ArrayList<Integer>(0)).addAll(new ListOf<>(1, 2, null)),
            new Throws<>(
                "Item #2 of #toArray() is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void behavesAsCollection() {
        MatcherAssert.assertThat(
            "Must behave as a no-null collection",
            new NoNulls<>(
                new ListOf<>(1)
            ),
            new BehavesAsCollection<>(1)
        );
    }

    @Test
    void hasToString() {
        MatcherAssert.assertThat(
            "Must have correct NoNulls#toString() and contain value of its elements",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ),
            new HasToString<>(
                new StringContainsInOrder(new IterableOf<>("1", "2", "3"))
            )
        );
    }

    @Test
    void emptyWhenCleared() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        col.clear();
        MatcherAssert.assertThat(
            "Must be empty after NoNulls#clear() is called",
            col,
            new IsEmptyCollection<>()
        );
    }

    @Test
    void whenItemRemoved() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        col.remove(1);
        MatcherAssert.assertThat(
            "Must have item removed from no-null collection",
            col,
            new IsEqual<>(new ListOf<>(2, 3))
        );
    }

    @Test
    void whenItemAdded() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2)
        );
        col.add(3);
        MatcherAssert.assertThat(
            "Must have item added to no-null collection",
            col,
            new IsEqual<>(new ListOf<>(1, 2, 3))
        );
    }

    @Test
    void throwsAtAttemptToAddNull() {
        MatcherAssert.assertThat(
            "Must throw exception when add NULL",
            () -> new NoNulls<>(
                new ListOf<>(1, 3)
            ).add(null),
            new Throws<>(
                "Item of #add(T) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void removesAll() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        col.removeAll(new ListOf<>(1, null));
        MatcherAssert.assertThat(
            "Must have an item removed",
            col,
            new HasValues<>(2, 3)
        );
    }

    @Test
    void retainsAll() {
        MatcherAssert.assertThat(
            "NoNulls#retainAll(...) must return false for the same elements",
            new NoNulls<>(new ListOf<>(1, 2, 3)).retainAll(new ListOf<>(1, 2, 3)),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void retainsAllWithNulls() {
        MatcherAssert.assertThat(
            "NoNulls#retailAll(..) must return true for different elements",
            new NoNulls<>(new ListOf<>(1, 2, 3)).retainAll(new ListOf<>(1, null, 3)),
            new IsTrue()
        );
    }

    @Test
    void throwsAtAttemptToRemoveNull() {
        MatcherAssert.assertThat(
            "Must throw exception when removing NULL for NoNull collection",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).remove(null),
            new Throws<>(
                "Item of #remove(T) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void hashCodeIsTheSame() {
        final Collection<Integer> items = new ListOf<>(1, 2);
        MatcherAssert.assertThat(
            "Must have the same hashCode",
            new NoNulls<>(items).hashCode(),
            new IsEqual<>(
                items.hashCode()
            )
        );
    }

    @Test
    void shouldBeEmpty() {
        MatcherAssert.assertThat(
            "Must be empty if created from an empty list",
            new NoNulls<>(new ListOf<>()).isEmpty(),
            new IsTrue()
        );
    }

    @Test
    void addsToEmptyCollection() {
        final Collection<Integer> col = new NoNulls<>(
            new ListOf<>()
        );
        col.add(1);
        MatcherAssert.assertThat(
            "Must not be empty after an item was added",
            col.isEmpty(),
            new IsNot<>(new IsTrue())
        );
    }
}
