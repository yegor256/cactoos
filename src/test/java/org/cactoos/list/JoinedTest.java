/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Joined}.
 *
 * @since 0.20
 */
final class JoinedTest {

    /**
     * Literal ONE value.
     */
    private static final String LITERAL_ONE = "ONE";

    /**
     * Literal TWO value.
     */
    private static final String LITERAL_TWO = "TWO";

    /**
     * Literal THREE value.
     */
    private static final String LITERAL_THREE = "THREE";

    /**
     * Literal FOUR value.
     */
    private static final String LITERAL_FOUR = "FOUR";

    @Test
    void behavesAsCollection() {
        MatcherAssert.assertThat(
            "Can't behave as a list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ),
            new BehavesAsList<>(JoinedTest.LITERAL_TWO)
        );
    }

    @Test
    void size() {
        MatcherAssert.assertThat(
            "Must evaluate the size of the joined list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).size(),
            new IsEqual<>(4)
        );
    }

    @Test
    void isEmpty() {
        MatcherAssert.assertThat(
            "Must be evaluated as an empty list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).isEmpty(),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void isEmptyForEmptyJoined() {
        MatcherAssert.assertThat(
            "Must be empty",
            new Joined<String>().isEmpty(),
            new IsTrue()
        );
    }

    @Test
    void emptyJoinedIsEqualToEmptyListOf() {
        MatcherAssert.assertThat(
            "Empty joined list must be equal to an empty ListOf",
            new Joined<String>(),
            new IsEqual<>(new ListOf<String>())
        );
    }

    @Test
    void contains() {
        MatcherAssert.assertThat(
            "Must contain element specified",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).contains(JoinedTest.LITERAL_THREE),
            new IsTrue()
        );
    }

