/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void size() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void isEmpty() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void isEmptyForEmptyJoined() {
        new Assertion<>(
            "Must be empty",
            new Joined<String>().isEmpty(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void emptyJoinedIsEqualToEmptyListOf() {
        new Assertion<>(
            "Empty joined list must be equal to an empty ListOf",
            new Joined<String>(),
            new IsEqual<>(new ListOf<String>())
        ).affirm();
    }

    @Test
    void contains() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void iterator() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void add() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.add(JoinedTest.LITERAL_THREE);
        new Assertion<>(
            "Must be able to add element specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    void addWritesThroughToSource() {
        final List<String> first = new ArrayList<>(
            Collections.singletonList(JoinedTest.LITERAL_ONE)
        );
        final List<String> second = new ArrayList<>(
            Collections.singletonList(JoinedTest.LITERAL_TWO)
        );
        final List<String> joined = JoinedTest.joined(first, second);
        joined.add(JoinedTest.LITERAL_THREE);
        new Assertion<>(
            "The appended element must be written to the last source list",
            second,
            new IsEqual<>(
                Arrays.asList(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
        new Assertion<>(
            "The other source lists must stay untouched",
            first,
            new IsEqual<>(Collections.singletonList(JoinedTest.LITERAL_ONE))
        ).affirm();
        new Assertion<>(
            "The joined list must see the element added to the source",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    void remove() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(JoinedTest.LITERAL_TWO);
        new Assertion<>(
            "Must be able to remove element specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            )
        ).affirm();
    }

    @Test
    void containsAll() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void containsAllFailsWhenMissing() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void addAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.addAll(
            new ListOf<>(
                JoinedTest.LITERAL_THREE,
                JoinedTest.LITERAL_FOUR
            )
        );
        new Assertion<>(
            "Must be able to addAll elements specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR
                )
            )
        ).affirm();
    }

    @Test
    void addAllInFront() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.addAll(
            0,
            new ListOf<>(
                JoinedTest.LITERAL_THREE,
                JoinedTest.LITERAL_FOUR
            )
        );
        new Assertion<>(
            "Must be able to addAll elements in front",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO
                )
            )
        ).affirm();
    }

    @Test
    void removeAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(
                JoinedTest.LITERAL_ONE,
                JoinedTest.LITERAL_TWO
            ),
            new ListOf<>(JoinedTest.LITERAL_THREE)
        );
        joined.removeAll(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        new Assertion<>(
            "Must be able to removeAll elements specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            )
        ).affirm();
    }

    @Test
    void retainAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        joined.retainAll(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        new Assertion<>(
            "Must be able to retain all",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    void clear() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            ),
            new ListOf<>(
                JoinedTest.LITERAL_ONE
            )
        );
        joined.clear();
        new Assertion<>(
            "Must be able to clear",
            joined.size(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void get() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void indexOutOfBoundsOnGet() {
        new Assertion<>(
            "Exception is expected for index greater than size",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).get(2),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void negativeIndexOnGet() {
        new Assertion<>(
            "Exception is expected for negative index",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).get(-1),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void set() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.set(0, JoinedTest.LITERAL_THREE);
        new Assertion<>(
            "Must be able to set element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        ).affirm();
    }

    @Test
    void setWritesThroughToSource() {
        final List<String> first = new ArrayList<>(
            Collections.singletonList(JoinedTest.LITERAL_ONE)
        );
        final List<String> second = new ArrayList<>(
            Arrays.asList(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
        );
        final List<String> joined = JoinedTest.joined(first, second);
        joined.set(1, JoinedTest.LITERAL_FOUR);
        new Assertion<>(
            "set() must replace the element in the source list owning it",
            second,
            new IsEqual<>(
                Arrays.asList(
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
        new Assertion<>(
            "The other source lists must stay untouched",
            first,
            new IsEqual<>(Collections.singletonList(JoinedTest.LITERAL_ONE))
        ).affirm();
        new Assertion<>(
            "The joined list must see the element set in the source",
            joined.get(1),
            new IsEqual<>(JoinedTest.LITERAL_FOUR)
        ).affirm();
    }

    @Test
    void addByIndex() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.add(0, JoinedTest.LITERAL_THREE);
        new Assertion<>(
            "Must be able to add element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        ).affirm();
    }

    @Test
    void removeByIndex() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(0);
        new Assertion<>(
            "Must be able to remove element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        ).affirm();
    }

    @Test
    void removeByElement() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(JoinedTest.LITERAL_ONE);
        new Assertion<>(
            "Must be able to remove element by specified element",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        ).affirm();
    }

    @Test
    void indexOf() {
        new Assertion<>(
            "Must return the index of the element",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(JoinedTest.LITERAL_THREE)
            ).indexOf(JoinedTest.LITERAL_THREE),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void indexOfMissing() {
        new Assertion<>(
            "Must return -1 for a missing element",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).indexOf(JoinedTest.LITERAL_FOUR),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void lastIndexOf() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void lastIndexOfMissing() {
        new Assertion<>(
            "Must return -1 for a missing element",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).lastIndexOf(JoinedTest.LITERAL_FOUR),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void listIteratorPositionsCorrectly() {
        new Assertion<>(
            "Must return the element at the requested position",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(JoinedTest.LITERAL_THREE)
            ).listIterator(1).next(),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        ).affirm();
    }

    @Test
    void listIteratorSecond() {
        new Assertion<>(
            "Exception is expected for greater then size index",
            () -> new Joined<Integer>().listIterator(66),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void listIteratorNegativeIndex() {
        new Assertion<>(
            "Exception is expected for negative index",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE)
            ).listIterator(-1),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void subList() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void subListReflectsSourceChanges() {
        final List<String> first = new ArrayList<>(
            Arrays.asList(JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO)
        );
        final List<String> sub = JoinedTest.joined(
            first,
            Collections.singletonList(JoinedTest.LITERAL_THREE)
        ).subList(1, 3);
        first.set(1, JoinedTest.LITERAL_FOUR);
        new Assertion<>(
            "The backed sublist must reflect the change of the source list",
            sub,
            new IsEqual<>(
                Arrays.asList(
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    void subListSetWritesThrough() {
        final List<String> first = new ArrayList<>(
            Arrays.asList(JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO)
        );
        final List<String> joined = JoinedTest.joined(
            first,
            Collections.singletonList(JoinedTest.LITERAL_THREE)
        );
        final List<String> sub = joined.subList(1, 3);
        sub.set(0, JoinedTest.LITERAL_FOUR);
        new Assertion<>(
            "The element set through the sublist must land in the source list",
            first,
            new IsEqual<>(
                Arrays.asList(JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_FOUR)
            )
        ).affirm();
        new Assertion<>(
            "The joined list must see the element set through the sublist",
            joined.get(1),
            new IsEqual<>(JoinedTest.LITERAL_FOUR)
        ).affirm();
    }

    @Test
    void subListAddWritesThrough() {
        final List<String> first = new ArrayList<>(
            Collections.singletonList(JoinedTest.LITERAL_ONE)
        );
        final List<String> second = new ArrayList<>(
            Arrays.asList(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
        );
        final List<String> joined = JoinedTest.joined(first, second);
        final List<String> sub = joined.subList(1, 3);
        sub.add(1, JoinedTest.LITERAL_FOUR);
        new Assertion<>(
            "The element added through the sublist must land in the source",
            second,
            new IsEqual<>(
                Arrays.asList(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
        new Assertion<>(
            "The sublist must see the element it added",
            sub,
            new IsEqual<>(
                Arrays.asList(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
        new Assertion<>(
            "The other source lists must stay untouched",
            first,
            new IsEqual<>(Collections.singletonList(JoinedTest.LITERAL_ONE))
        ).affirm();
        new Assertion<>(
            "The joined list must see the element added through the sublist",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    void subListRemoveWritesThrough() {
        final List<String> first = new ArrayList<>(
            Collections.singletonList(JoinedTest.LITERAL_ONE)
        );
        final List<String> second = new ArrayList<>(
            Arrays.asList(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
        );
        final List<String> joined = JoinedTest.joined(first, second);
        final List<String> sub = joined.subList(1, 3);
        new Assertion<>(
            "Sublist must return the removed element",
            sub.remove(0),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        ).affirm();
        new Assertion<>(
            "The sublist must see the element it removed",
            sub,
            new IsEqual<>(Collections.singletonList(JoinedTest.LITERAL_THREE))
        ).affirm();
        new Assertion<>(
            "The element removed through the sublist must leave the source",
            second,
            new IsEqual<>(Collections.singletonList(JoinedTest.LITERAL_THREE))
        ).affirm();
        new Assertion<>(
            "The other source lists must stay untouched",
            first,
            new IsEqual<>(Collections.singletonList(JoinedTest.LITERAL_ONE))
        ).affirm();
        new Assertion<>(
            "The joined list must see the element removed through the sublist",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    void subListOutOfBoundsEnd() {
        new Assertion<>(
            "Exception is expected when end is greater than size",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).subList(0, 3),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void subListNegativeStart() {
        new Assertion<>(
            "Exception is expected when start is negative",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).subList(-1, 1),
            new Throws<>(IndexOutOfBoundsException.class)
        ).affirm();
    }

    @Test
    void subListThrowsIllegalArgument() {
        new Assertion<>(
            "Exception is expected when start is greater than end",
            () -> new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO)
            ).subList(2, 1),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void equalsListOf() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void listOfEqualsJoined() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void doesNotEqualDifferentListOf() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void hashCodeMatchesListOf() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void evaluatesSourcesLazily() {
        new Assertion<>(
            "Sources must not be evaluated until an operation is called",
            () -> new Joined<String>(new JoinedTest.BrokenSourceList()).size(),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void itemAndList() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void infersCorrectly() {
        new Assertion<>(
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
        ).affirm();
    }

    private static List<String> joined(final List<String> first,
        final List<String> second) {
        return new Joined<>(new IterableOf<>(first, second));
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
