/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link JoinedListIterator}.
 *
 * @since 1.0.0
 */
final class JoinedListIteratorTest {

    @Test
    @SuppressWarnings("unchecked")
    void joinsListIterators() {
        MatcherAssert.assertThat(
            "Must concatenate iterable of listIterators together",
            new IterableOf<>(
                new JoinedListIterator<String>(
                    new ListOf<>("x").listIterator(),
                    new ListOf<>("y").listIterator()
                )
            ),
            new IsEqual<>(new IterableOf<>("x", "y"))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void navigatesNextOnNonEmptyIteratorFirstTime() {
        MatcherAssert.assertThat(
            "Must call next method directly on non-empty listIterator for the first time",
            new JoinedListIterator<>(
                new ListOf<>(1).listIterator(),
                new ListOf<>(2).listIterator(),
                new ListOf<>(3).listIterator()
            ).next(),
            new IsEqual<>(1)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void navigatesNextOnNonEmptyIteratorSecondTime() {
        final ListIterator<Integer> joined = new JoinedListIterator<>(
            new ListOf<>(1).listIterator(),
            new ListOf<>(2).listIterator(),
            new ListOf<>(3).listIterator()
        );
        joined.next();
        MatcherAssert.assertThat(
            "Must call next method directly on non-empty listIterator for the second time",
            joined.next(),
            new IsEqual<>(2)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void navigatesPreviousOnNonEmptyIterator() {
        final ListIterator<Integer> joined = new JoinedListIterator<>(
            new ListOf<>(1).listIterator(),
            new ListOf<>(2).listIterator(),
            new ListOf<>(3).listIterator()
        );
        joined.next();
        joined.next();
        MatcherAssert.assertThat(
            "Must call previous method directly on non-empty listIterator",
            joined.previous(),
            new IsEqual<>(2)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void navigatesPreviousTwiceOnNonEmptyIterator() {
        final ListIterator<Integer> joined = new JoinedListIterator<>(
            new ListOf<>(1).listIterator(),
            new ListOf<>(2).listIterator(),
            new ListOf<>(3).listIterator()
        );
        joined.next();
        joined.next();
        joined.previous();
        MatcherAssert.assertThat(
            "Must call previous method directly on non-empty listIterator for the second time",
            joined.previous(),
            new IsEqual<>(1)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void throwsExceptionWhenCallPreviousOnEmptyIterator() {
        MatcherAssert.assertThat(
            "Must throw an exception",
            () -> new JoinedListIterator<Integer>(new ListOf<>()).previous(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinItemAndIterable() {
        MatcherAssert.assertThat(
            "Must join item and iterable",
            new IterableOf<>(
                new JoinedListIterator<>(
                    0,
                    new ListOf<>(1, 2, 3).listIterator()
                )
            ),
            new IsEqual<>(new IterableOf<>(0, 1, 2, 3))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinIterableAndItem() {
        MatcherAssert.assertThat(
            "Must join iterable and item",
            new IterableOf<>(
                new JoinedListIterator<>(
                    new ListOf<>(1, 2, 3).listIterator(),
                    0
                )
            ),
            new IsEqual<>(new IterableOf<>(1, 2, 3, 0))
        );
    }

    @Test
    void nextIndexReturnsZeroInitially() {
        MatcherAssert.assertThat(
            "Must return index of the next element",
            new JoinedListIterator<>(
                new ListOf<>(1).listIterator(),
                new ListOf<>(2).listIterator(),
                new ListOf<>(3).listIterator()
            ).nextIndex(),
            new IsEqual<>(0)
        );
    }

    @Test
    void nextIndexReturnsOneAfterNext() {
        final ListIterator<Integer> joined = new JoinedListIterator<>(
            new ListOf<>(1).listIterator(),
            new ListOf<>(2).listIterator(),
            new ListOf<>(3).listIterator()
        );
        joined.next();
        MatcherAssert.assertThat(
            "Must return index of the next element after calling next",
            joined.nextIndex(),
            new IsEqual<>(1)
        );
    }

    @Test
    void previousIndexDoesntExistTest() {
        MatcherAssert.assertThat(
            "Must return -1 if there is no previous item",
            new JoinedListIterator<>(
                new ListOf<>(1).listIterator(),
                new ListOf<>(2).listIterator(),
                new ListOf<>(3).listIterator()
            ).previousIndex(),
            new IsEqual<>(-1)
        );
    }

    @Test
    void nextThrowsIfThereIsNoNext() {
        MatcherAssert.assertThat(
            "Must throw error if there is no next element",
            () -> new JoinedListIterator<>().next(),
            new Throws<>(
                NoSuchElementException.class
            )
        );
    }

    @Test
    void previousIndexReturnsZeroAfterOneNext() {
        final ListIterator<Integer> joined = new JoinedListIterator<>(
            new ListOf<>(1).listIterator(),
            new ListOf<>(2).listIterator(),
            new ListOf<>(3).listIterator()
        );
        joined.next();
        MatcherAssert.assertThat(
            "Must return index of the previous element after one next",
            joined.previousIndex(),
            new IsEqual<>(0)
        );
    }

    @Test
    void previousIndexReturnsOneAfterTwoNext() {
        final ListIterator<Integer> joined = new JoinedListIterator<>(
            new ListOf<>(1).listIterator(),
            new ListOf<>(2).listIterator(),
            new ListOf<>(3).listIterator()
        );
        joined.next();
        joined.next();
        MatcherAssert.assertThat(
            "Must return index of the previous element after two next",
            joined.previousIndex(),
            new IsEqual<>(1)
        );
    }

    @Test
    void removesLastReturnedItemFromSecondList() {
        final List<Integer> first = new ArrayList<>(Arrays.asList(1, 2));
        final List<Integer> second = new ArrayList<>(Arrays.asList(3, 4));
        final ListIterator<Integer> joined = new JoinedListIterator<Integer>(
            first.listIterator(), second.listIterator()
        );
        joined.next();
        joined.next();
        joined.next();
        joined.remove();
        MatcherAssert.assertThat(
            "Must remove the item from the list that returned it",
            second,
            new IsEqual<>(Arrays.asList(4))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void removesFromFirstListAfterPrevious() {
        final List<Integer> first = new ArrayList<>(Arrays.asList(1, 2));
        final List<Integer> second = new ArrayList<>(Arrays.asList(3));
        final ListIterator<Integer> joined = new JoinedListIterator<Integer>(
            first.listIterator(), second.listIterator()
        );
        joined.next();
        joined.next();
        joined.next();
        joined.previous();
        joined.previous();
        joined.remove();
        MatcherAssert.assertThat(
            "Must remove the item returned by previous",
            first,
            new IsEqual<>(Arrays.asList(1))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void rejectsRemoveBeforeNext() {
        MatcherAssert.assertThat(
            "Must refuse to remove before any item was returned",
            () -> {
                new JoinedListIterator<Integer>(
                    new ArrayList<>(Arrays.asList(1)).listIterator()
                ).remove();
                return true;
            },
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void setsLastReturnedItem() {
        final List<Integer> first = new ArrayList<>(Arrays.asList(1));
        final List<Integer> second = new ArrayList<>(Arrays.asList(2, 3));
        final ListIterator<Integer> joined = new JoinedListIterator<Integer>(
            first.listIterator(), second.listIterator()
        );
        joined.next();
        joined.next();
        joined.set(7);
        MatcherAssert.assertThat(
            "Must replace the item in the list that returned it",
            second,
            new IsEqual<>(Arrays.asList(7, 3))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void addsItemToIteratorWhereCursorIs() {
        final List<Integer> first = new ArrayList<>(Arrays.asList(1));
        final List<Integer> second = new ArrayList<>(Arrays.asList(2));
        final ListIterator<Integer> joined = new JoinedListIterator<Integer>(
            first.listIterator(), second.listIterator()
        );
        joined.next();
        joined.add(5);
        MatcherAssert.assertThat(
            "Must add the item right after the item returned last",
            first,
            new IsEqual<>(Arrays.asList(1, 5))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void addsItemBeforeAnythingWasReturned() {
        final List<Integer> first = new ArrayList<>(Arrays.asList(1));
        new JoinedListIterator<Integer>(
            first.listIterator(), new ArrayList<Integer>().listIterator()
        ).add(0);
        MatcherAssert.assertThat(
            "Must add the item at the very beginning",
            first,
            new IsEqual<>(Arrays.asList(0, 1))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void movesCursorAfterAdd() {
        final ListIterator<Integer> joined = new JoinedListIterator<Integer>(
            new ArrayList<>(Arrays.asList(1)).listIterator(),
            new ArrayList<>(Arrays.asList(2)).listIterator()
        );
        joined.next();
        joined.add(5);
        MatcherAssert.assertThat(
            "Must return the next item after the added one",
            joined.next(),
            new IsEqual<>(2)
        );
    }
}