    @Test
    void iterator() {
        MatcherAssert.assertThat(
            "Joined Iterator Must return next element equal to the first added",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).iterator().next(),
            new IsEqual<>(
                JoinedTest.LITERAL_ONE
            )
        );
    }

    @Test
    void addIsNotSupported() {
        MatcherAssert.assertThat(
            "add() must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).add(JoinedTest.LITERAL_THREE),
            new Throws<>(
                "#add(T): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void removeIsNotSupported() {
        MatcherAssert.assertThat(
            "remove(Object) must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).remove(JoinedTest.LITERAL_TWO),
            new Throws<>(
                "#remove(Object): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void containsAll() {
        MatcherAssert.assertThat(
            "Must contain all elements",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_THREE),
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_FOUR)
            ).containsAll(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO
                )
            ),
            new IsTrue()
        );
    }

    @Test
    void containsAllFailsWhenMissing() {
        MatcherAssert.assertThat(
            "Must not contain all elements",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO)
            ).containsAll(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_THREE
                )
            ),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void addAllIsNotSupported() {
        MatcherAssert.assertThat(
            "addAll(Collection) must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).addAll(
                new ListOf<>(
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR
                )
            ),
            new Throws<>(
                "#addAll(Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void addAllByIndexIsNotSupported() {
        MatcherAssert.assertThat(
            "addAll(int, Collection) must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).addAll(
                0,
                new ListOf<>(
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR
                )
            ),
            new Throws<>(
                "#addAll(int, Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void removeAllIsNotSupported() {
        MatcherAssert.assertThat(
            "removeAll(Collection) must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).removeAll(
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ),
            new Throws<>(
                "#removeAll(Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void retainAllIsNotSupported() {
        MatcherAssert.assertThat(
            "retainAll(Collection) must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).retainAll(
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ),
            new Throws<>(
                "#retainAll(Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void clearIsNotSupported() {
        MatcherAssert.assertThat(
            "clear() must throw exception",
            () -> {
                new Joined<String>(
                    new ListOf<>(JoinedTest.LITERAL_ONE),
                    new ListOf<>(JoinedTest.LITERAL_TWO)
                ).clear();
                return true;
            },
            new Throws<>(
                "#clear(): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void get() {
        MatcherAssert.assertThat(
            "Must get element",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            ).get(1),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        );
    }

    @Test
    void indexOutOfBoundsOnGet() {
        MatcherAssert.assertThat(
            "Exception is expected for index greater than size",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).get(2),
            new Throws<>(IndexOutOfBoundsException.class)
        );
    }

    @Test
    void negativeIndexOnGet() {
        MatcherAssert.assertThat(
            "Exception is expected for negative index",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).get(-1),
            new Throws<>(IndexOutOfBoundsException.class)
        );
    }

    @Test
    void setIsNotSupported() {
        MatcherAssert.assertThat(
            "set(int, T) must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).set(0, JoinedTest.LITERAL_THREE),
            new Throws<>(
                "#set(int, T): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void addByIndexIsNotSupported() {
        MatcherAssert.assertThat(
            "add(int, T) must throw exception",
            () -> {
                new Joined<String>(
                    new ListOf<>(JoinedTest.LITERAL_ONE),
                    new ListOf<>(JoinedTest.LITERAL_TWO)
                ).add(0, JoinedTest.LITERAL_THREE);
                return true;
            },
            new Throws<>(
                "#add(int, T): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void removeByIndexIsNotSupported() {
        MatcherAssert.assertThat(
            "remove(int) must throw exception",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).remove(0),
            new Throws<>(
                "#remove(int): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void iteratorRemoveIsNotSupported() {
        MatcherAssert.assertThat(
            "iterator().remove() must throw exception",
            () -> {
                final Iterator<String> iter = new Joined<String>(
                    new ListOf<>(JoinedTest.LITERAL_ONE),
                    new ListOf<>(JoinedTest.LITERAL_TWO)
                ).iterator();
                iter.next();
                iter.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void indexOf() {
        MatcherAssert.assertThat(
            "Must return the index of the element",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(JoinedTest.LITERAL_THREE)
            ).indexOf(JoinedTest.LITERAL_THREE),
            new IsEqual<>(2)
        );
    }

    @Test
    void indexOfMissing() {
        MatcherAssert.assertThat(
            "Must return -1 for a missing element",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).indexOf(JoinedTest.LITERAL_FOUR),
            new IsEqual<>(-1)
        );
    }

    @Test
    void lastIndexOf() {
        MatcherAssert.assertThat(
            "Must return the index of the last occurrence of the element",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_THREE
                )
            ).lastIndexOf(JoinedTest.LITERAL_ONE),
            new IsEqual<>(2)
        );
    }

    @Test
    void lastIndexOfMissing() {
        MatcherAssert.assertThat(
            "Must return -1 for a missing element",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).lastIndexOf(JoinedTest.LITERAL_FOUR),
            new IsEqual<>(-1)
        );
    }

    @Test
    void listIteratorPositionsCorrectly() {
        MatcherAssert.assertThat(
            "Must return the element at the requested position",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(JoinedTest.LITERAL_THREE)
            ).listIterator(1).next(),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        );
    }

    @Test
    void listIteratorSecond() {
        MatcherAssert.assertThat(
            "Exception is expected for greater then size index",
            () -> new Joined<Integer>().listIterator(66),
            new Throws<>(IndexOutOfBoundsException.class)
        );
    }

    @Test
    void listIteratorNegativeIndex() {
        MatcherAssert.assertThat(
            "Exception is expected for negative index",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE)
            ).listIterator(-1),
            new Throws<>(IndexOutOfBoundsException.class)
        );
    }

    @Test
    void subList() {
        MatcherAssert.assertThat(
            "Must be able to to get sub list",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
            ).subList(1, 3),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        );
    }

    @Test
    void subListOutOfBoundsEnd() {
        MatcherAssert.assertThat(
            "Exception is expected when end is greater than size",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).subList(0, 3),
            new Throws<>(IndexOutOfBoundsException.class)
        );
    }

    @Test
    void subListNegativeStart() {
        MatcherAssert.assertThat(
            "Exception is expected when start is negative",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).subList(-1, 1),
            new Throws<>(IndexOutOfBoundsException.class)
        );
    }

    @Test
    void subListThrowsIllegalArgument() {
        MatcherAssert.assertThat(
            "Exception is expected when start is greater than end",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).subList(2, 1),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void equalsListOf() {
        MatcherAssert.assertThat(
            "Joined must be equal to ListOf with the same elements",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(JoinedTest.LITERAL_THREE)
            ),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        );
    }

    @Test
    void listOfEqualsJoined() {
        MatcherAssert.assertThat(
            "ListOf must be equal to Joined with the same elements",
            new ListOf<>(
                JoinedTest.LITERAL_ONE,
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            ),
            new IsEqual<>(
                new Joined<String>(
                    new ListOf<>(
                        JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                    ),
                    new ListOf<>(JoinedTest.LITERAL_THREE)
                )
            )
        );
    }

    @Test
    void doesNotEqualDifferentListOf() {
        MatcherAssert.assertThat(
            "Joined must not be equal to a ListOf with different elements",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ),
            new IsNot<>(
                new IsEqual<>(
                    new ListOf<>(
                        JoinedTest.LITERAL_TWO,
                        JoinedTest.LITERAL_ONE
                    )
                )
            )
        );
    }

    @Test
    void hashCodeMatchesListOf() {
        MatcherAssert.assertThat(
            "Joined must have the same hashCode as ListOf of the same elements",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(JoinedTest.LITERAL_THREE)
            ).hashCode(),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                ).hashCode()
            )
        );
    }

    @Test
    void evaluatesSourcesLazily() {
        MatcherAssert.assertThat(
            "Sources must not be evaluated until an operation is called",
            () -> new Joined<String>(new JoinedTest.BrokenSourceList()).size(),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void itemAndList() {
        MatcherAssert.assertThat(
            "Must be able to join element with a list",
            new Joined<>(
                JoinedTest.LITERAL_ONE,
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
            ),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        );
    }

    @Test
    void infersCorrectly() {
        MatcherAssert.assertThat(
            "Must be able to infer type of elements",
            new Joined<>(
                1,
                new ListOf<>(
                    2.0,
                    3.0
                )
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Number>>(
                    new IsInstanceOf(Integer.class),
                    new IsInstanceOf(Double.class),
                    new IsInstanceOf(Double.class)
                )
            )
        );
    }

    /**
     * A source list that fails when its list iterator is requested.
     *
     * @since 1.0.0
     */
    private static final class BrokenSourceList extends AbstractList<String> {

        @Override
        public String get(final int index) {
            final String item;
            if (index == 0) {
                item = JoinedTest.LITERAL_ONE;
            } else {
                item = JoinedTest.LITERAL_TWO;
            }
            return item;
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public ListIterator<String> listIterator() {
            throw new IllegalStateException("sources must stay untouched");
        }
    }
}
