/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 *
 * @since 0.35
 */
final class NoNullsTest {

    @Test
    void throwsErrorIfNullInGet() {
        new Assertion<>(
            "must throw error if contains null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).get(1),
            new Throws<>(
                "Item #1 of [1, null, 3] is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void setThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if set null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).set(2, null),
            new Throws<>(
                "Item can't be NULL in #set(2,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void setThrowsErrorIfPreviousValueNull() {
        final List<Integer> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if previous value is null",
            () -> new NoNulls<>(list).set(0, 2),
            new Throws<>(
                "Result of #set(0,T) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void addThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if add null",
            () -> {
                new NoNulls<>(new ArrayList<>(1)).add(0, null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #add(0,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void removeThrowsErrorIfValueNull() {
        final List<String> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if removed value is null",
            () -> new NoNulls<>(list).remove(0),
            new Throws<>(
                "Result of #remove(0) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfListIteratorNextValueIsNull() {
        new Assertion<>(
            "must throw error if removed value in iterator is null",
            () -> new NoNulls<>(
                new ListOf<>(null, 2, 3)
            ).listIterator().next(),
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void throwsErrorIfListIteratorPreviousValueIsNull() {
        final List<Integer> list = new ArrayList<>(2);
        list.add(1);
        list.add(2);
        final ListIterator<Integer> listiterator = new NoNulls<>(
            list
        ).listIterator();
        listiterator.next();
        list.set(0, null);
        new Assertion<>(
            "must throw error if previous value in iterator is null",
            listiterator::previous,
            new Throws<>(
                "Previous item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void indexOfThrowsErrorIfArgumentIsNull() {
        new Assertion<>(
            "must throw error if searched value is null",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).indexOf(null),
            new Throws<>(
                "Item can't be NULL in #indexOf(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void indexOfTest() {
        new Assertion<>(
            "must return first index",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).indexOf(2),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void lastIndexOfThrowsErrorIfArgumentIsNull() {
        new Assertion<>(
            "must throw error if searched value is null",
            () -> new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).lastIndexOf(null),
            new Throws<>(
                "Item can't be NULL in #lastIndexOf(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void lastIndexOfTest() {
        new Assertion<>(
            "must return last index",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).lastIndexOf(2),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    void sizeTest() {
        new Assertion<>(
            "must return list size",
            new NoNulls<>(
                new ListOf<>(1, 2, 2, 2, 5)
            ).size(),
            new IsEqual<>(5)
        ).affirm();
    }

    @Test
    void isEmptyTrueTest() {
        new Assertion<>(
            "must return true if list is empty",
            new NoNulls<>(
                new ListOf<>()
            ).isEmpty(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void isEmptyFalseTest() {
        new Assertion<>(
            "must return false if list is not empty",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).isEmpty(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void clearTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.clear();
        new Assertion<>(
            "must be empty",
            list.isEmpty(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void containsTrueTest() {
        new Assertion<>(
            "must return true if elements is in list",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(2),
            new IsTrue()
        ).affirm();
    }

    @Test
    void containsFalseTest() {
        new Assertion<>(
            "must return false if elements is not in list",
            new NoNulls<>(
                new ListOf<>(1, 2, 3)
            ).contains(4),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void setValidInputTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.set(1, 5);
        new Assertion<>(
            "must update the list",
            list.toArray(),
            new IsEqual<>(
                new Integer[]{1, 5, 3}
            )
        ).affirm();
    }

    @Test
    void addValidInputTest() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.add(1, 5);
        new Assertion<>(
            "must update the list",
            list.toArray(),
            new IsEqual<>(
                new Integer[]{1, 5, 2, 3}
            )
        ).affirm();
    }

    @Test
    void addAllReturnsTrue() {
        new Assertion<>(
            "must return true on success",
            new NoNulls<>(new ListOf<>(1, 2, 3)).addAll(new ListOf<>(4, 5, 6)),
            new IsTrue()
        ).affirm();
    }

    @Test
    void addAllAddsItemsToEnd() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.addAll(new ListOf<>(4, 5, 6));
        new Assertion<>(
            "must add items to the end of the list",
            list,
            new IsEqual<>(new ListOf<>(1, 2, 3, 4, 5, 6))
        ).affirm();
    }

    @Test
    void addAllWithIndexReturnsTrue() {
        new Assertion<>(
            "must return true on success",
            new NoNulls<>(new ListOf<>(1, 2, 3)).addAll(1, new ListOf<>(4, 5, 6)),
            new IsTrue()
        ).affirm();
    }

    @Test
    void addAllWithIndexAddsAtIndex() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3)
        );
        list.addAll(1, new ListOf<>(4, 5, 6));
        new Assertion<>(
            "must add items to list at specified index",
            list,
            new IsEqual<>(new ListOf<>(1, 4, 5, 6, 2, 3))
        ).affirm();
    }

    @Test
    void removeAllReturnsTrueWhenChanged() {
        new Assertion<>(
            "must return true if list changed",
            new NoNulls<>(new ListOf<>(1, 2, 3, 4, 5, 6))
                .removeAll(new ListOf<>(10, 4, 2, 9, -3)),
            new IsTrue()
        ).affirm();
    }

    @Test
    void removeAllRemovesItems() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3, 4, 5, 6)
        );
        list.removeAll(new ListOf<>(10, 4, 2, 9, -3));
        new Assertion<>(
            "must remove specified items",
            list,
            new IsEqual<>(new ListOf<>(1, 3, 5, 6))
        ).affirm();
    }

    @Test
    void removeAllReturnsFalseWhenUnchanged() {
        new Assertion<>(
            "must return false if list did not change",
            new NoNulls<>(new ListOf<>(1, 2, 3, 4, 5, 6))
                .removeAll(new ListOf<>(10, 0, 7, 9, -3)),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void removeAllKeepsListUnchanged() {
        final List<Integer> list = new NoNulls<>(
            new ListOf<>(1, 2, 3, 4, 5, 6)
        );
        list.removeAll(new ListOf<>(10, 0, 7, 9, -3));
        new Assertion<>(
            "list must be the same",
            list,
            new IsEqual<>(new ListOf<>(1, 2, 3, 4, 5, 6))
        ).affirm();
    }

    @Test
    void equalsReturnsTrueForSameContent() {
        new Assertion<>(
            "two NoNulls with same content must be equal",
            new NoNulls<>(new ArrayList<>(new ListOf<>("a", "b"))),
            new IsEqual<>(
                new NoNulls<>(new ArrayList<>(new ListOf<>("a", "b")))
            )
        ).affirm();
    }

    @Test
    void equalsIsSymmetricWithPlainList() {
        final List<Integer> plain = new ArrayList<>(new ListOf<>(1, 2, 3));
        final List<Integer> wrapped = new NoNulls<>(
            new ArrayList<>(new ListOf<>(1, 2, 3))
        );
        new Assertion<>(
            "wrapper.equals(plain) must match plain.equals(wrapper)",
            wrapped.equals(plain),
            new IsEqual<>(plain.equals(wrapped))
        ).affirm();
    }

    @Test
    void hashCodeMatchesPlainList() {
        new Assertion<>(
            "hashCode() must follow List contract",
            new NoNulls<>(
                new ArrayList<>(new ListOf<>(1, 2, 3))
            ).hashCode(),
            new IsEqual<>(
                new ArrayList<>(new ListOf<>(1, 2, 3)).hashCode()
            )
        ).affirm();
    }

    @Test
    void equalsReturnsFalseForDifferentContent() {
        new Assertion<>(
            "two NoNulls with different content must not be equal",
            new NoNulls<>(new ArrayList<>(new ListOf<>("a", "b"))).equals(
                new NoNulls<>(new ArrayList<>(new ListOf<>("a", "c")))
            ),
            new IsEqual<>(false)
        ).affirm();
    }
}
