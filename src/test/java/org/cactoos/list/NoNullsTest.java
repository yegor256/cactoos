/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class NoNullsTest {

    @Test
    void getThrowsErrorIfNull() {
        MatcherAssert.assertThat(
            "must throw error if contains null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).get(1),
            new Throws<>(
                "Item #1 of [1, null, 3] is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void setThrowsErrorIfArgumentNull() {
        MatcherAssert.assertThat(
            "must throw error if set null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).set(2, null),
            new Throws<>(
                "Item can't be NULL in #set(2,T)",
                IllegalArgumentException.class
            )
        );
    }

    @Test
    void setThrowsErrorIfPreviousValueNull() {
        final List<Integer> list = new ArrayList<>(1);
        list.add(null);
        MatcherAssert.assertThat(
            "must throw error if previous value is null",
            () -> new NoNulls<>(list).set(0, 2),
            new Throws<>(
                "Result of #set(0,T) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void addThrowsErrorIfArgumentNull() {
        MatcherAssert.assertThat(
            "must throw error if add null",
            () -> {
                new NoNulls<>(new ArrayList<>(1)).add(0, null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #add(0,T)",
                IllegalArgumentException.class
            )
        );
    }

    @Test
    void removeThrowsErrorIfValueNull() {
        final List<Integer> list = new ArrayList<>(1);
        list.add(null);
        MatcherAssert.assertThat(
            "must throw error if removed value is null",
            () -> new NoNulls<>(list).remove(0),
            new Throws<>(
                "Result of #remove(0) is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void getThrowsErrorIfListIteratorNextValueIsNullValue() {
        MatcherAssert.assertThat(
            "must throw error if removed value in iterator is null",
            () -> new NoNulls<>(
                new ListOf<>(null, 2, 3)
            ).listIterator().next(),
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void getThrowsErrorIfListIteratorPreviousValueIsNullValue() {
        final List<Integer> list = new ArrayList<>(2);
        list.add(1);
        list.add(2);
        final ListIterator<Integer> listiterator = new NoNulls<>(
            list
        ).listIterator();
        listiterator.next();
        list.set(0, null);
        MatcherAssert.assertThat(
            "must throw error if previous value in iterator is null",
            listiterator::previous,
            new Throws<>(
                "Previous item is NULL",
                IllegalStateException.class
            )
        );
    }

    @Test
    void indexOfThrowsErrorIfArgumentIsNull() {
        MatcherAssert.assertThat(
            "must throw error if searched value is null",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).indexOf(null),
            new Throws<>(
                "Item can't be NULL in #indexOf(T)",
                IllegalArgumentException.class
            )
        );
    }

    @Test
    void indexOfTest() {
        MatcherAssert.assertThat(
            "must return first index",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).indexOf(2),
            new IsEqual<>(1)
        );
    }

    @Test
    void lastIndexOfThrowsErrorIfArgumentIsNull() {
        MatcherAssert.assertThat(
            "must throw error if searched value is null",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).lastIndexOf(null),
            new Throws<>(
                "Item can't be NULL in #lastIndexOf(T)",
                IllegalArgumentException.class
            )
        );
    }

    @Test
    void lastIndexOfTest() {
        MatcherAssert.assertThat(
            "must return last index",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).lastIndexOf(2),
            new IsEqual<>(3)
        );
    }

    @Test
    void sizeTest() {
        MatcherAssert.assertThat(
            "must return list size",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).size(),
            new IsEqual<>(5)
        );
    }

    @Test
    void isEmptyTrueTest() {
        MatcherAssert.assertThat(
            "must return true if list is empty",
            new NoNulls<>(
                new ListOf<>()
            ).isEmpty(),
            new IsTrue()
        );
    }

    @Test
    void isEmptyFalseTest() {
        MatcherAssert.assertThat(
            "must return false if list is not empty",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    void clearTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.clear();
        MatcherAssert.assertThat(
            "must be empty",
            list.isEmpty(),
            new IsTrue()
        );
    }

    @Test
    void containsTrueTest() {
        MatcherAssert.assertThat(
            "must return true if elements is in list",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(2),
            new IsTrue()
        );
    }

    @Test
    void containsFalseTest() {
        MatcherAssert.assertThat(
            "must return false if elements is not in list",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(4),
            new IsEqual<>(false)
        );
    }

    @Test
    void setValidInputTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.set(1, 5);
        MatcherAssert.assertThat(
            "must update the list",
            list.toArray(),
            new IsEqual<>(
                new Integer[]{1, 5, 3}
            )
        );
    }

    @Test
    void addValidInputTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.add(1, 5);
        MatcherAssert.assertThat(
            "must update the list",
            list.toArray(),
            new IsEqual<>(
                new Integer[]{1, 5, 2, 3}
            )
        );
    }

    @Test
    void addAllReturnsTrue() {
        MatcherAssert.assertThat(
            "must return true on success",
            new NoNulls<>(new ListOf<>(1, 2, 3)).addAll(new ListOf<>(4, 5, 6)),
            new IsTrue()
        );
    }

    @Test
    void addAllAddsItemsToEnd() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.addAll(new ListOf<>(4, 5, 6));
        MatcherAssert.assertThat(
            "must add items to the end of the list",
            list,
            Matchers.contains(1, 2, 3, 4, 5, 6)
        );
    }

    @Test
    void addAllWithIndexReturnsTrue() {
        MatcherAssert.assertThat(
            "must return true on success",
            new NoNulls<>(new ListOf<>(1, 2, 3)).addAll(1, new ListOf<>(4, 5, 6)),
            new IsTrue()
        );
    }

    @Test
    void addAllWithIndexAddsAtIndex() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.addAll(1, new ListOf<>(4, 5, 6));
        MatcherAssert.assertThat(
            "must add items to list at specified index",
            list,
            Matchers.contains(1, 4, 5, 6, 2, 3)
        );
    }

    @Test
    void removeAllReturnsTrueWhenChanged() {
        MatcherAssert.assertThat(
            "must return true if list changed",
            new NoNulls<>(new ListOf<>(1, 2, 3, 4, 5, 6))
                .removeAll(new ListOf<>(10, 4, 2, 9, -3)),
            new IsTrue()
        );
    }

    @Test
    void removeAllRemovesItems() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3, 4, 5, 6)
        );
        list.removeAll(new ListOf<>(10, 4, 2, 9, -3));
        MatcherAssert.assertThat(
            "must remove specified items",
            list,
            Matchers.contains(1, 3, 5, 6)
        );
    }

    @Test
    void removeAllReturnsFalseWhenUnchanged() {
        MatcherAssert.assertThat(
            "must return false if list did not change",
            new NoNulls<>(new ListOf<>(1, 2, 3, 4, 5, 6))
                .removeAll(new ListOf<>(10, 0, 7, 9, -3)),
            new IsEqual<>(false)
        );
    }

    @Test
    void removeAllKeepsListUnchanged() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3, 4, 5, 6)
        );
        list.removeAll(new ListOf<>(10, 0, 7, 9, -3));
        MatcherAssert.assertThat(
            "list must be the same",
            list,
            Matchers.contains(1, 2, 3, 4, 5, 6)
        );
    }

    @Test
    void equalsReturnsTrueForSameContent() {
        MatcherAssert.assertThat(
            "two NoNulls with same content must be equal",
            new NoNulls<>(new ArrayList<>(new ListOf<>("a", "b"))),
            new IsEqual<>(
                new NoNulls<>(new ArrayList<>(new ListOf<>("a", "b")))
            )
        );
    }

    @Test
    void equalsIsSymmetricWithPlainList() {
        final List<Integer> plain = new ArrayList<>(new ListOf<>(1, 2, 3));
        final List<Integer> wrapped = new NoNulls<>(
            new ArrayList<>(new ListOf<>(1, 2, 3))
        );
        MatcherAssert.assertThat(
            "wrapper.equals(plain) must match plain.equals(wrapper)",
            wrapped.equals(plain),
            new IsEqual<>(plain.equals(wrapped))
        );
    }

    @Test
    void hashCodeMatchesPlainList() {
        MatcherAssert.assertThat(
            "hashCode() must follow List contract",
            new NoNulls<>(
                new ArrayList<>(new ListOf<>(1, 2, 3))
            ).hashCode(),
            new IsEqual<>(
                new ArrayList<>(new ListOf<>(1, 2, 3)).hashCode()
            )
        );
    }

    @Test
    void equalsReturnsFalseForDifferentContent() {
        MatcherAssert.assertThat(
            "two NoNulls with different content must not be equal",
            new NoNulls<>(new ArrayList<>(new ListOf<>("a", "b"))).equals(
                new NoNulls<>(new ArrayList<>(new ListOf<>("a", "c")))
            ),
            new IsEqual<>(false)
        );
    }
}
